package com.abhj.cinemato.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface ISchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
