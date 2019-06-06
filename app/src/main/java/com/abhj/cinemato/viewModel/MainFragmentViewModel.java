package com.abhj.cinemato.viewModel;

import android.support.annotation.NonNull;

import com.abhj.cinemato.datamodel.IDataModel;
import com.abhj.cinemato.model.Movie;
import com.abhj.cinemato.model.MovieResponse;
import com.abhj.cinemato.model.Page;
import com.abhj.cinemato.schedulers.ISchedulerProvider;

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
