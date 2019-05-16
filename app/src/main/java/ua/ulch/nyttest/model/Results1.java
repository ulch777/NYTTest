package ua.ulch.nyttest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Results1 implements Parcelable {

    private Media[] media;

    private String title;

    private String url;

    private String published_date;


    protected Results1(Parcel in) {
        title = in.readString();
        url = in.readString();
        published_date = in.readString();
    }

    public static final Creator<Results1> CREATOR = new Creator<Results1>() {
        @Override
        public Results1 createFromParcel(Parcel in) {
            return new Results1(in);
        }

        @Override
        public Results1[] newArray(int size) {
            return new Results1[size];
        }
    };

    public Media[] getMedia() {
        return media;
    }

    public void setMedia(Media[] media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(published_date);
    }
}
