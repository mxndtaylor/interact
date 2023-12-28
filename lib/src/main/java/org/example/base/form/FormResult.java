package org.example.base.form;

import java.io.Serializable;
import java.util.Map;

import org.example.base.slot.SlotResult;

/**
 *  Exists as convenience to collect all the assumptions we make about classes passed to Forms
 *  @see org.example.base.slot.SlotResult
 */
public interface FormResult<T extends FormResult<T>> extends Serializable {
    T copyWith(Map<String, ? extends SlotResult> map);
    Map<String, ? extends SlotResult> fieldsToMap();
    FormResultMeta<T> getMeta();
}
