package com.abhj.cinemato.viewModel;

import android.support.annotation.NonNull;

import com.abhj.cinemato.datamodel.IDataModel;
import com.abhj.cinemato.model.DetailResponse;
import com.abhj.cinemato.model.DirectorCreditResponse;
import com.abhj.cinemato.model.DirectorId;
import com.abhj.cinemato.model.Id;
import com.abhj.cinemato.schedulers.ISchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DetailsActivityViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorSubject<Id> mId = BehaviorSubject.create();

    @NonNull
    private final BehaviorSubject<DirectorId> mDirectorId = BehaviorSubject.create();


    public DetailsActivityViewModel(@NonNull final IDataModel dataModel,
                                    @NonNull final ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }


    @NonNull
    public Observable<DetailResponse> getDetail() {
        return mId
                .observeOn(mSchedulerProvider.io())
                .flatMap(mDataModel::getDetail);
    }


    @NonNull
    public Observable<DirectorCreditResponse> getCredit() {
        return mDirectorId
                .observeOn(mSchedulerProvider.io())
                .flatMap(mDataModel::getCredit);
    }


    public void idChanged(final Id id) {
        mId.onNext(id);
    }


    public void directorIdChanged(final DirectorId id) {
        mDirectorId.onNext(id);
    }

}
