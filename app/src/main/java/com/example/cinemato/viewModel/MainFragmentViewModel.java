package com.example.cinemato.viewModel;

import android.support.annotation.NonNull;

import com.example.cinemato.datamodel.IDataModel;
import com.example.cinemato.model.Movie;
import com.example.cinemato.model.MovieResponse;
import com.example.cinemato.model.Page;
import com.example.cinemato.schedulers.ISchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class MainFragmentViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorSubject<Page> mPage = BehaviorSubject.create();


    public MainFragmentViewModel(@NonNull final IDataModel dataModel,
                                 @NonNull final ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }


    @NonNull
    public Observable<List<Movie>> getMoviesByCode() {
        return mPage
                .observeOn(mSchedulerProvider.io())
                .flatMap(mDataModel::getMoviesByCode)
                .map(MovieResponse::getResults);
    }


    public void pageChanged(final Page page) {
        mPage.onNext(page);
    }


}
