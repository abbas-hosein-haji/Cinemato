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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cinemato.R;
import com.example.cinemato.activities.PersonActivity;
import com.example.cinemato.model.ActorObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchViewHolder> {

    private final List<ActorObject> mActors;
    private final Context mContext;

    public SearchAdapter(List<ActorObject> actors, Context context) {
        mActors = actors;
        mContext = context;
    }


    @NonNull
    @Override
    public SearchAdapter.searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search, parent, false);
        return new SearchAdapter.searchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.searchViewHolder holder, int position) {

        holder.name.setText(mActors.get(position).getName());

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185/" + mActors.get(position).getProfilePath())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(AppCompatResources.getDrawable(mContext, R.drawable.ic_person_80dp))
                .error(AppCompatResources.getDrawable(mContext, R.drawable.ic_person_80dp))
                .into(holder.image);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, PersonActivity.class);
            intent.putExtra("personPoster", mActors.get(holder.getAdapterPosition()).getProfilePath());
            intent.putExtra("personId", mActors.get(holder.getAdapterPosition()).getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                        holder.image,
                        "myTransition2");
                mContext.startActivity(intent, options.toBundle());
            } else {
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mActors.size();
    }

    public void appendActors(List<ActorObject> actorsToAppend) {
        mActors.addAll(actorsToAppend);
        notifyDataSetChanged();

    }

    class searchViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.search_name)
        TextView name;

        @BindView(R.id.search_pic)
        de.hdodenhof.circleimageview.CircleImageView image;

        searchViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }


}
