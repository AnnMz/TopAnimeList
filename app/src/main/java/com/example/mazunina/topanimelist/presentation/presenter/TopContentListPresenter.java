package com.example.mazunina.topanimelist.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.domain.model.ContentType;
import com.example.mazunina.topanimelist.domain.usecase.TopContentListUseCase;
import com.example.mazunina.topanimelist.presentation.App;
import com.example.mazunina.topanimelist.presentation.view.TopContentListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class TopContentListPresenter extends BasePresenter<TopContentListView> {
    private final ContentType contentType;

    @Inject
    TopContentListUseCase useCase;

    public TopContentListPresenter(ContentType contentType) {
        this.contentType = contentType;
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData(false);
    }

    private void loadData(boolean swipe) {
        showLoad(swipe, true);
        Single<List<ContentModel>> getTopListSingle;
        if (contentType == ContentType.ANIME) {
            getTopListSingle = useCase.getTopAnimeList();
        } else {
            getTopListSingle = useCase.getTopMangaList();
        }
        Disposable disposable = getTopListSingle.subscribe(
                result -> {
                    showLoad(swipe, false);
                    getViewState().showData(result);
                }, error -> {
                    showLoad(swipe, false);
                    getViewState().showError(error.getMessage());
                });
        addDisposable(disposable);
    }

    private void showLoad(boolean swipe, boolean isLoading) {
        if (swipe) {
            getViewState().showRefreshing(isLoading);
        } else {
            getViewState().showLoading(isLoading);
        }
    }

    public void onBackPressed() {
        getViewState().showRefreshing(false);
        getViewState().showLoading(false);
    }

    public void onSwipeRefresh() {
        loadData(true);
    }

    public void onItemClick(ContentModel model) {
        getViewState().navigateToDetail(model.getId(), contentType);
    }
}
