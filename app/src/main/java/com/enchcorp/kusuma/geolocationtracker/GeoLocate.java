package com.enchcorp.kusuma.geolocationtracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by enchanterkusma on 12/7/17.
 */

public class GeoLocate implements Parcelable{
   String id;
    String gname;
    String glati;

    protected GeoLocate(Parcel in) {
        id = in.readString();
        gname = in.readString();
        glati = in.readString();
        glongi = in.readString();
        gfilter = in.readString();
    }

    public static final Creator<GeoLocate> CREATOR = new Creator<GeoLocate>() {
        @Override
        public GeoLocate createFromParcel(Parcel in) {
            return new GeoLocate(in);
        }

        @Override
        public GeoLocate[] newArray(int size) {
            return new GeoLocate[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGlati() {
        return glati;
    }

    public void setGlati(String glati) {
        this.glati = glati;
    }

    public String getGlongi() {
        return glongi;
    }

    public void setGlongi(String glongi) {
        this.glongi = glongi;
    }

    public String getGfilter() {
        return gfilter;
    }

    public void setGfilter(String gfilter) {
        this.gfilter = gfilter;
    }

    String glongi;
    String gfilter;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(gname);
        parcel.writeString(glati);
        parcel.writeString(glongi);
        parcel.writeString(gfilter);
    }
}
