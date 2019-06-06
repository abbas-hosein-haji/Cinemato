package com.abhj.cinemato.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhj.cinemato.R;
import com.abhj.cinemato.model.ReviewObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewViewHolder> {

    private final List<ReviewObject> mReviews;
    private final Context mContext;

    public ReviewAdapter(List<ReviewObject> reviews, Context context) {
        mReviews = reviews;
        mContext = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);
        return new ReviewAdapter.reviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.reviewViewHolder holder, int position) {
        holder.author.setText(mReviews.get(position).getAuthor());
        holder.content.setText(mReviews.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }


    class reviewViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        @BindView(R.id.author_textView)
        TextView author;

        @BindView(R.id.content_TextView)
        TextView content;


        reviewViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }


}