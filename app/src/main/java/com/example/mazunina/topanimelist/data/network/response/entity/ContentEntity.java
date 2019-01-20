package com.example.mazunina.topanimelist.data.network.response.entity;

import com.google.gson.annotations.SerializedName;

public class ContentEntity {
    @SerializedName("mal_id")
    private Long id;
    @SerializedName("rank")
    private Integer rank;
    @SerializedName("title")
    private String title;
    @SerializedName("image_url")
    private String image;
    @SerializedName("type")
    private String type;
    @SerializedName("episodes")
    private Integer episodes;
    @SerializedName("volumes")
    private Integer volumes;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("score")
    private Float score;

    public Long getId() {
        return id;
    }

    public Integer getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Float getScore() {
        return score;
    }
}
