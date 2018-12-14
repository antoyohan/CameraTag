package com.example.ando.cameratag.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.ando.cameratag.model.Marker;

import java.util.ArrayList;

@Entity(tableName = "PRESCRIPTION_TABLE")
public class PrescriprionEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "image_loc")
    private String mImageLoc;
    @ColumnInfo(name = "coords")
    private ArrayList<Marker> mMarkerCoordinates;

    public PrescriprionEntity(String mImageLoc, ArrayList<Marker> mMarkerCoordinates) {
        this.mImageLoc = mImageLoc;
        this.mMarkerCoordinates = mMarkerCoordinates;
    }

    public String getImageLoc() {
        return mImageLoc;
    }

    public void setImageLoc(String mImageLoc) {
        this.mImageLoc = mImageLoc;
    }

    public ArrayList<Marker> getMarkerCoordinates() {
        return mMarkerCoordinates;
    }

    public void setMarkerCoordinates(ArrayList<Marker> mMarkerCoordinates) {
        this.mMarkerCoordinates = mMarkerCoordinates;
    }
}
