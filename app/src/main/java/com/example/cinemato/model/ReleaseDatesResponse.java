package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReleaseDatesResponse {
    @SerializedName("results")
    private List<ReleaseObject> results;

    public List<ReleaseObject> getResults() {
        return results;
    }
}
