package com.abhj.cinemato.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.abhj.cinemato.R;
import com.abhj.cinemato.adapters.CreditAdapter;
import com.abhj.cinemato.model.Credit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonMovieFragment extends Fragment {

    @BindView(R.id.creditMovieRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.nested_container_person_movie)
    NestedScrollView nestedScrollView;

    Bundle bundle;
    private Unbinder unbinder;


    public PersonMovieFragment() {
        // Required empty public constructor
    }

    public static PersonMovieFragment newInstance(Bundle args) {
        PersonMovieFragment fragment = new PersonMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_movie, container, false);

        unbinder = ButterKnife.bind(this, view);
        setupViews();
        enableTouchTheftFromParent(nestedScrollView);


        return view;
    }

    private void setupViews() {
        ArrayList<Credit> images = Objects.requireNonNull(getArguments()).getParcelableArrayList("credits");
        sortByReleaseDate(images);
        CreditAdapter adapter = new CreditAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);
    }

    private void sortByReleaseDate(ArrayList<Credit> credits) {
        Collections.sort(credits, new Comparator<Credit>() {
            @Override
            public int compare(Credit o1, Credit o2) {
                if (o1.getReleaseDate() == null) {
                    return (o2.getReleaseDate() == null) ? 0 : 1;
                }
                if (o2.getReleaseDate() == null) {
                    return -1;
                }
                return o2.getReleaseDate().compareTo(o1.getReleaseDate());
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
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
