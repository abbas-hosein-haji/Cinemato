package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class GenreObject {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
