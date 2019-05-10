package com.example.cinemato.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemato.CinemaApplication;
import com.example.cinemato.R;
import com.example.cinemato.adapters.DetailsPagerAdapter;
import com.example.cinemato.adapters.PersonFragmentPagerAdapter;
import com.example.cinemato.fragments.PosterDialogFragment;
import com.example.cinemato.model.Id;
import com.example.cinemato.model.ImageObject;
import com.example.cinemato.model.MyString;
import com.example.cinemato.model.PersonResponse;
import com.example.cinemato.viewModel.PersonActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class PersonActivity extends AppCompatActivity {

    @BindView(R.id.person_title)
    TextView personTitle;

    @BindView(R.id.person_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.person_profile_image)
    ImageView profileImageView;

    @BindView(R.id.person_plain_imageView)
    ImageView colorImageView;

    @BindView(R.id.person_empty)
    ImageView emptyImage;

    @BindView(R.id.person_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.person_toolbar)
    Toolbar toolbar;

    @BindView(R.id.person_appbar)
    AppBarLayout appBarLayout;


    @BindView(R.id.person_view_pager)
    ViewPager personViewPager;

    @BindView(R.id.person_fragment_viewpager)
    ViewPager fragmentViewPager;

    @BindView(R.id.person_sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.person_indicator)
    ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator indicator;

    MyString myString;
    private int mPersonId;
    private Bundle mpersonBundle;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private PersonActivityViewModel mViewModel;
    @Nullable
    private DetailsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        mViewModel = getPersonViewModel();
        setupViews();

    }


    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }


    private void setupViews() {

        ButterKnife.bind(this);
        setStatusBarGradient(this);
        setupActionBar();
        setupCollapsingToolbarLayout();
        setupIntent();


    }


    private void bind() {
        mViewModel.idChanged(new Id(mPersonId));
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(mViewModel.getPerson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupDetailViews)
        );

    }

    private void unBind() {
        mCompositeDisposable.clear();
    }


    private void setupDetailViews(@NonNull final PersonResponse personResponse) {
        setImages(personResponse.getTaggedImageResponse().getResults());
        setUpFullScreenImage(new ArrayList<>(personResponse.getPersonImages().getProfiles()));
        personTitle.setText(personResponse.getName());
        myString = new MyString(personResponse.getName());


        mpersonBundle = new Bundle();
        mpersonBundle.putString("bio", personResponse.getBiography());
        mpersonBundle.putString("home", personResponse.getHomepage());
        mpersonBundle.putString("place", personResponse.getPlace_of_birth());
        mpersonBundle.putString("birth", personResponse.getBirthday());
        mpersonBundle.putString("death", personResponse.getDeathday());
        mpersonBundle.putStringArrayList("known", new ArrayList<>(personResponse.getAlso_known_as()));
        mpersonBundle.putParcelableArrayList("actorImages", new ArrayList<>(personResponse.getPersonImages().getProfiles()));
        mpersonBundle.putParcelableArrayList("credits", new ArrayList<>(personResponse.getMovieCredits().getCredits()));


        setupPersonFragmentPagerAdapter();
    }


    private void setupIntent() {
        Intent intent = getIntent();
        String profilePath = intent.getStringExtra("personPoster");
        mPersonId = intent.getIntExtra("personId", 0);


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + profilePath)
                .dontAnimate()
                .placeholder(AppCompatResources.getDrawable(this, R.drawable.ic_person_80dp))
                .error(AppCompatResources.getDrawable(this, R.drawable.ic_person_80dp))
                .into(profileImageView);
    }


    private void setupPersonFragmentPagerAdapter() {
        PersonFragmentPagerAdapter fragmentAdapter =
                new PersonFragmentPagerAdapter(getSupportFragmentManager());
        fragmentAdapter.setmId(mPersonId);
        fragmentAdapter.setmBundle(mpersonBundle);
        fragmentViewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(fragmentViewPager);
    }


    private void setImages(@NonNull final List<ImageObject> imageObjects) {
        if (imageObjects.size() > 0 ) {
            mAdapter = new DetailsPagerAdapter(this, imageObjects);
            personViewPager.setAdapter(mAdapter);
            indicator.attachToPager(personViewPager);

        } else {

            emptyImage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }



    }



    private void setUpFullScreenImage(ArrayList<ImageObject> posters) {
        if (posters.size() > 0) {
            profileImageView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("images", posters);
                bundle.putInt("position", 0);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                PosterDialogFragment newFragment = PosterDialogFragment.newInstance();
                newFragment.setArguments(bundle);

                newFragment.show(ft, "slideshow");
            });
        }
    }


    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }


    private void setupCollapsingToolbarLayout() {
        myString = new MyString("...");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(myString.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_aciton_buttons, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                onBackPressed();
                return true;

            case R.id.action_home_icon:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;


            default:
                onBackPressed();
                return true;

        }
    }


    @NonNull
    private PersonActivityViewModel getPersonViewModel() {
        return ((CinemaApplication) getApplication()).getPersonViewModel();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


}
