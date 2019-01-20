package com.example.mazunina.topanimelist.data.network.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("request_cached")
    private Boolean cached;
    @SerializedName("request_cache_expiry")
    private Long cacheExpiry;
    @SerializedName("request_hash")
    private String hash;

    @SerializedName("error")
    private String error;

    public Boolean isCached() {
        return cached;
    }

    public Long getCacheExpiry() {
        return cacheExpiry;
    }

    public String getHash() {
        return hash;
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
    }

    public String getError() {
        return error;
    }
}
