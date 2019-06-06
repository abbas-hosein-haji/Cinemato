package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectorCreditResponse {

    @SerializedName("crew")
    private List<DirectorCrewObject> crew;


    public List<DirectorCrewObject> getCrew() {
        return crew;
    }


}

