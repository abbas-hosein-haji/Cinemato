package com.abhj.cinemato.datamodel;

import android.support.annotation.NonNull;

import com.abhj.cinemato.BuildConfig;
import com.abhj.cinemato.model.DetailResponse;
import com.abhj.cinemato.model.DirectorCreditResponse;
import com.abhj.cinemato.model.DirectorId;
import com.abhj.cinemato.model.Id;
import com.abhj.cinemato.model.MovieResponse;
import com.abhj.cinemato.model.Page;
import com.abhj.cinemato.model.PersonResponse;
import com.abhj.cinemato.model.SearchActorQuery;
import com.abhj.cinemato.model.SearchActorResponse;
import com.abhj.cinemato.rest.ApiClient;
import com.abhj.cinemato.rest.ApiInterface;

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
