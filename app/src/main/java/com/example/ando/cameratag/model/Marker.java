package com.example.ando.cameratag.model;

import android.arch.persistence.room.ColumnInfo;

public class Marker {

    @ColumnInfo
    private float xcoord;
    @ColumnInfo
    private float ycoord;

    public Marker(float xcoord, float ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public float getXcoord() {
        return xcoord;
    }

    public float getYcoord() {
        return ycoord;
    }
}
