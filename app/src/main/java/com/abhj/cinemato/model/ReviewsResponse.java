package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("results")
    private List<ReviewObject> results;


    public List<ReviewObject> getResults() {
        return results;
    }
}
