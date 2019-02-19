package com.example.cinemato.datamodel;

import android.support.annotation.NonNull;

import com.example.cinemato.BuildConfig;
import com.example.cinemato.model.DetailResponse;
import com.example.cinemato.model.DirectorCreditResponse;
import com.example.cinemato.model.DirectorId;
import com.example.cinemato.model.Id;
import com.example.cinemato.model.MovieResponse;
import com.example.cinemato.model.Page;
import com.example.cinemato.model.PersonResponse;
import com.example.cinemato.model.SearchActorQuery;
import com.example.cinemato.model.SearchActorResponse;
import com.example.cinemato.rest.ApiClient;
import com.example.cinemato.rest.ApiInterface;

import io.reactivex.Observable;

public class DataModel implements IDataModel {

    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private static final String appendToResponse = "release_dates,credits,videos,recommendations,images,casts,reviews";
    private static final String personAppendToResponse = "tagged_images,images,movie_credits";
    final private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    @NonNull
    @Override
    public Observable<SearchActorResponse> getSearchActor(@NonNull final SearchActorQuery s) {

        switch (s.getmSelector()) {
            case 0:
                return apiInterface.getSearchActors(API_KEY, s.getmQuery(), s.getmPage());
            case 1:
                return apiInterface.getPopularPeople(API_KEY, s.getmPage());
            default:
                return Observable.empty();
        }

    }

    @NonNull
    @Override
    public Observable<DetailResponse> getDetail(@NonNull final Id id) {

        return apiInterface.getMovieDetails(id.getId(), API_KEY, appendToResponse);
    }


    @NonNull
    @Override
    public Observable<DirectorCreditResponse> getCredit(@NonNull final DirectorId id) {

        return apiInterface.getDirectorCredits(id.getId(), API_KEY);
    }

    @NonNull
    @Override
    public Observable<PersonResponse> getPerson(@NonNull Id id) {
        return apiInterface.getPerson(id.getId(), API_KEY, personAppendToResponse);
    }


    @NonNull
    @Override
    public Observable<MovieResponse> getMoviesByCode(@NonNull final Page page) {
        switch (page.getmPosition()) {
            case 0:
                return apiInterface.getNowPlayingMovies(API_KEY, LANGUAGE, page.getmPage());
            case 1:
                return apiInterface.getPopularMovies(API_KEY, LANGUAGE, page.getmPage());
            case 2:
                return apiInterface.getUpcomingMovies(API_KEY, LANGUAGE, page.getmPage());
            case 3:
                return apiInterface.getTopRatedMovies(API_KEY, LANGUAGE, page.getmPage());
            case 4:  //search movies
                return apiInterface.getSearchMovies(API_KEY, page.getmQuery(), page.getmPage());
            default:
                return Observable.empty();
        }
    }


}
