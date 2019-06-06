package com.abhj.cinemato.model;

public class SearchActorQuery {

    private int mSelector;
    private int mPage;
    private String mQuery;

    public SearchActorQuery(int mSelector, int mPage, String mQuery) {
        this.mSelector = mSelector;
        this.mPage = mPage;
        this.mQuery = mQuery;
    }

    public int getmSelector() {
        return mSelector;
    }

    public void setmSelector(int mSelector) {
        this.mSelector = mSelector;
    }

    public int getmPage() {
        return mPage;
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    public String getmQuery() {
        return mQuery;
    }

    public void setmQuery(String mQuery) {
        this.mQuery = mQuery;
    }
}
