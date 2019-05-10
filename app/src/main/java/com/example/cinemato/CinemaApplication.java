package com.example.cinemato;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.example.cinemato.datamodel.DataModel;
import com.example.cinemato.datamodel.IDataModel;
import com.example.cinemato.schedulers.ISchedulerProvider;
import com.example.cinemato.schedulers.SchedulerProvider;
import com.example.cinemato.viewModel.DetailsActivityViewModel;
import com.example.cinemato.viewModel.MainFragmentViewModel;
import com.example.cinemato.viewModel.PersonActivityViewModel;
import com.example.cinemato.viewModel.SearchActivityViewModel;


public class CinemaApplication extends Application {

    @NonNull
    private final IDataModel mDataModel;

    public CinemaApplication() {
        mDataModel = new DataModel();
    }

    @NonNull
    public IDataModel getDataModel() {
        return mDataModel;
    }

    @NonNull
    public ISchedulerProvider getSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @NonNull
    public MainFragmentViewModel getViewModel() {
        return new MainFragmentViewModel(getDataModel(), getSchedulerProvider());
    }


    @NonNull
    public DetailsActivityViewModel getDetailViewModel() {
        return new DetailsActivityViewModel(getDataModel(), getSchedulerProvider());
    }


    @NonNull
    public PersonActivityViewModel getPersonViewModel() {
        return new PersonActivityViewModel(getDataModel(), getSchedulerProvider());
    }

    @NonNull
    public SearchActivityViewModel getSearchViewModel() {
        return new SearchActivityViewModel(getDataModel(), getSchedulerProvider());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        // Normal app init code...
//    }

}