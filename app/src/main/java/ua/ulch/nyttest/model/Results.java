package ua.ulch.nyttest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Results implements Parcelable {

    private String email_count;

    private String eta_id;

    private String subsection;

    private String count_type;

    private String column;

    private String nytdsection;

    private String section;

    private String asset_id;
    @SerializedName("abstract")
    private String _abstract;

    private String source;

    private Media[] media;

    private String type;

    private String title;

    private String uri;

    private String url;

    private String adx_keywords;

    private String id;

    private String byline;

    private String published_date;

    private String updated;


    protected Results(Parcel in) {
        email_count = in.readString();
        eta_id = in.readString();
        subsection = in.readString();
        count_type = in.readString();
        column = in.readString();
        nytdsection = in.readString();
        section = in.readString();
        asset_id = in.readString();
        _abstract = in.readString();
        source = in.readString();
        type = in.readString();
        title = in.readString();
        uri = in.readString();
        url = in.readString();
        adx_keywords = in.readString();
        id = in.readString();
        byline = in.readString();
        published_date = in.readString();
        updated = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getEmail_count ()
    {
        return email_count;
    }

    public void setEmail_count (String email_count)
    {
        this.email_count = email_count;
    }

    public String getEta_id ()
    {
        return eta_id;
    }

    public void setEta_id (String eta_id)
    {
        this.eta_id = eta_id;
    }

    public String getSubsection ()
    {
        return subsection;
    }

    public void setSubsection (String subsection)
    {
        this.subsection = subsection;
    }

    public String getCount_type ()
    {
        return count_type;
    }

    public void setCount_type (String count_type)
    {
        this.count_type = count_type;
    }


    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getColumn ()
    {
        return column;
    }

    public void setColumn (String column)
    {
        this.column = column;
    }

    public String getNytdsection ()
    {
        return nytdsection;
    }

    public void setNytdsection (String nytdsection)
    {
        this.nytdsection = nytdsection;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    public String getAsset_id ()
    {
        return asset_id;
    }

    public void setAsset_id (String asset_id)
    {
        this.asset_id = asset_id;
    }


    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public Media[] getMedia ()
    {
        return media;
    }

    public void setMedia (Media[] media)
    {
        this.media = media;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }


    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getAdx_keywords ()
    {
        return adx_keywords;
    }

    public void setAdx_keywords (String adx_keywords)
    {
        this.adx_keywords = adx_keywords;
    }


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getByline ()
    {
        return byline;
    }

    public void setByline (String byline)
    {
        this.byline = byline;
    }

    public String getPublished_date ()
    {
        return published_date;
    }

    public void setPublished_date (String published_date)
    {
        this.published_date = published_date;
    }

    public String getUpdated ()
    {
        return updated;
    }

    public void setUpdated (String updated)
    {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Results{" +
                ", email_count='" + email_count + '\'' +
                ", eta_id='" + eta_id + '\'' +
                ", subsection='" + subsection + '\'' +
                ", count_type='" + count_type + '\'' +
                ", column='" + column + '\'' +
                ", nytdsection='" + nytdsection + '\'' +
                ", section='" + section + '\'' +
                ", asset_id='" + asset_id + '\'' +
                ", _abstract='" + _abstract + '\'' +
                ", source='" + source + '\'' +
                ", media=" + Arrays.toString(media) +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", uri='" + uri + '\'' +
                ", url='" + url + '\'' +
                ", adx_keywords='" + adx_keywords + '\'' +
                ", id='" + id + '\'' +
                ", byline='" + byline + '\'' +
                ", published_date='" + published_date + '\'' +
                ", updated='" + updated + '\'' +
                '}';
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
