package com.example.cinemato.utility;

import android.util.SparseArray;

import java.util.List;

public class GenreIdToString {

    private StringBuilder stringBuilder = new StringBuilder();

    public GenreIdToString(List<Integer> genreIds) {
        SparseArray<String> mapGenreIds = new SparseArray<>();
        mapGenreIds.put(28, "Action");
        mapGenreIds.put(12, "Adventure");
        mapGenreIds.put(16, "Animation");
        mapGenreIds.put(35, "Comedy");
        mapGenreIds.put(80, "Crime");
        mapGenreIds.put(99, "Documentary");
        mapGenreIds.put(18, "Drama");
        mapGenreIds.put(10751, "Family");
        mapGenreIds.put(14, "Fantasy");
        mapGenreIds.put(36, "History");
        mapGenreIds.put(27, "Horror");
        mapGenreIds.put(10402, "Music");
        mapGenreIds.put(9648, "Mystery");
        mapGenreIds.put(10749, "Romance");
        mapGenreIds.put(878, "Science Fiction");
        mapGenreIds.put(10770, "TV Movie");
        mapGenreIds.put(53, "Thriller");
        mapGenreIds.put(10752, "War");
        mapGenreIds.put(37, "Western");

        for (int i = 0; i < genreIds.size(); i++) {

            if (i == genreIds.size() - 1) {
                stringBuilder.append(mapGenreIds.get(genreIds.get(i)));
            } else {
                stringBuilder.append(mapGenreIds.get(genreIds.get(i))).append(",").append(" ");
            }
        }
    }


    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }


}

