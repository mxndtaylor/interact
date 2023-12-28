package org.example.base.form;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.example.base.Filled;
import org.example.base.slot.FilledSlot;
import org.example.base.slot.Slot;
import org.example.base.slot.SlotResult;
import org.example.err.InteractError;
import org.example.err.InteractException;

public class FilledForm<T extends FormResult<T>> implements Filled<T> {
    private final T value;

    public FilledForm(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
    
    static class Builder<T extends FormResult<T>> {
        private final Map<String, FilledSlot<? extends SlotResult>> propertyMap;
        private final Class<T> type;
        private final Function<Map<String, FilledSlot<? extends SlotResult>>, T> mapConstructor;
        private final Map<String, Slot<? extends SlotResult>> slotMap;

        Builder(FormResultMeta<T> meta) {
            this.type = meta.getType();
            this.propertyMap = new LinkedHashMap<String, FilledSlot<? extends SlotResult>>();
            this.mapConstructor = meta.getFactory();
            this.slotMap = meta.getSlots();
        }

        public Builder<T> add(String key, SlotResult value) throws InteractException {
            // slot should be in map
            if (!slotMap.containsKey(key)) {
                throw new InteractException(
                    "Cannot add key '" + key + "', value '" + value
                        + "' as one of fields '" + slotMap.keySet() + "'"
                );
            }

            // slot should be defined
            Slot<? extends SlotResult> slot = slotMap.get(key);
            if (slot == null) {
                throw new InteractError(
                    "Cannot assign value '" + value + "' to null slot '" + slot + "'"
                );
            }

            // value should be assignable to the slot
            Class<?> valueClass = slot.getClass();
            if (value != null && !valueClass.isAssignableFrom(value.getClass())) {
                throw new InteractException(
                    "Cannot assign value '" + value + "' to field with class '"
                        + valueClass + "' due to incompatible types."
                );
            }

            propertyMap.put(key, slot.castAndFill(value));
            return this;
        }

        public Builder<T> addFrom(T baseValue) {
            Map<String, FilledSlot<? extends SlotResult>> map = new LinkedHashMap<>();
            // TODO: convert baseValue class to map
            
            

            final var values = Collections.unmodifiableMap(map);
            return addFromUnchecked(values);
        }
        
        public Builder<T> addFrom(Map<String, SlotResult> values) {
            Map<String, FilledSlot<? extends SlotResult>> checkedValues = new LinkedHashMap<>();
            slotMap.forEach((str, slot) -> { 
                if (values.containsKey(str)) {
                    checkedValues.put(str, slot.castAndFill(values.get(str)));
                }
            });
            return addFromUnchecked(checkedValues);
        }

        private Builder<T> addFromUnchecked(Map<String, FilledSlot<? extends SlotResult>> values) {
            propertyMap.putAll(values);
            return this;
        }
        
        public FilledForm<T> build() throws InteractError {
            return new FilledForm<T>(mapConstructor.apply(propertyMap));
        }
    }
}
