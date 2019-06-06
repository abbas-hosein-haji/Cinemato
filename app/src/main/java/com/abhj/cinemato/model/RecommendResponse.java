package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendResponse {
    @SerializedName("results")
    private List<RecommendObject> results;


    public List<RecommendObject> getResults() {
        return results;
    }
}
