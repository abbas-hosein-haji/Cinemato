package com.example.cinemato.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.cinemato.R;
import com.example.cinemato.adapters.PosterDialogAdapter;
import com.example.cinemato.model.ImageObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PosterDialogFragment extends DialogFragment {

    @BindView(R.id.slide_view_pager)
    ViewPager viewPager;

    @BindView(R.id.dialog_toolbar)
    Toolbar toolbar;


    private String TAG = PosterDialogFragment.class.getSimpleName();
    private int selectedPosition = 0;
    private ArrayList<ImageObject> images;


    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            toolbar.setTitle((position + 1) + " of " + images.size());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    private Unbinder unbinder;

    public static PosterDialogFragment newInstance() {
        return new PosterDialogFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_NoActionBar_TranslucentDecor);
        } else setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poster_dialog, container, false);

        unbinder = ButterKnife.bind(this, v);
        setupAnimation();
        setupToolbar(toolbar);
        getImages();
        setupViewPager(viewPager);
        setCurrentItem(selectedPosition);

        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //butteKnife
        unbinder.unbind();
    }


    private void setupViewPager(ViewPager viewPager) {
        PosterDialogAdapter adapter = new PosterDialogAdapter(getContext(), images);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }


    private void setupToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        toolbar.inflateMenu(R.menu.dialog_menu_buttons);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        shareItem();
                        return true;
                    case R.id.download:
                        if (readAndWriteExternalStorage(getContext())) {
                            downloadFile();
                        }
                        return true;
                    default:
                        return true;
                }
            }
        });

    }


    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        toolbar.setTitle((position + 1) + " of " + images.size());
    }


    private void setupAnimation() {
        Objects.requireNonNull(getDialog().getWindow()).getAttributes()
                .windowAnimations = R.style.DialogAnimation;
    }


    private void getImages() {
        images = Objects.requireNonNull(getArguments()).getParcelableArrayList("images");
        selectedPosition = getArguments().getInt("position");
        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());
    }


    //download image for saving
    public void downloadFile() {
        String path = images.get(viewPager.getCurrentItem()).getFilePath();
        String uRl = "https://image.tmdb.org/t/p/w500" + path;
        String name = path.substring(1, 5);
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cinemato");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        DownloadManager mgr = (DownloadManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(name)
                .setDescription("Saved images from Cinemato")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES + "/Cinemato", name + ".jpg");
        assert mgr != null;
        mgr.enqueue(request);
        // Open Download Manager to view File progress
        Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_LONG).show();
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile();
                }
        }
    }


    public boolean readAndWriteExternalStorage(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return false;
        } else {
            return true;
        }

    }


    public void shareItem() {

        String url = "https://image.tmdb.org/t/p/w500" + images.get(viewPager.getCurrentItem()).getFilePath();
        Glide.with(getContext())
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Toast.makeText(getContext(), "Sharing...", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));
                        startActivity(Intent.createChooser(i, "Share Image"));
                    }
                });
    }


    //Convert Bitmap into Uri
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(Objects.requireNonNull(getContext())
                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


}
