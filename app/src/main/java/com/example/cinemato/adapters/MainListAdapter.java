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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cinemato.R;
import com.example.cinemato.activities.DetailsActivity;
import com.example.cinemato.model.Movie;
import com.example.cinemato.utility.GenreIdToString;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.cinemaViewHolder> {

    private final List<Movie> mMovie;
    private final Context mContext;
    private GenreIdToString genreIdToString = new GenreIdToString();

    public MainListAdapter(List<Movie> movie, Context context) {
        mMovie = movie;
        mContext = context;
    }

    @NonNull
    @Override
    public cinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_main, parent, false);
        return new cinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cinemaViewHolder holder, int position) {

        Movie movie = mMovie.get(holder.getAdapterPosition());

        holder.setTitle(movie.getTitle());
        holder.setGenre(movie.getGenreIds());
        holder.setDate(movie.getReleaseDate());
        holder.setRating(movie.getVoteAverage());
        holder.setImage(movie.getPosterPath());
        holder.setOnClick(movie.getPosterPath(),movie.getId());

    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    public void appendMovies(List<Movie> moviesToAppend) {
        mMovie.addAll(moviesToAppend);
        notifyDataSetChanged();

    }

    class cinemaViewHolder extends RecyclerView.ViewHolder {

        final View mView;


        @BindView(R.id.main_title)
        TextView movieTitle;
        @BindView(R.id.main_jenre)
        TextView genre;
        @BindView(R.id.main_date)
        TextView date;
        @BindView(R.id.main_rating)
        TextView rating;
        @BindView(R.id.main_image)
        ImageView poster;

        cinemaViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }


         void setTitle(String string) {
            this.movieTitle.setText(string);
        }

         void setGenre(List<Integer> genreIds) {
            this.genre.setText(genreIdToString.findGenres(genreIds));
        }

         void setDate(String string) {
            this.date.setText(string.split("-")[0]);
        }

         void setRating(Double d) {
            this.rating.setText(String.valueOf(d));
        }


        void setImage(String posterPath) {
            Glide.with(mContext)
                    .load("https://image.tmdb.org/t/p/w185/" + posterPath)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.place_holder_w300)
                    .error(R.drawable.place_holder_w300_error)
                    .into(this.poster);
        }


        void setOnClick(String posterPath, Integer id) {
            this.mView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("posterPath", posterPath);
                intent.putExtra("id", id);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                            this.poster,
                            "myTransition");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }

            });

        }
    }


}