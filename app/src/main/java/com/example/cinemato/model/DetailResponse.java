package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genres")
    private List<GenreObject> genres;

    @SerializedName("id")
    private Integer id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("title")
    private String title;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("budget")
    private int budget;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("revenue")
    private int revenue;


    @SerializedName("release_dates")
    private ReleaseDatesResponse releaseDatesResponse;

    @SerializedName("credits")
    private CreditResponse creditResponse;

    @SerializedName("videos")
    private VideoResponse videoResponse;

    @SerializedName("recommendations")
    private RecommendResponse recommendResponse;

    @SerializedName("reviews")
    private ReviewsResponse reviewsResponse;

    @SerializedName("images")
    private ImagesResponse imagesResponse;

    @SerializedName("casts")
    private CastsResponse castsResponse;

    public CastsResponse getCastsResponse() {
        return castsResponse;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<GenreObject> getGenres() {
        return genres;
    }

    public Integer getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Boolean getVideo() {
        return video;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getRevenue() {
        return revenue;
    }

    public ReleaseDatesResponse getReleaseDatesResponse() {
        return releaseDatesResponse;
    }

    public CreditResponse getCreditResponse() {
        return creditResponse;
    }

    public VideoResponse getVideoResponse() {
        return videoResponse;
    }

    public RecommendResponse getRecommendResponse() {
        return recommendResponse;
    }

    public ReviewsResponse getReviewsResponse() {
        return reviewsResponse;
    }

    public ImagesResponse getImagesResponse() {
        return imagesResponse;
    }
}
