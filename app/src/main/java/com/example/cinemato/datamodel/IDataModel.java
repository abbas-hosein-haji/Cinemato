package com.example.cinemato.datamodel;

import android.support.annotation.NonNull;

import com.example.cinemato.model.DetailResponse;
import com.example.cinemato.model.DirectorCreditResponse;
import com.example.cinemato.model.DirectorId;
import com.example.cinemato.model.Id;
import com.example.cinemato.model.MovieResponse;
import com.example.cinemato.model.Page;
import com.example.cinemato.model.PersonResponse;
import com.example.cinemato.model.SearchActorQuery;
import com.example.cinemato.model.SearchActorResponse;

import io.reactivex.Observable;

public interface IDataModel {

    @NonNull
    Observable<DetailResponse> getDetail(@NonNull final Id id);

    @NonNull
    Observable<MovieResponse> getMoviesByCode(@NonNull final Page page);

    @NonNull
    Observable<DirectorCreditResponse> getCredit(@NonNull final DirectorId id);

    @NonNull
    Observable<PersonResponse> getPerson(@NonNull final Id id);

    @NonNull
    Observable<SearchActorResponse> getSearchActor(@NonNull final SearchActorQuery s);


}
