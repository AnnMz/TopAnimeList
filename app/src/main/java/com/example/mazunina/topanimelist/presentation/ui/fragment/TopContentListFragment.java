package com.example.mazunina.topanimelist.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.mazunina.topanimelist.R;
import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.domain.model.ContentType;
import com.example.mazunina.topanimelist.presentation.BundleArgs;
import com.example.mazunina.topanimelist.presentation.presenter.TopContentListPresenter;
import com.example.mazunina.topanimelist.presentation.ui.adapter.TopContentListAdapter;
import com.example.mazunina.topanimelist.presentation.utils.UiUtils;
import com.example.mazunina.topanimelist.presentation.utils.ViewController;
import com.example.mazunina.topanimelist.presentation.view.TopContentListView;

import java.util.List;

public class TopContentListFragment extends BaseFragment implements TopContentListView {
    private static final String TAG = "TopListFragment";

    @InjectPresenter
    TopContentListPresenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TopContentListAdapter adapter;
    private Snackbar snackbar;
    private ViewController viewController;

    private final TopContentListAdapter.Callbacks callbacks = new TopContentListAdapter.Callbacks() {
        @Override
        public void onItemClick(ContentModel model) {
            presenter.onItemClick(model);
        }
    };

    public static TopContentListFragment newInstance(Bundle args) {
        TopContentListFragment fragment = new TopContentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    TopContentListPresenter provideTopContentListPresenter() {
        Bundle args = getArguments();
        if (args != null) {
            ContentType contentType = ContentType.getByName(args.getString(BundleArgs.ARG_CONTENT_TYPE));
            return new TopContentListPresenter(contentType);
        }
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TopContentListAdapter(getContext());
        adapter.setCallbacks(callbacks);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top_list_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView contentView = view.findViewById(R.id.content);
        contentView.setHasFixedSize(true);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.setAdapter(adapter);

        View progressView = view.findViewById(R.id.layout_progress);
        View emptyView = view.findViewById(R.id.layout_empty);
        viewController = new ViewController(contentView, progressView, emptyView);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onSwipeRefresh());
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
    public void showData(List<ContentModel> data) {
        adapter.setData(data);
        if (data.isEmpty()) {
            viewController.showEmpty();
        }
    }

    @Override
    public void showRefreshing(boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void navigateToDetail(long contentId, ContentType contentType) {
        final Bundle bundle = new Bundle();
        bundle.putLong(BundleArgs.ARG_CONTENT_ID, contentId);
        bundle.putString(BundleArgs.ARG_CONTENT_TYPE, contentType.name());

        BaseFragment fragment = ContentDetailFragment.newInstance(bundle);
        if (getActivity() != null && isAdded()) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.container, fragment, fragment.getFragmentTag())
                    .addToBackStack(fragment.getFragmentTag())
                    .commit();
            fm.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public String getFragmentTag() {
        Bundle args = getArguments();
        if (args != null) {
            return TAG + args.getString(BundleArgs.ARG_CONTENT_TYPE);
        }
        return TAG;
    }

    @Override
    public boolean hasBottomNavigation() {
        return true;
    }

    @Override
    public void onDestroy() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        super.onDestroy();
    }
}
