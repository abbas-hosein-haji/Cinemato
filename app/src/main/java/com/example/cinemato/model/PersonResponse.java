package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    @SerializedName("biography")
    private String biography;

    @SerializedName("place_of_birth")
    private String place_of_birth;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("also_known_as")
    private List<String> also_known_as;

    @SerializedName("tagged_images")
    private TaggedImageResponse taggedImageResponse;

    @SerializedName("images")
    private PersonImages personImages;

    @SerializedName("movie_credits")
    private MovieCredits movieCredits;


    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getBiography() {
        return biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public TaggedImageResponse getTaggedImageResponse() {
        return taggedImageResponse;
    }

    public PersonImages getPersonImages() {
        return personImages;
    }

    public MovieCredits getMovieCredits() {
        return movieCredits;
    }
}
