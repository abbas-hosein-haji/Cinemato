package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class VideoObjects {

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;


    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
