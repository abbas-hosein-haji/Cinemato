package com.abhj.cinemato.model;

public class Page {
    private final int mPosition;
    private final int mPage;
    private String mQuery;

    public Page(int mPosition, int mPage, String mQuery) {
        this.mPosition = mPosition;
        this.mPage = mPage;
        this.mQuery = mQuery;
    }


    public String getmQuery() {
        return mQuery;
    }

    public int getmPosition() {
        return mPosition;
    }

    public int getmPage() {
        return mPage;
    }
}
