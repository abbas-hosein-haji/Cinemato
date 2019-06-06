package com.abhj.cinemato.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageObject implements Parcelable {


    public static final Creator<ImageObject> CREATOR = new Creator<ImageObject>() {
        @Override
        public ImageObject createFromParcel(Parcel in) {
            return new ImageObject(in);
        }

        @Override
        public ImageObject[] newArray(int size) {
            return new ImageObject[size];
        }
    };
    @SerializedName("file_path")
    private String filePath;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;

    protected ImageObject(Parcel in) {
        filePath = in.readString();
        height = in.readInt();
        width = in.readInt();
    }

    public String getFilePath() {
        return filePath;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filePath);
        parcel.writeInt(height);
        parcel.writeInt(width);
    }
}
