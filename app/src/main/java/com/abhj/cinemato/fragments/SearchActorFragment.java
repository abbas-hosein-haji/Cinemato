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

import com.abhj.cinemato.CinemaApplication;
import com.abhj.cinemato.R;
import com.abhj.cinemato.adapters.SearchAdapter;
import com.abhj.cinemato.model.ActorObject;
import com.abhj.cinemato.model.SearchActorQuery;
import com.abhj.cinemato.viewModel.SearchActivityViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchActorFragment extends Fragment {

    private static final String ARG_QUERY = "actorQuery";
    private static final String ARG_SELECTOR = "selector";
    @BindView(R.id.search_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.search_progressBar)
    ProgressBar progressBar;
    SearchActorQuery mySearch;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private SearchActivityViewModel mViewModel;
    @Nullable
    private SearchAdapter adapter;
    private Unbinder unbinder;
    private int mCurrentPage = 1;
    private String mSearchQuery;
    private int mSelector = 0;
    private boolean mIsFetching;

    public SearchActorFragment() {
        // Required empty public constructor
    }


    public static SearchActorFragment newInstance(String query) {
        SearchActorFragment fragment = new SearchActorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchActorFragment newInstance(int s) {
        SearchActorFragment fragment = new SearchActorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SELECTOR, s);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSearchQuery = getArguments().getString(ARG_QUERY);
            mSelector = getArguments().getInt(ARG_SELECTOR);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_actor, container, false);


        unbinder = ButterKnife.bind(this, view);
        mViewModel = getSearchViewModel();
        bind();
        setupOnScrollListener(recyclerView);
        setupRecyclerView(recyclerView);
        swipeEnhancer(recyclerView);


        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        unBind();
    }


    private void setupRecyclerView(RecyclerView r) {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        r.addItemDecoration(itemDecoration);
    }


    private void bind() {
        mySearch = new SearchActorQuery(mSelector, mCurrentPage, mSearchQuery);
        mViewModel.queryChanged(mySearch);
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(mViewModel.getSearchActor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ActorObject>>() {
                    @Override
                    public void onNext(List<ActorObject> actors) {
                        setupSearchViews(actors);
                        mIsFetching = false;
                        mCurrentPage = mCurrentPage + 1;
                        progressBar.setVisibility(View.GONE);
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

        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
    }


    private void setupSearchViews(List<ActorObject> actor) {
        if (adapter == null) {
            assert recyclerView != null;
            adapter = new SearchAdapter(actor, getContext());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.appendActors(actor);
        }

    }

    @NonNull
    private SearchActivityViewModel getSearchViewModel() {
        return ((CinemaApplication) Objects.requireNonNull(getActivity()).getApplication()).getSearchViewModel();
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
                        mySearch = new SearchActorQuery(mSelector, mCurrentPage + 1, mSearchQuery);
                        mViewModel.queryChanged(mySearch);
                        mIsFetching = true;
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    // viewPager swipe priority over recyclerView scrolling
    private void swipeEnhancer(RecyclerView recyclerView) {
        assert recyclerView != null;
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        });
    }


}
