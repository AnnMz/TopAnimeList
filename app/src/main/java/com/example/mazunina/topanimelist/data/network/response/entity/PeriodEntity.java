package com.example.mazunina.topanimelist.data.network.response.entity;

import com.google.gson.annotations.SerializedName;

public class PeriodEntity {
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("string")
    private String periodStr;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPeriodStr() {
        return periodStr;
    }
}
