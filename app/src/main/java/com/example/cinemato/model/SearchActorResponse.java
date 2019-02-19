package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchActorResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<ActorObject> results;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;


    public int getPage() {
        return page;
    }

    public List<ActorObject> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
