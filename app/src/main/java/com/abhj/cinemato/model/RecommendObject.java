package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class RecommendObject {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;


    @SerializedName("id")
    private Integer id;

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }
}


