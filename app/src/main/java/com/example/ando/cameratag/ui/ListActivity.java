package com.example.ando.cameratag.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ando.cameratag.R;
import com.example.ando.cameratag.adapter.ListViewAdapter;
import com.example.ando.cameratag.database.AppDataBase;
import com.example.ando.cameratag.database.entity.PrescriprionEntity;
import com.example.ando.cameratag.ui.custom.CustomImageView;
import com.example.ando.cameratag.utils.PhotoUtils;

import java.io.File;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private ListViewAdapter.OnClickListener mClickListener = new ListViewAdapter.OnClickListener() {
        @Override
        public void onClick(PrescriprionEntity data) {
            mCustomImageView = findViewById(R.id.image_preview);
            mCustomImageView.setVisibility(View.VISIBLE);
            File imgFile = new File(data.getImageLoc());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

               //

                mCustomImageView.setImageBitmap(myBitmap);

            }
        }
    };
    private CustomImageView mCustomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initViews();
        inflateData();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.content_list_view);
    }

    private void inflateData() {
        List<PrescriprionEntity> mList = AppDataBase.getAppDatabase(this).prescriptionDao().getData();
        ListViewAdapter adapter = new ListViewAdapter(mList, mClickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}
