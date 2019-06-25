package com.example.ideal48.application160519;

import com.example.ideal48.application160519.model.AnimeDetails;
import com.example.ideal48.application160519.model.AnimeResponse;
import com.example.ideal48.application160519.model.AnimeSearchResponse;
import com.example.ideal48.application160519.model.EpisodesResponse;
import com.example.ideal48.application160519.model.ForumResponse;
import com.example.ideal48.application160519.model.NewsResponse;
import com.example.ideal48.application160519.model.PicturesResponse;
import com.example.ideal48.application160519.model.RecommendationsResponse;
import com.example.ideal48.application160519.model.ReviewsResponse;
import com.example.ideal48.application160519.model.StatsResponse;
import com.example.ideal48.application160519.model.VideosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("top/anime/{page}")
    Call<AnimeResponse> getAllAnime(@Path("page") int pageIndex);

    @GET("top/anime/{page}/airing")
    Call<AnimeResponse> getAiring(@Path("page") int pageIndex);

    @GET("top/anime/{page}/upcoming")
    Call<AnimeResponse> getUpcoming(@Path("page") int pageIndex);

    @GET("top/anime/{page}/tv")
    Call<AnimeResponse> getTV(@Path("page") int pageIndex);

    @GET("top/anime/{page}/movie")
    Call<AnimeResponse> getMovie(@Path("page") int pageIndex);

    @GET("top/anime/{page}/ova")
    Call<AnimeResponse> getOva(@Path("page") int pageIndex);

    @GET("top/anime/{page}/special")
    Call<AnimeResponse> getSpecial(@Path("page") int pageIndex);

    @GET("top/anime/{page}/bypopularity")
    Call<AnimeResponse> getBypopularity(@Path("page") int pageIndex);

    @GET("top/anime/{page}/favorite")
    Call<AnimeResponse> getFavorite(@Path("page") int pageIndex);


    @GET("anime/{id}/characters_staff")
    Call<AnimeDetails> getAnimeDetails(@Path("id") int malId);

    @GET("v3/anime/{id}/videos")
    Call<VideosResponse> getVideosResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/episodes/1")
    Call<EpisodesResponse> getEpisodesResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/reviews/1")
    Call<ReviewsResponse> getReviewsResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/recommendations")
    Call<RecommendationsResponse> getRecommendationsResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/stats")
    Call<StatsResponse> getStatsResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/news")
    Call<NewsResponse> getNewsResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/forum")
    Call<ForumResponse> getForumResponse(@Path("id") int malId);

    @GET("v3/anime/{id}/pictures")
    Call<PicturesResponse> getPicturesResponse(@Path("id") int malId);


    @GET("v3/search/anime")
    Call<AnimeSearchResponse> getSearchResults(@Query("q") String query, @Query("page") int pageNumber);
}