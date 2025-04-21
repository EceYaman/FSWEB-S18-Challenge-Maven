package com.workintech.fswebs18challengemaven.entity;

public enum Color {
    SPADE,
    HEARTH,
    DIAMOND,
    CLUB;

    @Override
    public String toString() {
        return name();
    }
}
