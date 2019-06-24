package com.example.ideal48.application160519.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.AnimeImagePopupWindow;
import com.example.ideal48.application160519.GetDataService;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.RetrofitClientInstance;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.adapter.AnimeDetailsPagerAdapter;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeDetails;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeDetailsActivity extends AppCompatActivity {

    public static int malId;
    public static GetDataService service;
    public static AnimeDetails animeDetails = new AnimeDetails();
    View animeDetailsParentView;

    ProgressDialog progressDialog;
    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        malId = getIntent().getIntExtra("malId", 0);

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimeDetailsActivity.this.onBackPressed();
            }
        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        picasso = picasso.with(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(this);
        final UserDao userDao = userRoomDatabase.userDao();

        final ImageView posterIV = findViewById(R.id.poster_iv);
        final TextView titleTV = findViewById(R.id.title_tv);
        final TextView scoreTV = findViewById(R.id.score_tv);
        final TextView scoreByTV = findViewById(R.id.score_by_tv);
        final ImageView favBtnIV = findViewById(R.id.activity_anime_fav_iv);
        final TextView rankTV = findViewById(R.id.rank_tv);
        final TextView popularityTV = findViewById(R.id.popularity_tv);
        final TextView membersTV = findViewById(R.id.members_tv);
        animeDetailsParentView = findViewById(R.id.anime_details_parent_view);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<AnimeDetails> call = service.getAnimeDetails(malId);
        call.enqueue(new Callback<AnimeDetails>() {
            @Override
            public void onResponse(Call<AnimeDetails> call, Response<AnimeDetails> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    animeDetails = response.body();

                    titleTV.setText(animeDetails.getmTitle());

                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        boolean isShow = true;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                collapsingToolbar.setTitle(animeDetails.getmTitle());
                                titleTV.setVisibility(View.GONE);
                                isShow = true;
                            } else if (isShow) {
                                collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                                titleTV.setVisibility(View.VISIBLE);
                                isShow = false;
                            }
                        }
                    });

                    picasso.load(animeDetails.getmImageUrl()).into(posterIV);

                    posterIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AnimeImagePopupWindow(AnimeDetailsActivity.this,
                                    R.layout.image_popup_window_layout, animeDetailsParentView, animeDetails.getmImageUrl(), null);
                        }
                    });

                    scoreTV.setText(String.valueOf(animeDetails.getmScore()));
                    scoreByTV.setText(String.valueOf(animeDetails.getmScoredBy()) + " users");

                    new AsyncTask<Void, Void, Anime>() {
                        @Override
                        protected Anime doInBackground(Void... voids) {
                            Anime anime = userDao.isAnimeFav(malId);
                            return anime;
                        }

                        @Override
                        protected void onPostExecute(Anime anime) {
                            super.onPostExecute(anime);
                            if (anime == null) {
                                favBtnIV.setImageResource(R.drawable.ic_favorite_border);
                            } else {
                                favBtnIV.setImageResource(R.drawable.ic_favorite);
                            }
                        }
                    }.execute();

                    favBtnIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AsyncTask<Void, Void, Anime>() {
                                @Override
                                protected Anime doInBackground(Void... voids) {
                                    Anime anime = userDao.isAnimeFav(malId);
                                    return anime;
                                }

                                @Override
                                protected void onPostExecute(Anime anime) {
                                    super.onPostExecute(anime);

                                    if (anime == null) {
                                        final Anime setFav = new Anime(malId, animeDetails.getmTitle(),
                                                animeDetails.getmImageUrl(), animeDetails.getmType(),
                                                animeDetails.getmEpisodes(), animeDetails.getmScore());
                                        new AsyncTask<Void, Void, Void>() {
                                            @Override
                                            protected Void doInBackground(Void... voids) {
                                                userDao.insertFavAnime(setFav);
                                                return null;
                                            }
                                        }.execute();
                                        favBtnIV.setImageResource(R.drawable.ic_favorite);
                                    } else {
                                        final Anime delFav = new Anime(malId, animeDetails.getmTitle(),
                                                animeDetails.getmImageUrl(), animeDetails.getmType(),
                                                animeDetails.getmEpisodes(), animeDetails.getmScore());
                                        new AsyncTask<Void, Void, Void>() {
                                            @Override
                                            protected Void doInBackground(Void... voids) {
                                                userDao.deleteFavAnime(delFav);
                                                return null;
                                            }
                                        }.execute();
                                        favBtnIV.setImageResource(R.drawable.ic_favorite_border);
                                    }
                                }
                            }.execute();
                        }
                    });

                    rankTV.setText(String.valueOf(animeDetails.getmRank()));
                    popularityTV.setText(String.valueOf(animeDetails.getmPopularity()));
                    membersTV.setText(String.valueOf(animeDetails.getmMembers()));

                    TabLayout detailsTab = findViewById(R.id.details_tab);
                    ViewPager detailsViewPager = findViewById(R.id.details_viewpager);

                    AnimeDetailsPagerAdapter adapter = new AnimeDetailsPagerAdapter(AnimeDetailsActivity.this, getSupportFragmentManager());

                    detailsViewPager.setAdapter(adapter);
                    detailsTab.setupWithViewPager(detailsViewPager);
                } else {
                    Toast.makeText(AnimeDetailsActivity.this, "Details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeDetails> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AnimeDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
