package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("results")
    private List<VideoObjects> results;


    public List<VideoObjects> getResults() {
        return results;
    }
}
