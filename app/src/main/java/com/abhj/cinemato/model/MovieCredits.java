package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCredits {
    @SerializedName("cast")
    private List<Credit> credits;

    public List<Credit> getCredits() {
        return credits;
    }
}
