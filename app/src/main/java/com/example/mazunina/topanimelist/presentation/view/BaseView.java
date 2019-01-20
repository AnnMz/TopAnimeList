package com.example.mazunina.topanimelist.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface BaseView extends MvpView {
    void showLoading(boolean isLoading);

    void showError(String message);
}
