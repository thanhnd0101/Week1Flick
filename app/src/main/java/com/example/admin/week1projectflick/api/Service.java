package com.example.admin.week1projectflick.api;

import com.example.admin.week1projectflick.model.MovieResponse;
import com.example.admin.week1projectflick.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

    //@GET("movie/top_rated")
    //Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String api_key);

    @GET("movie/{id}/trailers")
    Call<TrailerResponse> getTrailerMovie(@Path("id") int id, @Query("api_key") String api_key);
}
