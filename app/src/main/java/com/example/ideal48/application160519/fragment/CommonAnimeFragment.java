package com.example.ideal48.application160519.fragment;


import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.AnimeDataSource;
import com.example.ideal48.application160519.GetDataService;
import com.example.ideal48.application160519.MainThreadExecutor;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.RequestFailure;
import com.example.ideal48.application160519.RetrofitClientInstance;
import com.example.ideal48.application160519.adapter.AnimeListAdapter;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class CommonAnimeFragment extends Fragment {

    View view;
    private List<Anime> mAnimeList = new ArrayList<>();

    String mAnimeCategory = "";
    Call<AnimeResponse> call;

    public CommonAnimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAnimeCategory = getArguments().getString(getString(R.string.url));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.anime_list_layout, container, false);

        TextView mEmptyStateTextView = (TextView) view.findViewById(R.id.empty_view);
        View loadingIndicator = view.findViewById(R.id.loading_indicator);
        RecyclerView animeRecyclerView = view.findViewById(R.id.anime_recycler_list);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            MainThreadExecutor executor = new MainThreadExecutor();

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

            AnimeListAdapter mAdapter = new AnimeListAdapter(getActivity());

            animeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            animeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            animeRecyclerView.setHasFixedSize(true);
            animeRecyclerView.setAdapter(mAdapter);

            AnimeDataSource dataSource = new AnimeDataSource(service, mAnimeCategory);

            PagedList.Config config = new PagedList.Config.Builder()
                    .setPageSize(50).setInitialLoadSizeHint(100)
                    .setEnablePlaceholders(true).build();

            PagedList<Anime> list = new PagedList.Builder<>(dataSource, config).setFetchExecutor(executor).setNotifyExecutor(executor).build();

            loadingIndicator.setVisibility(View.GONE);
            mAdapter.submitList(list);

            dataSource.getRequestFailureLiveData().observe(this, new Observer<RequestFailure>() {
                @Override
                public void onChanged(@Nullable final RequestFailure requestFailure) {
                    if (requestFailure == null) return;

                    Snackbar.make(view.findViewById(R.id.anime_recycler_list), requestFailure.getErrorMessage(), Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Retry the failed request
                                    requestFailure.getRetryable().retry();
                                }
                            }).show();
                }
            });

        } else {

            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("Internet Not Available");

            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();

        }
        return view;
    }

}
