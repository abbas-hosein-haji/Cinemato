package com.abhj.cinemato.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.abhj.cinemato.R;
import com.abhj.cinemato.model.VideoObjects;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final List<VideoObjects> mVideoObjects;
    private final Context mContext;

    public TrailerAdapter(List<VideoObjects> videoObjects, Context context) {
        mVideoObjects = videoObjects;
        mContext = context;
    }


    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {

        holder.trailerName.setText(mVideoObjects.get(position).getName());

        Glide.with(mContext)
                .load("https://img.youtube.com/vi/" + mVideoObjects.get(position).getKey() + "/hqdefault.jpg")
                .placeholder(R.drawable.place_holder_youtube)
                .into(holder.trailerImage);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/watch?v="
                    + mVideoObjects.get(position).getKey()));
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mVideoObjects.size();
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.trailer_text)
        TextView trailerName;
        @BindView(R.id.trailer_image)
        ImageView trailerImage;

        TrailerViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
