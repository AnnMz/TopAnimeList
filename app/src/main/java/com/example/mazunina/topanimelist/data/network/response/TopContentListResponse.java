package com.example.mazunina.topanimelist.data.network.response;

import com.example.mazunina.topanimelist.data.network.response.entity.ContentEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopContentListResponse extends BaseResponse {
    @SerializedName("top")
    private List<ContentEntity> entities;

    public List<ContentEntity> getEntities() {
        return entities;
    }
}
