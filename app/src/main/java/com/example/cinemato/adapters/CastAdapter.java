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
import com.example.cinemato.R;
import com.example.cinemato.activities.PersonActivity;
import com.example.cinemato.model.Casts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.castViewHolder> {

    private final List<Casts> mCasts;
    private final Context mContext;

    public CastAdapter(List<Casts> casts, Context context) {
        mCasts = casts;
        mContext = context;
    }

    @NonNull
    @Override
    public CastAdapter.castViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cast, parent, false);
        return new CastAdapter.castViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.castViewHolder holder, int position) {

        holder.name.setText(mCasts.get(position).getName());
        holder.character.setText(mContext.getString(R.string.as_character, mCasts
                .get(position).getCharacter()));


        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185/" + mCasts.get(position).getProfilePath())
                .dontAnimate()
                .placeholder(AppCompatResources.getDrawable(mContext, R.drawable.ic_person_80dp))
                .error(AppCompatResources.getDrawable(mContext, R.drawable.ic_person_80dp))
                .into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("personPoster", mCasts.get(holder.getAdapterPosition()).getProfilePath());
                intent.putExtra("personId", mCasts.get(holder.getAdapterPosition()).getId());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                            holder.image,
                            "myTransition2");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCasts.size();
    }


    class castViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.cast_name)
        TextView name;

        @BindView(R.id.cast_character)
        TextView character;

        @BindView(R.id.cast_pic)
        de.hdodenhof.circleimageview.CircleImageView image;

        castViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }


}