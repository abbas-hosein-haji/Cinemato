package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {
    @SerializedName("crew")
    private List<CrewObject> crew;


    public List<CrewObject> getCrew() {
        return crew;
    }
}
