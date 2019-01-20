package com.example.mazunina.topanimelist.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mazunina.topanimelist.R;
import com.example.mazunina.topanimelist.domain.model.ContentDetailModel;
import com.example.mazunina.topanimelist.domain.model.ContentType;
import com.example.mazunina.topanimelist.presentation.BundleArgs;
import com.example.mazunina.topanimelist.presentation.presenter.ContentDetailPresenter;
import com.example.mazunina.topanimelist.presentation.utils.UiUtils;
import com.example.mazunina.topanimelist.presentation.utils.ViewController;
import com.example.mazunina.topanimelist.presentation.view.ContentDetailView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContentDetailFragment extends BaseFragment implements ContentDetailView {
    private static final String TAG = "ContentDetailFragment";

    @InjectPresenter
    ContentDetailPresenter presenter;

    private TextView titleView;
    private TextView titleJpView;
    private ImageView imageView;
    private TextView descriptionView;
    private TextView releaseTitleView;
    private TextView releaseValueView;
    private TextView ratingTitleView;
    private TextView ratingValueView;
    private TextView genresTitleView;
    private TextView genresValueView;
    private TextView authorTitleView;
    private TextView authorValueView;
    private TextView scoreTitleView;
    private TextView scoreValueView;

    private Snackbar snackbar;
    private ViewController viewController;

    public static ContentDetailFragment newInstance(Bundle args) {
        ContentDetailFragment fragment = new ContentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ContentDetailPresenter provideContentDetailPresenter() {
        Bundle args = getArguments();
        if (args != null) {
            long contentId = args.getLong(BundleArgs.ARG_CONTENT_ID, -1);
            ContentType contentType = ContentType.getByName(args.getString(BundleArgs.ARG_CONTENT_TYPE));
            return new ContentDetailPresenter(contentId, contentType);
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_detail_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View contentView = view.findViewById(R.id.content);
        View progressView = view.findViewById(R.id.layout_progress);
        View emptyView = view.findViewById(R.id.layout_empty);
        viewController = new ViewController(contentView, progressView, emptyView);

        titleView = view.findViewById(R.id.title);
        titleJpView = view.findViewById(R.id.title_jp);
        imageView = view.findViewById(R.id.image);
        descriptionView = view.findViewById(R.id.description);
        releaseTitleView = view.findViewById(R.id.release_title);
        releaseValueView = view.findViewById(R.id.release_value);
        ratingTitleView = view.findViewById(R.id.rating_title);
        ratingValueView = view.findViewById(R.id.rating_value);
        genresTitleView = view.findViewById(R.id.genres_title);
        genresValueView = view.findViewById(R.id.genres_value);
        authorTitleView = view.findViewById(R.id.author_title);
        authorValueView = view.findViewById(R.id.author_value);
        scoreTitleView = view.findViewById(R.id.score_title);
        scoreValueView = view.findViewById(R.id.score_value);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public boolean hasBottomNavigation() {
        return false;
    }

    @Override
    public void showLoading(boolean isLoading) {
        if (isLoading) {
            viewController.showProgress();
        } else {
            viewController.showDefault();
        }
    }

    @Override
    public void showError(String message) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        if (getView() != null) {
            snackbar = UiUtils.snackbar(getView(), message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void showData(ContentDetailModel model) {
        if (model == null) {
            viewController.showEmpty();
            return;
        }
        titleView.setText(model.getTitle());
        titleJpView.setText(model.getTitleJp());
        descriptionView.setText(model.getDescription());
        setImage(model.getImage());
        setReleasePeriodAndStatus(model.getStartDate(), model.getEndDate(), model.getStatus());
        setRating(model.getRating());
        setGenres(model.getGenres());
        if (model.getStudios() != null && !model.getStudios().isEmpty()) {
            setAuthors(model.getStudios().size() > 1 ? getString(R.string.studios) : getString(R.string.studio), model.getStudios());
        } else {
            setAuthors(model.getAuthors().size() > 1 ? getString(R.string.authors) : getString(R.string.author), model.getAuthors());
        }
        setScore(model.getScore());

    }

    private void setImage(String image) {
        if (getContext() != null && isAdded()) {
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .error(R.drawable.ic_panorama_black_24dp);
            Glide.with(getContext())
                    .load(image)
                    .apply(options)
                    .into(imageView);
        }
    }

    private void setReleasePeriodAndStatus(Date startDate, Date endDate, String status) {
        if (startDate == null && endDate == null) {
            releaseTitleView.setVisibility(View.GONE);
            releaseValueView.setVisibility(View.GONE);
        } else {
            final SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            String startDateS = formatter.format(startDate);
            String endDateS = "...";
            if (endDate != null) {
                endDateS = formatter.format(endDate);
            }
            releaseTitleView.setVisibility(View.VISIBLE);
            releaseValueView.setVisibility(View.VISIBLE);
            releaseValueView.setText(String.format("%s - %s (%s)", startDateS, endDateS, status));
        }
    }

    private void setRating(String rating) {
        if (rating == null || rating.isEmpty()) {
            ratingTitleView.setVisibility(View.GONE);
            ratingValueView.setVisibility(View.GONE);
        } else {
            ratingTitleView.setVisibility(View.VISIBLE);
            ratingValueView.setVisibility(View.VISIBLE);
            ratingValueView.setText(rating);
        }
    }

    private void setGenres(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            genresTitleView.setVisibility(View.GONE);
            genresValueView.setVisibility(View.GONE);
        } else {
            genresTitleView.setVisibility(View.VISIBLE);
            genresValueView.setVisibility(View.VISIBLE);
            genresValueView.setText(TextUtils.join(", ", genres));
        }
    }

    private void setAuthors(String title, List<String> values) {
        if (values == null || values.isEmpty()) {
            authorTitleView.setVisibility(View.GONE);
            authorValueView.setVisibility(View.GONE);
        } else {
            authorTitleView.setVisibility(View.VISIBLE);
            authorValueView.setVisibility(View.VISIBLE);
            authorTitleView.setText(title);
            authorValueView.setText(TextUtils.join(", ", values));
        }
    }

    private void setScore(float score) {
        scoreValueView.setText(String.valueOf(score));
    }

    @Override
    public void onDestroy() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        super.onDestroy();
    }
}
