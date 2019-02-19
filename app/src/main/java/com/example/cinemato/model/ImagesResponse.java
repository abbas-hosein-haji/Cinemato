package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {

    @SerializedName("backdrops")
    private List<ImageObject> backdrops;

    @SerializedName("posters")
    private List<ImageObject> posters;

    public List<ImageObject> getBackdrops() {
        return backdrops;
    }

    public List<ImageObject> getPosters() {
        return posters;
    }
}
