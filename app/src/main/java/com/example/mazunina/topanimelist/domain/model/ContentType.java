package com.example.mazunina.topanimelist.domain.model;

public enum  ContentType {
    ANIME,
    MANGA,

    UNKNOWN;

    public static ContentType getByName(String name) {
        for (ContentType value : ContentType.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
