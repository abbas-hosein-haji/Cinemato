package com.abhj.cinemato.model;

import com.google.gson.annotations.SerializedName;

public class CertificateObject {

    @SerializedName("certification")
    private String certification;

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }
}
