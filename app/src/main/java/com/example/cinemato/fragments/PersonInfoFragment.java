package com.example.cinemato.fragments;


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
import android.widget.TextView;

import com.example.cinemato.R;
import com.example.cinemato.adapters.ActorAdapter;
import com.example.cinemato.model.ImageObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonInfoFragment extends Fragment {

    @BindView(R.id.biography_textView)
    TextView biography;
    @BindView(R.id.born_textView)
    TextView born;
    @BindView(R.id.died_title)
    TextView diedTitle;
    @BindView(R.id.died_textView)
    TextView died;
    @BindView(R.id.birthPlace_textView)
    TextView birthPlace;
    @BindView(R.id.homepage_textView)
    TextView homepage;
    @BindView(R.id.known_textView)
    TextView alsoKnownAs;
    @BindView(R.id.age_textView)
    TextView age;
    @BindView(R.id.actor_bottom_images)
    TextView bottomImages;
    @BindView(R.id.nested_container_person_info)
    NestedScrollView nestedScrollView;
    @BindView(R.id.actor_recyclerView)
    RecyclerView recyclerView;
    Bundle bundle;
    private Unbinder unbinder;


    public PersonInfoFragment() {
        // Required empty public constructor
    }


    public static PersonInfoFragment newInstance(Bundle args) {
        PersonInfoFragment fragment = new PersonInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);

        unbinder = ButterKnife.bind(this, view);
        setupViews();
        enableTouchTheftFromParent(nestedScrollView);
        setuBottomRecyclerView();

        return view;
    }

    private void setuBottomRecyclerView() {
        ArrayList<ImageObject> images = Objects.requireNonNull(getArguments()).getParcelableArrayList("actorImages");

        if (Objects.requireNonNull(images).size() > 0) {
            ActorAdapter adapter = new ActorAdapter(getContext(), images);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            bottomImages.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }


    private void setupViews() {


        String birth = bundle.getString("birth");
        String death = bundle.getString("death");
        String bio = bundle.getString("bio");
        String place = bundle.getString("place");
        String home = bundle.getString("home");
        List<String> knownAs = bundle.getStringArrayList("known");


        if (birth != null && !birth.isEmpty()) {
            if (death != null && !death.isEmpty()) {
                age.setText(String.valueOf(getAge(birth) - getAge(death)));
                died.setText(reformatDate(death));
                died.setVisibility(View.VISIBLE);
                diedTitle.setVisibility(View.VISIBLE);
            } else {
                age.setText(String.valueOf(getAge(birth)));
            }
            born.setText(reformatDate(birth));
        }


        if (knownAs != null && !knownAs.isEmpty()) {
            alsoKnownAs.setText("");
            for (int i = 0; i < knownAs.size(); i++) {
                alsoKnownAs.append(knownAs.get(i) + ",");
            }
        }

        if (place != null && !place.isEmpty()) {
            birthPlace.setText(place);
        }

        if (bio != null && !bio.isEmpty()) {
            biography.setText(bio);
        }

        if (home != null && !home.isEmpty()) {
            homepage.setText(home);
        }


    }


    public int getAge(String dateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        int age;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        birthDate.setTime(convertedDate);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        // If birth date is greater than todays date (after 2 days adjustment of
        // leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR)
                - today.get(Calendar.DAY_OF_YEAR) > 3)
                || (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of
            // month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                && (birthDate.get(Calendar.DAY_OF_MONTH) > today
                .get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }


    public String reformatDate(String actualDate) {

        SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month_date.format(date);
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
