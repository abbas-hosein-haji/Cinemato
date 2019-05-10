package com.example.cinemato.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemato.R;
import com.example.cinemato.activities.DetailsActivity;
import com.example.cinemato.model.DirectorCrewObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DirectorAdapter extends RecyclerView.Adapter<DirectorAdapter.ViewHolder> {

    private final List<DirectorCrewObject> mList;
    private final Context mContext;

    public DirectorAdapter(List<DirectorCrewObject> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_director, parent, false);
        return new DirectorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getTitle());

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185" + mList.get(position).getPosterPath())
                .placeholder(R.drawable.place_holder_w300)
                .error(AppCompatResources.getDrawable(mContext, R.drawable.place_holder_w300_error))
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.director_text)
        TextView name;
        @BindView(R.id.director_image)
        ImageView image;


        ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }


}
