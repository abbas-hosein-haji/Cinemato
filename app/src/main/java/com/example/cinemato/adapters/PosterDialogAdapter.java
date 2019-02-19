package com.example.cinemato.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cinemato.model.ImageObject;

import java.util.ArrayList;

public class PosterDialogAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<ImageObject> images;


    public PosterDialogAdapter(Context context, ArrayList<ImageObject> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = Objects.requireNonNull(inflater).inflate(R.layout.image_fullscreen_poster, container, false);
//        ImageView imageViewPreview = view.findViewById(R.id.poster_full_Image);

        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w780/" + images.get(position).getFilePath())
                // .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}