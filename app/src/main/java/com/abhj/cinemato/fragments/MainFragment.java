package com.abhj.cinemato.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abhj.cinemato.CinemaApplication;
import com.abhj.cinemato.R;
import com.abhj.cinemato.adapters.MainListAdapter;
import com.abhj.cinemato.model.Movie;
import com.abhj.cinemato.model.Page;
import com.abhj.cinemato.viewModel.MainFragmentViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_VIEWPAGER_POSITION = "page-code";
    private static final String ARG_SEARCH_QUERY = "search-query";
    Page myPage;
    RecyclerView.OnItemTouchListener listener;
    @BindView(R.id.main_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private int mColumnCount = 1;
    private int mViewpagerPosition;
    private int mCurrentPage = 1;
    private boolean mIsFetching;
    private String mSearchQuery;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private MainFragmentViewModel mViewModel;
    @Nullable
    private MainListAdapter mMainListAdapter;
    private Unbinder unbinder;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(int columnCount, int position) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_VIEWPAGER_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    //new instance when we have search query
    public static MainFragment newInstance(int position, String query) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIEWPAGER_POSITION, position);
        args.putString(ARG_SEARCH_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mViewpagerPosition = getArguments().getInt(ARG_VIEWPAGER_POSITION);
            mSearchQuery = getArguments().getString(ARG_SEARCH_QUERY);
        }

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);
        mViewModel = getViewModel();
        bind();
        swipeEnhancer(mRecyclerView);
        setupOnScrollListener(mRecyclerView);
        setupRecyclerView(mRecyclerView);

        return view;
    }


    private void setupRecyclerView(RecyclerView r) {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        r.addItemDecoration(itemDecoration);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        unBind();
    }


    private void bind() {
        myPage = new Page(mViewpagerPosition, mCurrentPage, mSearchQuery);
        mViewModel.pageChanged(myPage);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mViewModel.getMoviesByCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> movies) {
                        setMovies(movies);
                        mIsFetching = false;
                        mCurrentPage = mCurrentPage + 1;
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Please check your Internet connection",
                                Toast.LENGTH_SHORT).show();

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

        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
            mRecyclerView.removeOnItemTouchListener(listener);
        }

    }


    private void setMovies(@NonNull final List<Movie> movies) {
        if (mMainListAdapter == null) {
            assert mRecyclerView != null;
            mMainListAdapter = new MainListAdapter(movies, getContext());
            mRecyclerView.setAdapter(mMainListAdapter);
        } else {
            mMainListAdapter.appendMovies(movies);
        }
    }


    @NonNull
    private MainFragmentViewModel getViewModel() {
        return ((CinemaApplication)
                (Objects.requireNonNull(getActivity()).getApplication()))
                .getViewModel();
    }


    // viewPager swipe priority over recyclerView scrolling
    private void swipeEnhancer(RecyclerView recyclerView) {

        listener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN && rv.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                    rv.stopScroll();
                    return false;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }
        };


        assert recyclerView != null;
        recyclerView.addOnItemTouchListener(listener);

    }


    private void setupOnScrollListener(@NonNull RecyclerView recyclerView) {
        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                assert manager != null;
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount) {

                    if (!mIsFetching) {
                        myPage = new Page(mViewpagerPosition, mCurrentPage + 1, mSearchQuery);
                        mViewModel.pageChanged(myPage);
                        mIsFetching = true;
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


}
