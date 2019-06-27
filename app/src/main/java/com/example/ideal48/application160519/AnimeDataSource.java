package com.example.ideal48.application160519;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AnimeDataSource extends PageKeyedDataSource<Integer, Anime> {

    private final GetDataService mService;
    private final String mAnimeCategory;
    private final MutableLiveData<RequestFailure> requestFailureLiveData;

    public AnimeDataSource(GetDataService service, String animeCategory) {
        mService = service;
        mAnimeCategory = animeCategory;
        this.requestFailureLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Anime> callback) {

        final int page = 1;
        Call<AnimeResponse> call;

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

        Callback<AnimeResponse> requestCallback = new Callback<AnimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<AnimeResponse> call, Response<AnimeResponse> response) {
                AnimeResponse animeResponse = response.body();

                if (animeResponse == null) {
                    onFailure(call, new HttpException(response));
                    return;
                }

                callback.onResult(animeResponse.getmTop(), 0, 50, null, page + 1);
            }

            @Override
            public void onFailure(@NonNull Call<AnimeResponse> call, @NonNull Throwable t) {

                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadInitial(params, callback);
                    }
                };

                handleError(retryable, t);

            }
        };
        call.enqueue(requestCallback);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Anime> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Anime> callback) {

        final int page = params.key;

        Call<AnimeResponse> call;

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

        Callback<AnimeResponse> requestCallback = new Callback<AnimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<AnimeResponse> call, @NonNull Response<AnimeResponse> response) {
                AnimeResponse animeResponse = response.body();

                if (animeResponse == null) {
                    onFailure(call, new HttpException(response));
                    return;
                }

                callback.onResult(
                        animeResponse.getmTop(),
                        page + 1
                );
            }

            @Override
            public void onFailure(@NonNull Call<AnimeResponse> call, @NonNull Throwable t) {

                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadAfter(params, callback);
                    }
                };

                handleError(retryable, t);

            }
        };

        call.enqueue(requestCallback);

    }

    public LiveData<RequestFailure> getRequestFailureLiveData() {
        return requestFailureLiveData;
    }

    private void handleError(Retryable retryable, Throwable t) {
        requestFailureLiveData.postValue(new RequestFailure(retryable, t.getMessage()));
    }

}
