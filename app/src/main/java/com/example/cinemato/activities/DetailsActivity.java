package com.example.cinemato.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.cinemato.adapters.DetailFragmentPagerAdapter;
import com.example.cinemato.adapters.DetailsPagerAdapter;
import com.example.cinemato.fragments.PosterDialogFragment;
import com.example.cinemato.model.DetailResponse;
import com.example.cinemato.model.GenreObject;
import com.example.cinemato.model.Id;
import com.example.cinemato.model.ImageObject;
import com.example.cinemato.model.MyString;
import com.example.cinemato.model.ReleaseObject;
import com.example.cinemato.viewModel.DetailsActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity implements ComponentCallbacks2 {

    @BindView(R.id.detail_genre)
    TextView genreText;
    @BindView(R.id.detail_title)
    TextView movieTitle;
    @BindView(R.id.pgTextView)
    TextView pgText;
    @BindView(R.id.dateTextView)
    TextView dateText;
    @BindView(R.id.runtimeTextView)
    TextView runTimeText;

    @BindView(R.id.second_image)
    ImageView posterImageView;
    @BindView(R.id.imageView)
    ImageView colorImageView;
    @BindView(R.id.dot1)
    ImageView dotAImageView;
    @BindView(R.id.dot2)
    ImageView dotBImageView;

    @BindView(R.id.empty_view_pager)
    ImageView emptyViewPager;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.Detail_viewpager)
    ViewPager viewPager;
    @BindView(R.id.detail_view_pager)
    ViewPager detailViewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.indicator)
    ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator indicator;

    @BindDrawable(R.drawable.rectangle_box)
    Drawable box;
    @BindDrawable(R.drawable.circle)
    Drawable circle;


    MyString myString;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private DetailsActivityViewModel mViewModel;
    @Nullable
    private DetailsPagerAdapter mAdapter;
    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mViewModel = getDetailViewModel();
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
        setupDetailFragmentPagerAdapter();

    }

    private void setupIntent() {
        Intent intent = getIntent();
        String posterPath = intent.getStringExtra("posterPath");
        mMovieId = intent.getIntExtra("id", 0);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + posterPath)
                .dontAnimate()
                .placeholder(R.drawable.place_holder_w300)
                .error(AppCompatResources.getDrawable(this, R.drawable.place_holder_w300_error))
                .into(posterImageView);

    }


    private void bind() {
        mViewModel.idChanged(new Id(mMovieId));
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(mViewModel.getDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupDetailViews)
        );

    }

    private void unBind() {
        mCompositeDisposable.clear();
    }


    private void setupDetailViews(@NonNull final DetailResponse detailResponse) {
        setImages(detailResponse.getImagesResponse().getBackdrops());

        movieTitle.setText(detailResponse.getTitle());
        dateText.setText(detailResponse.getReleaseDate().split("-")[0]);
        setupPgTextView(detailResponse);
        setupRuntimeTextView(detailResponse);
        setupGenreTextView(detailResponse);
        myString = new MyString(detailResponse.getTitle());
        setUpFullScreenImage(new ArrayList<>(detailResponse.getImagesResponse().getPosters()));
    }


    private void setImages(@NonNull final List<ImageObject> imageObjects) {
        if (imageObjects.size() > 0) {
            mAdapter = new DetailsPagerAdapter(this, imageObjects);
            detailViewPager.setAdapter(mAdapter);
            indicator.attachToPager(detailViewPager);

        } else {
            emptyViewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        }




    }


    private void setupPgTextView(DetailResponse detailResponse) {
        List<ReleaseObject> rObjects = detailResponse.getReleaseDatesResponse().getResults();
        for (int i = 0; i < rObjects.size(); i++) {
            if ((rObjects.get(i).getIso_3166_1()).equals("US")) {
                String pg = rObjects.get(i).getRelease_dates().get(0).getCertification();
                if (!pg.isEmpty()) {
                    pgText.setText(pg);
                }
                break;
            }
        }
    }


    private void setupRuntimeTextView(DetailResponse detailResponse) {
        int runTime = detailResponse.getRuntime();
        runTimeText.setText(String.format("%s%s", runTime / 60 + " hrs ", runTime % 60 + " mins"));
    }


    private void setupGenreTextView(DetailResponse detailResponse) {
        List<GenreObject> genres = detailResponse.getGenres();

        List<String> genreStrings = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++) {
            genreStrings.add(genres.get(i).getName());
        }

        StringBuilder ultimate = new StringBuilder();
        for (int i = 0; i < genreStrings.size(); i++) {
            if (i == genreStrings.size() - 1) {
                ultimate.append(genreStrings.get(i));
            } else
                ultimate.append(genreStrings.get(i)).append(",").append(" ");
        }
        genreText.setText(ultimate);
    }


    private void setupDetailFragmentPagerAdapter() {
        DetailFragmentPagerAdapter detailFragmentPagerAdapter =
                new DetailFragmentPagerAdapter(getSupportFragmentManager());
        detailFragmentPagerAdapter.setmId(mMovieId);
        viewPager.setAdapter(detailFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setUpFullScreenImage(ArrayList<ImageObject> posters) {
        if (posters.size() > 0) {
            posterImageView.setOnClickListener(view -> {
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
    private DetailsActivityViewModel getDetailViewModel() {
        return ((CinemaApplication) getApplication()).getDetailViewModel();
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
