package org.example.base.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.example.base.slot.FilledSlot;
import org.example.base.slot.Slot;
import org.example.base.slot.SlotResult;
import org.example.err.InteractError;

public class FormResultMeta<T extends FormResult<T>> {
    private static final Class<?> slotResultClass = SlotResult.class;
    private final Class<T> resultClass;

    public FormResultMeta(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    public Class<T> getType() {
        return resultClass;
    }

    public Constructor<T> getMapConstructor() throws InteractError {
        final var type = this.resultClass;
        Annotation[] annotations = type.getAnnotations();
        boolean hasMapConstructor = false;
        for (Annotation annotation : annotations) {
            switch (annotation.annotationType().getName()) {
                case "groovy.transform.MapConstructor":
                case "groovy.transform.Canonical":
                case "groovy.transform.Immutable":
                case "groovy.transform.TupleConstructor":
                    hasMapConstructor = true;
                    break;
            }
            if (hasMapConstructor) {
                break;
            }
        }
        try {
            return type.getConstructor(Map.class);
        } catch (NoSuchMethodException e) {
            if (hasMapConstructor) {
                throw new InteractError(
                    "Class " + type.getName() + " has a map constructor generating annotation, "
                        + "but no constructor was found by reflection. " 
                        + "Please fix the annotation-enabled generation of the constructor, "
                        + "or add an explicit constructor accepting a Map<String, Object> parameter "
                        + "mapping the names of the class's assignable fields to their values.",
                    e);
            }
            throw new InteractError(
                "Class " + type.getName() + " has no map constructor generating annotation "
                    + "and no constructor was found by reflection. " 
                    + "Please add a @MapConstructor annotation to the class, "
                    + "or an explicit constructor accepting a Map<String, Object> parameter "
                    + "mapping the names of the class's assignable fields to their values.",
                e
            );
        } catch (SecurityException e) {
            throw new InteractError(
                "Class " + type.getName() + " is not accessible, check class loader mismatch.",
                e
            );
        }
    }
    @SuppressWarnings("unchecked")
    public Map<String, Slot<? extends SlotResult>> getSlots() {
        final var type = this.resultClass;
        final var constructor = this.getMapConstructor();
        int constructorParamLength = constructor.getParameterCount();
        if (constructorParamLength == 0) {
            return Collections.emptyMap();
        }
        Class<?> constructorParamType = constructor.getParameters()[0].getType();
        if (constructorParamLength > 1 || constructorParamType.isAssignableFrom(Map.class)) {
            String message = constructorParamLength > 1 ? "Too many parameters" : "Single parameter has type '" + constructorParamType.getName() + "'";
            throw new InteractError(
                message 
                    + ", constructor should have a single parameter with type '"
                    + Map.class.getName() + "'"
            );
        }

        Field[] fields = null;
        try {
            fields = type.getFields();
        } catch (SecurityException e) {
            throw new InteractError(
                "Class " + type.getName() + " is not accessible, cannot access class's fields"
            );
        }

        Map<String, Slot<? extends SlotResult>> values = new LinkedHashMap<>();
        for (Field field : fields) {
            if (!slotResultClass.isAssignableFrom(field.getType())) {
                throw new InteractError(
                    "Field '" + field.getName() + "' of class '" 
                        + field.getType().getName() + "' is not assignable to class '"
                        + slotResultClass.getName() + "'. Cannot create Slot of field unless "
                        + "it is assignable to class '" + slotResultClass.getName() + "'."
                );
            }
            values.put(field.getName(), new Slot<SlotResult>(
                field.getName(),
                (Class<SlotResult>)field.getType())
            );
        }

        return Collections.unmodifiableMap(values);
    }
    public Function<Map<String, FilledSlot<? extends SlotResult>>, T> getFactory() {
        final var type = this.resultClass;
        final var constructor = getMapConstructor();
        return (map) -> {
            final var newMap = toSlotResultMap(map);
            try {
                return constructor.newInstance(newMap);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new InteractError(
                    "Class " + type.getName() + " has a map constructor, "
                        + " but was not able use it to construct an instance, " 
                        + "check the module, package, class and constructor permissions.",
                    e
                );
            }
        };
    }
    public static Map<String, SlotResult> toSlotResultMap(Map<String, FilledSlot<? extends SlotResult>> slots) {
        Map<String, SlotResult> result = new LinkedHashMap<>();

        slots.forEach((name, slot) -> result.put(name, slot.value()));

        return result;
    }
    
    public static 
}

