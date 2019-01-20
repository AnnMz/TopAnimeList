package com.example.mazunina.topanimelist.presentation.utils;

import android.view.View;

public class ViewController {
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_PROGRESS = 2;

    private int type = TYPE_DEFAULT;

    private View contentView;
    private View emptyView;
    private View progressView;

    public ViewController(View contentView, View progressView, View emptyView) {
        this.contentView = contentView;
        this.progressView = progressView;
        this.emptyView = emptyView;

        render();
    }

    public void showEmpty() {
        type = TYPE_EMPTY;
        render();
    }

    public void showProgress() {
        type = TYPE_PROGRESS;
        render();
    }

    public void showDefault() {
        type = TYPE_DEFAULT;
        render();
    }

    private void render() {
        switch (type) {
            case TYPE_EMPTY:
                contentView.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                break;
            case TYPE_PROGRESS:
                contentView.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                break;
            default:
                contentView.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                break;
        }
    }
}
