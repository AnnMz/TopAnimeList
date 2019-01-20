package com.example.mazunina.topanimelist.data.network.response;

import com.example.mazunina.topanimelist.data.network.response.entity.CreatorEntity;
import com.example.mazunina.topanimelist.data.network.response.entity.GenreEntity;
import com.example.mazunina.topanimelist.data.network.response.entity.PeriodEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentDetailResponse extends BaseResponse {
    @SerializedName("mal_id")
    private Long id;
    @SerializedName("title")
    private String title;
    @SerializedName("title_japanese")
    private String titleJp;
    @SerializedName("synopsis")
    private String description;
    @SerializedName("status")
    private String status;
    @SerializedName("image_url")
    private String image;
    @SerializedName("episodes")
    private Integer episodes;
    @SerializedName("volumes")
    private Integer volumes;
    @SerializedName("aired")
    private PeriodEntity periodEntity;
    @SerializedName("rating")
    private String rating;
    @SerializedName("score")
    private Float score;
    @SerializedName("studios")
    private List<CreatorEntity> studioEntities;
    @SerializedName("authors")
    private List<CreatorEntity> authorEntities;
    @SerializedName("genres")
    private List<GenreEntity> genreEntityList;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleJp() {
        return titleJp;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public PeriodEntity getPeriod() {
        return periodEntity;
    }

    public String getRating() {
        return rating;
    }

    public Float getScore() {
        return score;
    }

    public List<CreatorEntity> getStudios() {
        return studioEntities;
    }

    public List<CreatorEntity> getAuthors() {
        return authorEntities;
    }

    public List<GenreEntity> getGenres() {
        return genreEntityList;
    }
}
