package com.example.mazunina.topanimelist.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.domain.model.ContentType;

import java.util.List;

public interface TopContentListView extends BaseView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showData(List<ContentModel> data);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showRefreshing(boolean isLoading);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navigateToDetail(long contentId, ContentType contentType);
}
