package com.example.mazunina.topanimelist.data.network.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreatorEntity {
    @SerializedName("mal_id")
    private Long id;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
