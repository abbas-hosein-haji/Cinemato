package com.abhj.cinemato.datamodel;

import android.support.annotation.NonNull;

import com.abhj.cinemato.model.DetailResponse;
import com.abhj.cinemato.model.DirectorCreditResponse;
import com.abhj.cinemato.model.DirectorId;
import com.abhj.cinemato.model.Id;
import com.abhj.cinemato.model.MovieResponse;
import com.abhj.cinemato.model.Page;
import com.abhj.cinemato.model.PersonResponse;
import com.abhj.cinemato.model.SearchActorQuery;
import com.abhj.cinemato.model.SearchActorResponse;

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
