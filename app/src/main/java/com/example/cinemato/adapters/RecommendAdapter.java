package com.example.cinemato.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemato.R;
import com.example.cinemato.activities.DetailsActivity;
import com.example.cinemato.model.RecommendObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private final List<RecommendObject> mList;
    private final Context mContext;

    public RecommendAdapter(List<RecommendObject> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recommend, parent, false);
        return new RecommendAdapter.RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {

        holder.name.setText(mList.get(position).getTitle());

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185" + mList.get(position).getPosterPath())
                .placeholder(R.drawable.place_holder_w300)
                .error(R.drawable.place_holder_w300_error)
                .into(holder.image);

        holder.mView.setOnClickListener(view -> {

            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("posterPath", mList.get(position).getPosterPath());
            intent.putExtra("id", mList.get(position).getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) mContext,
                                holder.image,
                                "myTransition");
                mContext.startActivity(intent, options.toBundle());
            } else {
                mContext.startActivity(intent);
            }

        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class RecommendViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.recommend_text)
        TextView name;
        @BindView(R.id.recommend_image)
        ImageView image;


        RecommendViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
