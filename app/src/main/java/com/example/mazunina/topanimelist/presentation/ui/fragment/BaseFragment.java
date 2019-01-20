package com.example.mazunina.topanimelist.presentation.ui.fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.mazunina.topanimelist.presentation.view.BaseView;

public abstract class BaseFragment extends MvpAppCompatFragment implements BaseView {
    public abstract void onBackPressed();

    public abstract String getFragmentTag();

    /**
     * Determines whether a fragment has bottom navigation
     */
    public abstract boolean hasBottomNavigation();
}
