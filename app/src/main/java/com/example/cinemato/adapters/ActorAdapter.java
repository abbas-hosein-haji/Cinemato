package com.example.cinemato.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cinemato.R;
import com.example.cinemato.activities.PersonActivity;
import com.example.cinemato.fragments.PosterDialogFragment;
import com.example.cinemato.model.ImageObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.actorViewHolder> {

    private final ArrayList<ImageObject> mImages;
    private final Context mContext;

    public ActorAdapter(Context mContext, ArrayList<ImageObject> mImages) {
        this.mImages = mImages;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ActorAdapter.actorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_person, parent, false);
        return new ActorAdapter.actorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorAdapter.actorViewHolder holder, int position) {

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185/" + mImages.get(position).getFilePath())
                .placeholder(R.drawable.place_holder_w300)
                .error(R.drawable.place_holder_w300_error)
                .into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", mImages);
                bundle.putInt("position", holder.getAdapterPosition());

                FragmentTransaction ft = ((PersonActivity) mContext).getSupportFragmentManager().beginTransaction();

                PosterDialogFragment newFragment = PosterDialogFragment.newInstance();
                newFragment.setArguments(bundle);

                newFragment.show(ft, "slideshow");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class actorViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.actor_imageView)
        ImageView image;

        actorViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }


}
