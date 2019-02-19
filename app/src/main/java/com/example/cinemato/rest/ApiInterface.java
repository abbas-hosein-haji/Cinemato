package com.example.cinemato.rest;


import com.example.cinemato.model.DetailResponse;
import com.example.cinemato.model.DirectorCreditResponse;
import com.example.cinemato.model.MovieResponse;
import com.example.cinemato.model.PersonResponse;
import com.example.cinemato.model.SearchActorResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/now_playing")
    Observable<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("movie/{id}")
    Observable<DetailResponse> getMovieDetails(
            @Path("id") int id
            , @Query("api_key") String apiKey
            , @Query("append_to_response") String appendToResponse
    );


    @GET("person/{director_id}/movie_credits")
    Observable<DirectorCreditResponse> getDirectorCredits(
            @Path("director_id") int id
            , @Query("api_key") String apiKey);


    @GET("person/{person_id}")
    Observable<PersonResponse> getPerson(
            @Path("person_id") int id
            , @Query("api_key") String apiKey
            , @Query("append_to_response") String appendToResponse
    );


    @GET("search/movie")
    Observable<MovieResponse> getSearchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );


    @GET("search/person")
    Observable<SearchActorResponse> getSearchActors(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );


    @GET("person/popular")
    Observable<SearchActorResponse> getPopularPeople(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

}
