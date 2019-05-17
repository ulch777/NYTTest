package ua.ulch.nyttest.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Media {
    private String copyright;
    @SerializedName("media-metadata")
    private MediaMetadata[] mediaMetadata;

    private String subtype;

    private String caption;

    private String type;

    private String approved_for_syndication;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public MediaMetadata[] getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(MediaMetadata[] mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApproved_for_syndication() {
        return approved_for_syndication;
    }

    public void setApproved_for_syndication(String approved_for_syndication) {
        this.approved_for_syndication = approved_for_syndication;
    }

    @NotNull
    @Override
    public String toString() {
        return "Media{" +
                "copyright='" + copyright + '\'' +
                ", mediaMetadata=" + Arrays.toString(mediaMetadata) +
                ", subtype='" + subtype + '\'' +
                ", caption='" + caption + '\'' +
                ", type='" + type + '\'' +
                ", approved_for_syndication='" + approved_for_syndication + '\'' +
                '}';
    }
}
