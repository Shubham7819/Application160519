package com.example.ideal48.application160519;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeResponse;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

public class AnimeDataSource extends PageKeyedDataSource<String, Anime> {

    private static final String TAG = AnimeDataSource.class.getSimpleName();
    private final GetDataService mService;
    private final String mAnimeCategory;
    private final MutableLiveData networkState;
    ConnectivityManager connectivityManager;
    Context mContext;

    public AnimeDataSource(Context context, String animeCategory) {
        mService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        mAnimeCategory = animeCategory;
        mContext = context;
        this.networkState = new MutableLiveData();

        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Anime> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            final int page = 1;
            Call<ArrayList<Anime>> call;

            if (mAnimeCategory.equals("all")) {
                call = mService.getAllAnime(page);
            } else if (mAnimeCategory.equals("airing")) {
                call = mService.getAiring(page);
            } else if (mAnimeCategory.equals("upcoming")) {
                call = mService.getUpcoming(page);
            } else if (mAnimeCategory.equals("tv")) {
                call = mService.getTV(page);
            } else if (mAnimeCategory.equals("movie")) {
                call = mService.getMovie(page);
            } else if (mAnimeCategory.equals("ova")) {
                call = mService.getOva(page);
            } else if (mAnimeCategory.equals("special")) {
                call = mService.getSpecial(page);
            } else if (mAnimeCategory.equals("bypopularity")) {
                call = mService.getBypopularity(page);
            } else {
                call = mService.getFavorite(page);
            }

            call.enqueue(new Callback<ArrayList<Anime>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Anime>> call, Response<ArrayList<Anime>> response) {
                    ArrayList animeResponse = response.body();

                    if (response.isSuccessful()) {
                        callback.onResult(animeResponse, Integer.toString(1), Integer.toString(2));
                        networkState.postValue(NetworkState.LOADED);
                    } else {
                        Log.e("API CALL", response.message());
                        Retryable retryable = new Retryable() {
                            @Override
                            public void retry() {
                                loadInitial(params, callback);
                            }
                        };
//                    onFailure(call, new HttpException(response));
                        networkState.postValue(new NetworkState(retryable, NetworkState.Status.FAILED, response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Anime>> call, @NonNull Throwable t) {

                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadInitial(params, callback);
                    }
                };

                handleError(retryable, t);
                    String errorMessage;
                    if (t.getMessage() == null) {
                        errorMessage = "unknown error";
                    } else {
                        errorMessage = t.getMessage();
                    }
                    networkState.postValue(new NetworkState(retryable, NetworkState.Status.FAILED, errorMessage));
                    callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
                }
            });
        } else {
            Retryable retryable = new Retryable() {
                @Override
                public void retry() {
                    loadInitial(params, callback);
                }
            };
            NetworkState networkState1 = new NetworkState(retryable, NetworkState.Status.FAILED, mContext.getString(R.string.no_internet));
            networkState1.setRetryable(retryable);
            networkState.postValue(networkState1);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Anime> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, Anime> callback) {
        Log.i(TAG, "Loading page " + params.key );

        networkState.postValue(NetworkState.LOADING);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            final AtomicInteger page = new AtomicInteger(0);
            try {
                page.set(Integer.parseInt(params.key));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Call<ArrayList<Anime>> call;

            if (mAnimeCategory.equals("all")) {
                call = mService.getAllAnime(page.get());
            } else if (mAnimeCategory.equals("airing")) {
                call = mService.getAiring(page.get());
            } else if (mAnimeCategory.equals("upcoming")) {
                call = mService.getUpcoming(page.get());
            } else if (mAnimeCategory.equals("tv")) {
                call = mService.getTV(page.get());
            } else if (mAnimeCategory.equals("movie")) {
                call = mService.getMovie(page.get());
            } else if (mAnimeCategory.equals("ova")) {
                call = mService.getOva(page.get());
            } else if (mAnimeCategory.equals("special")) {
                call = mService.getSpecial(page.get());
            } else if (mAnimeCategory.equals("bypopularity")) {
                call = mService.getBypopularity(page.get());
            } else {
                call = mService.getFavorite(page.get());
            }

            call.enqueue(new Callback<ArrayList<Anime>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Anime>> call, @NonNull Response<ArrayList<Anime>> response) {
                    ArrayList animeResponse = response.body();

                    if (response.isSuccessful()) {
                        callback.onResult(animeResponse, Integer.toString(page.get() + 1));
                        networkState.postValue(NetworkState.LOADED);
                    } else {
                        Retryable retryable = new Retryable() {
                            @Override
                            public void retry() {
                                loadAfter(params, callback);
                            }
                        };
//                    onFailure(call, new HttpException(response));
                        networkState.postValue(new NetworkState(retryable, NetworkState.Status.FAILED, response.message()));
                        Log.e("API CALL", response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Anime>> call, @NonNull Throwable t) {

                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadAfter(params, callback);
                    }
                };

                handleError(retryable, t);
                    String errorMessage;
                    if (t.getMessage() == null) {
                        errorMessage = "unknown error";
                    } else {
                        errorMessage = t.getMessage();
                    }
                    networkState.postValue(new NetworkState(retryable, NetworkState.Status.FAILED, errorMessage));
                    callback.onResult(new ArrayList<>(), Integer.toString(page.get()));
                }
            });
        } else {

            Retryable retryable = new Retryable() {
                @Override
                public void retry() {
                    loadAfter(params, callback);
                }
            };

            networkState.postValue(new NetworkState(retryable, NetworkState.Status.FAILED, mContext.getString(R.string.no_internet)));
        }

    }

    public LiveData<NetworkState> getRequestFailureLiveData() {
        return networkState;
    }

    private void handleError(Retryable retryable, Throwable t) {
        networkState.postValue(new RequestFailure(retryable, t.getMessage()));
    }

}
