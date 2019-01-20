package com.example.mazunina.topanimelist.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mazunina.topanimelist.R;

public class AboutAppFragment extends BaseFragment {
    private static final String TAG = "AboutAppFragment";

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_app_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView apiInfoView = view.findViewById(R.id.api_info);
        apiInfoView.setText(Html.fromHtml(getString(R.string.api_info)));
        apiInfoView.setMovementMethod(LinkMovementMethod.getInstance());
        TextView authorInfoView = view.findViewById(R.id.author_info);
        authorInfoView.setText(Html.fromHtml(getString(R.string.author_info)));
        authorInfoView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public boolean hasBottomNavigation() {
        return true;
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showError(String message) {

    }
}
