package com.abhj.cinemato.viewModel;

import android.support.annotation.NonNull;

import com.abhj.cinemato.datamodel.IDataModel;
import com.abhj.cinemato.model.ActorObject;
import com.abhj.cinemato.model.SearchActorQuery;
import com.abhj.cinemato.model.SearchActorResponse;
import com.abhj.cinemato.schedulers.ISchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SearchActivityViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorSubject<SearchActorQuery> mSearch = BehaviorSubject.create();


    public SearchActivityViewModel(@NonNull final IDataModel dataModel,
                                   @NonNull final ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }


    @NonNull
    public Observable<List<ActorObject>> getSearchActor() {
        return mSearch
                .observeOn(mSchedulerProvider.io())
                .flatMap(mDataModel::getSearchActor)
                .map(SearchActorResponse::getResults);
    }

    public void queryChanged(final SearchActorQuery s) {
        mSearch.onNext(s);
    }
}
