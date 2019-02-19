package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class CrewObject {

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;


    @SerializedName("id")
    private int id;


    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }
}
