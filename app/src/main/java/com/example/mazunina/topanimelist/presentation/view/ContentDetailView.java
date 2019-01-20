package com.example.mazunina.topanimelist.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;

public interface ContentDetailView extends BaseView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showData(ContentDetailModel result);
}
