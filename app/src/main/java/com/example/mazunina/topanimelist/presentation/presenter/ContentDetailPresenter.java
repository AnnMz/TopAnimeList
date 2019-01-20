package com.example.mazunina.topanimelist.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.model.ContentType;
import com.example.mazunina.topanimelist.domain.usecase.ContentDetailUseCase;
import com.example.mazunina.topanimelist.presentation.App;
import com.example.mazunina.topanimelist.presentation.view.ContentDetailView;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ContentDetailPresenter extends BasePresenter<ContentDetailView> {
    private final long contentId;
    private final ContentType contentType;

    @Inject
    ContentDetailUseCase useCase;

    public ContentDetailPresenter(long contentId, ContentType contentType) {
        this.contentId = contentId;
        this.contentType = contentType;
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        if (contentId == -1) {
            getViewState().showError("Unknown content id");
            return;
        }
        getViewState().showLoading(true);
        Single<ContentDetailModel> getContentDetailSingle;
        if (contentType == ContentType.ANIME) {
            getContentDetailSingle = useCase.getAnimeDetail(contentId);
        } else {
            getContentDetailSingle = useCase.getMangaDetail(contentId);
        }
        Disposable disposable = getContentDetailSingle.subscribe(
                result -> {
                    getViewState().showLoading(false);
                    getViewState().showData(result);
                }, error -> {
                    getViewState().showLoading(false);
                    getViewState().showError(error.getMessage());
                }
        );
        addDisposable(disposable);
    }

    public void onBackPressed() {
        getViewState().showLoading(false);
    }
}
