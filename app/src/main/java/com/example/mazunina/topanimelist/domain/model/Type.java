package com.example.mazunina.topanimelist.domain.model;

public enum Type {
    TV,
    MOVIE,
    OVA,
    ONA,
    MANGA,
    MANHWA,
    NOVEL,
    UNKNOWN;

    public static Type getByName(String name) {
        for (Type value : Type.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
