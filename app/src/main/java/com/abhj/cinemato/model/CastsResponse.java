package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastsResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<Casts> cast;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Casts> getCast() {
        return cast;
    }

    public void setCast(List<Casts> cast) {
        this.cast = cast;
    }


}
