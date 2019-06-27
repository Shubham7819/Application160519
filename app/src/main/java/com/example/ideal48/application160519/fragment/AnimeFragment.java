package com.example.ideal48.application160519.fragment;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.HomeActivity;
import com.example.ideal48.application160519.activity.SearchableActivity;
import com.example.ideal48.application160519.adapter.AnimePagerAdapter;

import static com.example.ideal48.application160519.activity.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeFragment extends Fragment {

    View view;
    Context context;

    public AnimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.anim_layout, container, false);

        context = getActivity();

        ViewPager viewPager = view.findViewById(R.id.anime_viewpager);
        TabLayout tabLayout = view.findViewById(R.id.anime_tabs);

        AnimePagerAdapter adapter = new AnimePagerAdapter(context, getActivity().getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("AnimeFragment", "onResume called");

        toolbar.setTitle(R.string.anime);

        // Add SearchView in Toolbar
        toolbar.inflateMenu(R.menu.anime_search_menu);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(context, SearchableActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("AnimeFragment", "onPause called");

        // Remove Search Action from Toolbar when Fragment is not Visible.
        toolbar.getMenu().clear();
    }
}
