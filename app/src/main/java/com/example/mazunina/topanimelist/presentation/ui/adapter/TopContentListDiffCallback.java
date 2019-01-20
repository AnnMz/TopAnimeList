package com.example.mazunina.topanimelist.presentation.ui.adapter;

import android.support.v7.util.DiffUtil;

import com.example.mazunina.topanimelist.domain.model.ContentModel;

import java.util.List;

public class TopContentListDiffCallback extends DiffUtil.Callback {
    private final List<ContentModel> oldData;
    private final List<ContentModel> newData;

    public TopContentListDiffCallback(List<ContentModel> oldData, List<ContentModel> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        ContentModel oldModel = oldData.get(oldItemPosition);
        ContentModel newModel = newData.get(newItemPosition);
        return oldModel.getId() == newModel.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ContentModel oldModel = oldData.get(oldItemPosition);
        ContentModel newModel = newData.get(newItemPosition);
        return oldModel.getType() == newModel.getType() && oldModel.getTitle().equals(newModel.getTitle());
    }
}
