package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class Casts {

    @SerializedName("id")
    private int id;

    @SerializedName("character")
    private String character;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;


    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
