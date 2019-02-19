package com.example.cinemato.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReleaseObject {


    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    @SerializedName("release_dates")
    private List<CertificateObject> release_dates;

    public List<CertificateObject> getRelease_dates() {
        return release_dates;
    }

    public void setRelease_dates(List<CertificateObject> release_dates) {
        this.release_dates = release_dates;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }
}
