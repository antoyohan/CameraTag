package com.example.ando.cameratag.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.example.ando.cameratag.R;
import com.example.ando.cameratag.model.Marker;

import java.util.ArrayList;

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private ArrayList<Marker> mMarkerList = new ArrayList<>();
    Bitmap mPointer;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPointer = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_marker);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mMarkerList.size() > 0) {
            for (Marker marker : mMarkerList) {
                canvas.drawBitmap(mPointer, marker.getXcoord(), marker.getYcoord(), null);
            }
        }
    }

    public void addNewMarker(Marker marker) {
        this.mMarkerList.add(marker);
        invalidate();
    }

    public int getMarkerCount() {
        return mMarkerList.size();
    }

    public void removeOneMarker() {
        if (mMarkerList.size() > 0) {
            mMarkerList.remove(mMarkerList.size() - 1);
            invalidate();
        }
    }
}
