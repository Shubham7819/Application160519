package com.example.ideal48.application160519.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.GetDataService;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.RetrofitClientInstance;
import com.example.ideal48.application160519.adapter.AnimeListAdapter;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonAnimeFragment extends Fragment {

    View view;
    private TextView mEmptyStateTextView;
    private View loadingIndicator;
//    private static final String JIKAN_URL = "https://api.jikan.moe/top/anime";
    private AnimeListAdapter mAdapter;
    private List<Anime> mAnimeList = new ArrayList<>();

    String mAnimeCategory = "";
    Call<AnimeResponse> call;

    public CommonAnimeFragment() {
        // Required empty public constructor
    }

//    @SuppressLint("ValidFragment")
//    public CommonAnimeFragment(String animeCategory) {
//        mAnimeCategory = animeCategory;
//        Log.d("CommonAnimeFragment : ", animeCategory);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAnimeCategory = getArguments().getString(getString(R.string.url));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.anime_list_layout, container, false);
        mEmptyStateTextView = (TextView) view.findViewById(R.id.empty_view);
        loadingIndicator = view.findViewById(R.id.loading_indicator);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        if (mAnimeCategory.equals("all")) {
            call = service.getAllAnime();
        } else if (mAnimeCategory.equals("airing")) {
            call = service.getAiring();
        } else if (mAnimeCategory.equals("upcoming")) {
            call = service.getUpcoming();
        } else if (mAnimeCategory.equals("tv")) {
            call = service.getTV();
        } else if (mAnimeCategory.equals("movie")) {
            call = service.getMovie();
        } else if (mAnimeCategory.equals("ova")) {
            call = service.getOva();
        } else if (mAnimeCategory.equals("special")) {
            call = service.getSpecial();
        } else if (mAnimeCategory.equals("bypopularity")) {
            call = service.getBypopularity();
        } else {
            call = service.getFavorite();
        }

        call.enqueue(new Callback<AnimeResponse>() {
            @Override
            public void onResponse(Call<AnimeResponse> call, Response<AnimeResponse> response) {
//                try {
//                    String value  = response.body().string();
//                    Log.e("Response is", value);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                if (response.body() != null) {

                    mAnimeList.clear();
                    mAnimeList.addAll(response.body().getmTop());

                    RecyclerView animeRecyclerView = view.findViewById(R.id.anime_recycler_list);

                    mAdapter = new AnimeListAdapter(getActivity(), mAnimeList);

                    animeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    animeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    loadingIndicator.setVisibility(View.GONE);
                    animeRecyclerView.setAdapter(mAdapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText("Data Not Found !");
                }

            }

            @Override
            public void onFailure(Call<AnimeResponse> call, Throwable t) {

                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });

//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//
//            new AsyncTask<Void, Void, List<Anime>>(){
//
//                @Override
//                protected List<Anime> doInBackground(Void... voids) {
//                    return QueryUtils.fetchAnimeData(mAnimeCategory);
//                }
//
//                @Override
//                protected void onPostExecute(List<Anime> animeList) {
//                    super.onPostExecute(animeList);
//
//                    View loadingIndicator = view.findViewById(R.id.loading_indicator);
//                    loadingIndicator.setVisibility(View.GONE);
//
//                    if (animeList == null && animeList.isEmpty()) {
//                        mEmptyStateTextView.setText("No Data");
//                    }
//
//                    if (animeList != null && !animeList.isEmpty()) {
//                        mAnimeList.clear();
//                        mAnimeList.addAll(animeList);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//            }.execute();
//
//            LoaderManager loaderManager = getActivity().getLoaderManager();
//
//            loaderManager.initLoader(1, null, this);
//
//        } else {
//
//            View loadingIndicator = view.findViewById(R.id.loading_indicator);
//            loadingIndicator.setVisibility(View.GONE);
//
//            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }
        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//
//    }

//    @Override
//    public Loader<List<Anime>> onCreateLoader(int id, Bundle args) {
//        Log.d("onCreateLoader", mAnimeCategory);
////        return new AnimeLoader(getActivity(), mAnimeCategory);
//        return null;
//    }

//    @Override
//    public void onLoadFinished(Loader<List<Anime>> loader, List<Anime> data) {
//
////        View loadingIndicator = view.findViewById(R.id.loading_indicator);
////        loadingIndicator.setVisibility(View.GONE);
////
////        if (data == null && data.isEmpty()) {
////            mEmptyStateTextView.setText("No Data");
////        }
////
////        if (data != null && !data.isEmpty()) {
////            mAnimeList.clear();
////            mAnimeList.addAll(data);
////            mAdapter.notifyDataSetChanged();
////        }
//    }

//    @Override
//    public void onLoaderReset(Loader<List<Anime>> loader) {
//        // mAnimeList.clear();
//    }

}
