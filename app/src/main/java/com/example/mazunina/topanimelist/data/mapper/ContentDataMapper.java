package com.example.mazunina.topanimelist.data.mapper;

import com.example.mazunina.topanimelist.data.network.response.ContentDetailResponse;
import com.example.mazunina.topanimelist.data.network.response.entity.ContentEntity;
import com.example.mazunina.topanimelist.data.network.response.entity.CreatorEntity;
import com.example.mazunina.topanimelist.data.network.response.entity.GenreEntity;
import com.example.mazunina.topanimelist.data.network.response.entity.PeriodEntity;
import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.model.Type;
import com.example.mazunina.topanimelist.domain.model.ContentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;

public class ContentDataMapper {
    private static final String WRITTEN_BY_NOTE = "[Written by MAL Rewrite]";
    private final SimpleDateFormat formatter;

    @Inject
    public ContentDataMapper() {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
    }

    public ContentModel transform(ContentEntity entity) {
        ContentModel model = new ContentModel();
        if (entity != null) {
            model.setId(entity.getId());
            model.setRank(entity.getRank());
            model.setTitle(entity.getTitle());
            model.setImage(entity.getImage());
            model.setType(Type.getByName(entity.getType()));
            model.setEpisodes(entity.getEpisodes() == null ? 0 : entity.getEpisodes());
            model.setVolumes(entity.getVolumes() == null ? 0 : entity.getVolumes());
            model.setStartDate(entity.getStartDate());
            model.setEndDate(entity.getEndDate());
            model.setScore(entity.getScore() == null ? 0 : entity.getScore());
        }
        return model;
    }

    public ContentDetailModel transformDetail(ContentDetailResponse response) {
        ContentDetailModel model = new ContentDetailModel();
        if (response != null) {
            model.setId(response.getId());
            model.setTitle(response.getTitle());
            model.setTitleJp(response.getTitleJp());
            String description = response.getDescription();
            if (description != null) {
                model.setDescription(description.replace(WRITTEN_BY_NOTE, ""));
            }
            model.setStatus(response.getStatus());
            model.setImage(response.getImage());
            model.setEpisodes(response.getEpisodes() == null ? 0 : response.getEpisodes());
            model.setVolumes(response.getVolumes() == null ? 0 : response.getVolumes());
            PeriodEntity periodEntity = response.getPeriod();
            if (periodEntity != null) {
                model.setStartDate(getDate(periodEntity.getFrom()));
                model.setEndDate(getDate(periodEntity.getTo()));
            }
            model.setRating(response.getRating());
            model.setScore(response.getScore() == null ? 0 : response.getScore());
            List<CreatorEntity> studioEntities = response.getStudios();
            if (studioEntities != null) {
                List<String> studios = new ArrayList<>();
                for (int i = 0; i < studioEntities.size(); i++) {
                    studios.add(studioEntities.get(i).getName());
                }
                model.setStudios(studios);
            }
            List<CreatorEntity> authorEntities = response.getAuthors();
            if (authorEntities != null) {
                List<String> authors = new ArrayList<>();
                for (int i = 0; i < authorEntities.size(); i++) {
                    authors.add(authorEntities.get(i).getName());
                }
                model.setAuthors(authors);
            }
            List<GenreEntity> genreEntities = response.getGenres();
            if (genreEntities != null) {
                List<String> genres = new ArrayList<>();
                for (int i = 0; i < genreEntities.size(); i++) {
                    genres.add(genreEntities.get(i).getName());
                }
                model.setGenres(genres);
            }
        }
        return model;
    }

    private Date getDate(String dateS) {
        if (dateS != null) {
            try {
                return formatter.parse(dateS);
            } catch (ParseException exception) {
                Timber.e(exception);
            }
        }
        return null;
    }
}
