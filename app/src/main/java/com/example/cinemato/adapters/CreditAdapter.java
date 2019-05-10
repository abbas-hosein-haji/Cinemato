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
import com.example.cinemato.model.Credit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.creditViewHolder> {

    private final ArrayList<Credit> mCredits;
    private final Context mContext;

    public CreditAdapter(Context mContext, ArrayList<Credit> mCredits) {
        this.mCredits = mCredits;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CreditAdapter.creditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_credits, parent, false);
        return new CreditAdapter.creditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.creditViewHolder holder, int position) {

        holder.asTextView.setText(mContext.getString(R.string.as_character, mCredits
                .get(position).getCharacter()));

        holder.titleTextView.setText(mCredits.get(position).getTitle());

        String r = mCredits.get(position).getReleaseDate();
        if (r != null) {
            holder.yearTextView.setText(r.split("-")[0]);
        }

        holder.voteTextView.setText(String.valueOf(mCredits.get(position).getVoteAverage()));

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185/" + mCredits.get(position).getPosterPath())
                .placeholder(R.drawable.place_holder_w300)
                .error(AppCompatResources.getDrawable(mContext, R.drawable.place_holder_w300_error))
                .into(holder.image);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("posterPath", mCredits.get(holder.getAdapterPosition()).getPosterPath());
            intent.putExtra("id", mCredits.get(holder.getAdapterPosition()).getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
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
        return mCredits.size();
    }

    class creditViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.itemCreditImageView)
        ImageView image;

        @BindView(R.id.itemYearTextView)
        TextView yearTextView;

        @BindView(R.id.itemAsTextView)
        TextView asTextView;

        @BindView(R.id.itemTitleTextView)
        TextView titleTextView;

        @BindView(R.id.itemVoteTextView)
        TextView voteTextView;


        creditViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
