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

import java.util.List;

public class DetailsPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageObject> images;


    public DetailsPagerAdapter(Context context, List<ImageObject> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = Objects.requireNonNull(inflater).inflate(R.layout.image_fullscreen_poster, container, false);
//        ImageView imageViewPreview = view.findViewById(R.id.poster_full_Image);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w780" + images.get(position).getFilePath())
                .centerCrop()
                //  .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}