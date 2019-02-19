package com.example.cinemato.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.cinemato.R;
import com.example.cinemato.fragments.SearchActorFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleActivity extends AppCompatActivity {

    @BindView(R.id.people_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.people_nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.people_toolbar)
    Toolbar mToolbar;

    String[] addresses = {"intorie@gmail.com"};
    String subject = "Feedback : Cinemato 1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        ButterKnife.bind(this);
        setupFragmentView();
        setStatusBarGradient(this);
        setupActionBar();
        setupNavigationView();


    }

    private void setupFragmentView() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.people_frame, SearchActorFragment.newInstance(1)).commit();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorBaseGrey));
        }
    }


    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Popular People");
    }


    private void setupNavigationView() {

        mNavigationView.setNavigationItemSelectedListener(item -> {

            item.setChecked(true);

            mDrawerLayout.closeDrawers();

            switch (item.getItemId()) {

                case R.id.nav_popular_people:
                    Intent intent = new Intent(PeopleActivity.this, PeopleActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_contact:
                    composeEmail(addresses, subject);
                    break;

                case R.id.nav_movies:
                    Intent intent3 = new Intent(PeopleActivity.this, MainActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.nav_settings:
                    Intent intent2 = new Intent(PeopleActivity.this, SettingsActivity.class);
                    startActivity(intent2);
                    break;

                default:
                    Intent intent4 = new Intent(PeopleActivity.this, MainActivity.class);
                    startActivity(intent4);
            }


            return true;
        });
    }


    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_buttons, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
