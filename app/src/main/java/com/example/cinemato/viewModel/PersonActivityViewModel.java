package com.example.cinemato.viewModel;

import android.support.annotation.NonNull;

import com.example.cinemato.datamodel.IDataModel;
import com.example.cinemato.model.Id;
import com.example.cinemato.model.PersonResponse;
import com.example.cinemato.schedulers.ISchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class PersonActivityViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorSubject<Id> mId = BehaviorSubject.create();


    public PersonActivityViewModel(@NonNull final IDataModel dataModel,
                                   @NonNull final ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    public Observable<PersonResponse> getPerson() {
        return mId
                .observeOn(mSchedulerProvider.io())
                .flatMap(mDataModel::getPerson);
    }


    public void idChanged(final Id id) {
        mId.onNext(id);
    }
}
