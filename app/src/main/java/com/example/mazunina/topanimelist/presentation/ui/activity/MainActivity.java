package com.example.mazunina.topanimelist.presentation.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mazunina.topanimelist.R;
import com.example.mazunina.topanimelist.domain.model.ContentType;
import com.example.mazunina.topanimelist.presentation.BundleArgs;
import com.example.mazunina.topanimelist.presentation.ui.fragment.AboutAppFragment;
import com.example.mazunina.topanimelist.presentation.ui.fragment.BaseFragment;
import com.example.mazunina.topanimelist.presentation.ui.fragment.TopContentListFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static final String KEY_SELECTED_ITEM_ID = "key_selected_item_id";
    private static final String KEY_BOTTOM_NAVIGATION_VISIBILITY = "key_bottom_navigation_visibility";

    private BottomNavigationView bottomNavigationView;
    private int selectedItemId;
    private boolean bottomNavigationVisible;

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = (@NonNull MenuItem item) -> {
        navigate(item);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener);

        // add back stack listener to show/hide bottom navigation
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            bottomNavigationVisible = savedInstanceState.getBoolean(KEY_BOTTOM_NAVIGATION_VISIBILITY, false);
            bottomNavigationView.setVisibility(bottomNavigationVisible ? View.VISIBLE : View.GONE);

            selectedItemId = savedInstanceState.getInt(KEY_SELECTED_ITEM_ID, 0);
            selectedItem = bottomNavigationView.getMenu().findItem(selectedItemId);
            selectedItem.setChecked(true);
        } else {
            selectedItem = bottomNavigationView.getMenu().getItem(0);
            navigate(selectedItem);
        }
    }

    private void navigate(MenuItem item) {
        selectedItemId = item.getItemId();
        item.setChecked(true);

        BaseFragment fragment = null;
        Bundle bundle = new Bundle();
        switch (selectedItemId) {
            case R.id.menu_anime:
                bundle.putString(BundleArgs.ARG_CONTENT_TYPE, ContentType.ANIME.name());
                fragment = TopContentListFragment.newInstance(bundle);
                break;
            case R.id.menu_manga:
                bundle.putString(BundleArgs.ARG_CONTENT_TYPE, ContentType.MANGA.name());
                fragment = TopContentListFragment.newInstance(bundle);
                break;
            case R.id.menu_about:
                fragment = AboutAppFragment.newInstance();
                break;
        }
        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentByTag(fragment.getFragmentTag()) != null) {
                fm.popBackStack(fragment.getFragmentTag(), 0);
            } else {
                fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, fragment, fragment.getFragmentTag())
                        .addToBackStack(fragment.getFragmentTag())
                        .commit();
            }
            fm.executePendingTransactions();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_ITEM_ID, selectedItemId);
        outState.putBoolean(KEY_BOTTOM_NAVIGATION_VISIBILITY, bottomNavigationVisible);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onBackPressed();
            if (!((BaseFragment) fragment).hasBottomNavigation()) {
                FragmentManager fm = getSupportFragmentManager();
                fm.popBackStack();
                fm.executePendingTransactions();
                return;
            }
        }
        MenuItem firstItem = bottomNavigationView.getMenu().getItem(0);
        if (selectedItemId != firstItem.getItemId()) {
            navigate(firstItem);
        } else {
            finish();
        }
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BaseFragment) {
            bottomNavigationVisible = ((BaseFragment) fragment).hasBottomNavigation();
            bottomNavigationView.setVisibility(bottomNavigationVisible ? View.VISIBLE : View.GONE);
        }
    }
}
