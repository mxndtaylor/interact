package org.example.base.slot;

import org.example.base.Filled;

public record FilledSlot<T extends SlotResult>(T value) implements Filled<T> {
}
