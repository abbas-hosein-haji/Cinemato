package com.example.cinemato.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemato.CinemaApplication;
import com.example.cinemato.R;
import com.example.cinemato.adapters.CastAdapter;
import com.example.cinemato.model.DetailResponse;
import com.example.cinemato.model.Id;
import com.example.cinemato.viewModel.DetailsActivityViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CastFragment extends Fragment {

    private static final String ARG_ID = "id";
    @BindView(R.id.cast_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nested_container_cast)
    NestedScrollView nestedScrollView;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private DetailsActivityViewModel mViewModel;
    @Nullable
    private CastAdapter adapter;
    private Unbinder unbinder;
    private int mIdFromActivity;


    public CastFragment() {
        // Required empty public constructor
    }

    public static CastFragment newInstance(int id) {
        CastFragment fragment = new CastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIdFromActivity = getArguments().getInt(ARG_ID);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cast, container, false);

        unbinder = ButterKnife.bind(this, view);
        mViewModel = getDetailViewModel();
        bind();
        enableTouchTheftFromParent(nestedScrollView);

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unBind();
    }

    private void bind() {
        mViewModel.idChanged(new Id(mIdFromActivity));
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(mViewModel.getDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DetailResponse>() {
                    @Override
                    public void onNext(DetailResponse detailResponse) {
                        setupCastViews(detailResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );

    }

    private void unBind() {
        mCompositeDisposable.clear();
        //butteKnife
        unbinder.unbind();
    }


    private void setupCastViews(DetailResponse detailResponse) {
        adapter = new CastAdapter(detailResponse.getCastsResponse().getCast(), getContext());
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }

    @NonNull
    private DetailsActivityViewModel getDetailViewModel() {
        return ((CinemaApplication) Objects.requireNonNull(getActivity()).getApplication()).getDetailViewModel();
    }


    public void enableTouchTheftFromParent(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // by setting this to false we allow parent view to take control of touch
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
    }


}
