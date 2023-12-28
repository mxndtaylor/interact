package org.example.base.slot;

import java.io.Serializable;

/**
 *  This class is here to collect everything that we assume about types passed as Slot type parameters,
 *  to provide better visibility of what types should be passed to begin with,
 *  and to provide easier way for end users to mark classes which they plan on creating Slots for
 *  @see {@link org.example.base.form.FormResult}
 */
public interface SlotResult extends Serializable {
}
