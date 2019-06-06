package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaggedImageResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<ImageObject> results;


    public int getId() {
        return id;
    }

    public List<ImageObject> getResults() {
        return results;
    }
}
