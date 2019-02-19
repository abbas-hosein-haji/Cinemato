package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonImages {

    @SerializedName("profiles")
    private List<ImageObject> profiles;


    public List<ImageObject> getProfiles() {
        return profiles;
    }
}
