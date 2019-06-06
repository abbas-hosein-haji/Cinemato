package com.abhj.cinemato.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhj.cinemato.CinemaApplication;
import com.abhj.cinemato.R;
import com.abhj.cinemato.adapters.DirectorAdapter;
import com.abhj.cinemato.adapters.RecommendAdapter;
import com.abhj.cinemato.adapters.TrailerAdapter;
import com.abhj.cinemato.model.CrewObject;
import com.abhj.cinemato.model.DetailResponse;
import com.abhj.cinemato.model.DirectorCreditResponse;
import com.abhj.cinemato.model.DirectorCrewObject;
import com.abhj.cinemato.model.DirectorId;
import com.abhj.cinemato.model.Id;
import com.abhj.cinemato.model.RecommendObject;
import com.abhj.cinemato.model.VideoObjects;
import com.abhj.cinemato.viewModel.DetailsActivityViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
public class InfoFragment extends Fragment {

    private static final String ARG_ID = "id";
    @BindView(R.id.trailer_recyclerView)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.director_recyclerView)
    RecyclerView directorRecyclerView;
    @BindView(R.id.recommend_recyclerView)
    RecyclerView recommendRecyclerView;
    @BindView(R.id.info_overView)
    TextView overView;
    @BindView(R.id.originalTitle)
    TextView originalTitle;
    @BindView(R.id.originalLanguage)
    TextView originalLanguage;
    @BindView(R.id.directedBy)
    TextView directedBy;
    @BindView(R.id.budget)
    TextView budget;
    @BindView(R.id.homepage)
    TextView homepage;
    @BindView(R.id.revenue)
    TextView revenue;
    @BindView(R.id.director_title)
    TextView directorTitle;
    @BindView(R.id.trailer_text_title)
    TextView trailerTitle;
    @BindView(R.id.recommend_text_title)
    TextView recommendTitle;
    @BindView(R.id.trailer_line)
    View trilarLine;
    @BindView(R.id.director_line)
    View directorLine;
    @BindView(R.id.nested_container_movie_info)
    View nestedScrollView;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private DetailsActivityViewModel mViewModel;
    @Nullable
    private TrailerAdapter trailerAdapter;
    @Nullable
    private DirectorAdapter directorAdapter;
    @Nullable
    private RecommendAdapter recommendAdapter;
    private Unbinder unbinder;
    private int mIdFromActivity;

    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(int id) {
        InfoFragment fragment = new InfoFragment();
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

        View view = inflater.inflate(R.layout.fragment_info, container, false);

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
                        setupFactsViews(detailResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );


        mCompositeDisposable.add(mViewModel.getCredit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DirectorCreditResponse>() {
                    @Override
                    public void onNext(DirectorCreditResponse directorCreditResponse) {
                        setupDirector(directorCreditResponse);
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

    private void setupDirector(DirectorCreditResponse directorCreditResponse) {

        if (directorCreditResponse.getCrew().size() > 0) {
            directorLine.setVisibility(View.VISIBLE);
            directorTitle.setVisibility(View.VISIBLE);
            directorRecyclerView.setVisibility(View.VISIBLE);
        }

        directorAdapter = new DirectorAdapter(removeDuplicates(directorCreditResponse.getCrew())
                , getContext());
        directorRecyclerView.setAdapter(directorAdapter);
    }


    private void setupFactsViews(DetailResponse detailResponse) {
        overView.setText(detailResponse.getOverview());

        originalTitle.setText(detailResponse.getOriginalTitle());
        if (detailResponse.getHomepage() != null) {
            homepage.setText(detailResponse.getHomepage());
        }


        Locale loc = new Locale(detailResponse.getOriginalLanguage());
        String name = loc.getDisplayLanguage(loc);
        originalLanguage.setText(name);

        int budgetInt = detailResponse.getBudget();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedInt = formatter.format(budgetInt);
        String stringBudget = String.valueOf(formattedInt);
        budget.setText("$" + stringBudget);


        int revInt = detailResponse.getRevenue();
        DecimalFormat formatter2 = new DecimalFormat("#,###,###");
        String formattedInt2 = formatter.format(revInt);
        String stringRev = String.valueOf(formattedInt2);
        revenue.setText("$" + stringRev);


        List<CrewObject> crewList = detailResponse.getCreditResponse().getCrew();
        for (int i = 0; i < crewList.size(); i++) {
            if ((crewList.get(i).getJob()).equals("Director")) {

                String directorName = crewList.get(i).getName();
                directedBy.setText(directorName);
                directorTitle.setText("More from " + directorName);
//               int directorId = crewList.get(i).getId();
//               directorCall(directorId);
                mViewModel.directorIdChanged(new DirectorId(crewList.get(i).getId()));
                break;
            }
        }


        setupTrailer(detailResponse.getVideoResponse().getResults());
        setupRecommendation(detailResponse.getRecommendResponse().getResults());


    }

    private void setupRecommendation(List<RecommendObject> recommendObjects) {
        if (recommendObjects.size() > 0) {
            recommendTitle.setVisibility(View.VISIBLE);
            recommendRecyclerView.setVisibility(View.VISIBLE);
        }
        recommendAdapter = new RecommendAdapter(recommendObjects, getContext());
        recommendRecyclerView.setAdapter(recommendAdapter);

    }

    private void setupTrailer(List<VideoObjects> videoObjects) {
        if (videoObjects.size() > 0) {
            trailerTitle.setVisibility(View.VISIBLE);
            trilarLine.setVisibility(View.VISIBLE);
            trailerRecyclerView.setVisibility(View.VISIBLE);
        }
        trailerAdapter = new TrailerAdapter(videoObjects, getContext());
        trailerRecyclerView.setAdapter(trailerAdapter);

    }

    private void unBind() {
        mCompositeDisposable.clear();
        //butteKnife
        unbinder.unbind();
    }


    @NonNull
    private DetailsActivityViewModel getDetailViewModel() {
        return ((CinemaApplication) Objects.requireNonNull(getActivity()).getApplication()).getDetailViewModel();
    }


    private List<DirectorCrewObject> removeDuplicates(List<DirectorCrewObject> list) {
        List<DirectorCrewObject> noRepeat = new ArrayList<>();
        for (DirectorCrewObject crew : list) {
            boolean isFound = false;
            // check if the event name exists in noRepeat
            for (DirectorCrewObject e : noRepeat) {
                if (e.getTitle().equals(crew.getTitle()) || (e.equals(crew))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) noRepeat.add(crew);
        }

        return noRepeat;
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
