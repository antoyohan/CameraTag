package com.example.ando.cameratag.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ando.cameratag.R;
import com.example.ando.cameratag.database.AppDataBase;
import com.example.ando.cameratag.database.dao.PrescriptionDao;
import com.example.ando.cameratag.database.entity.PrescriprionEntity;
import com.example.ando.cameratag.model.Marker;
import com.example.ando.cameratag.ui.custom.CustomImageView;
import com.example.ando.cameratag.utils.PhotoUtils;

import java.util.ArrayList;

import static com.example.ando.cameratag.utils.Constants.CAMERA_PERMISSION_CODE;
import static com.example.ando.cameratag.utils.Constants.CAMERA_REQUEST;
import static com.example.ando.cameratag.utils.Constants.WRITE_PERMISSION_CODE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Uri mImageUri;
    private Button mCamButton;
    private CustomImageView mImagePreview;
    private float[] lastTouchDownXY = new float[2];
    private ArrayList<Marker> mMarkerList = new ArrayList<>();
    private Marker mLastTouchedMarker;
    private Button mSaveButton;
    private Button mListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCamButton = (Button) findViewById(R.id.btn_cam);
        mSaveButton = (Button) findViewById(R.id.save_btn);
        mListButton = (Button)findViewById(R.id.show_list);
        mListButton.setOnClickListener(this);
        askForPermissions();
        mCamButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cam:
                if (isCameraAllowed() && isExternalStorageAllowed()) {
                    startCameraActivity();
                } else {
                    askForPermissions();
                }
                break;

            case R.id.preview_image:
                showMarker();
                break;

            case R.id.save_btn:
                saveContentToDb();
                break;

            case R.id.show_list:
                startListActivity();
                break;
        }
    }

    private void saveContentToDb() {
        Log.d(TAG, " uri " + mImageUri);
        PrescriprionEntity entity = new PrescriprionEntity(mImageUri.toString(), mMarkerList);
        PrescriptionDao dao = AppDataBase.getAppDatabase(this).prescriptionDao();
        if (dao.getCount(mImageUri.toString()) > 0) {
            dao.update(entity);
        } else {
            dao.insert(entity);
        }
        startListActivity();
    }

    private void startListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void showMarker() {
        mImagePreview.addNewMarker(mLastTouchedMarker);
    }

    private void startCameraActivity() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        mImageUri = PhotoUtils.getImageUri(this);
        //mImageUri = Uri.parse(Environment.getExternalStorageDirectory() + PhotoUtils.getImageName());
        Log.d(TAG, "URI " + mImageUri);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                showPhotoPreview(image);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showPhotoPreview(Bitmap image) {
        mCamButton.setVisibility(View.GONE);
        mListButton.setVisibility(View.GONE);
        mImagePreview = ((CustomImageView) findViewById(R.id.preview_image));
        mImagePreview.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.VISIBLE);
        mImagePreview.setImageBitmap(image);
        mImagePreview.setOnClickListener(this);
        mImagePreview.setOnTouchListener(mPerviewTouchlistener);
    }

    /*** Permission for download
     */
    private void askForPermissions() {
        if (Build.VERSION.SDK_INT > 22)
            if (!isCameraAllowed()) {
                requestCameraPermission();
            } else if (!isExternalStorageAllowed()) {
                requestStoragePermission();
            }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isCameraAllowed() {
        int result = checkSelfPermission(Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        this.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionDenied = grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED;
        if (requestCode == CAMERA_PERMISSION_CODE && permissionDenied) {
            Toast.makeText(this, getString(R.string.provide_permission), Toast.LENGTH_SHORT).show();
        } else if (requestCode == WRITE_PERMISSION_CODE && permissionDenied) {
            Toast.makeText(this, getString(R.string.provide_permission), Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!isExternalStorageAllowed()) {
                    requestStoragePermission();
                } else if (isExternalStorageAllowed() && isCameraAllowed()) {
                    //startCameraActivity();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isExternalStorageAllowed() {
        int result = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    View.OnTouchListener mPerviewTouchlistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mLastTouchedMarker = new Marker(event.getX(), event.getY());
                mMarkerList.add(mLastTouchedMarker);
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (mImagePreview.getMarkerCount() > 0) {
            mImagePreview.removeOneMarker();
        } else if (mImagePreview.getVisibility() == View.VISIBLE) {
            mImagePreview.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.GONE);
            mCamButton.setVisibility(View.VISIBLE);
            mListButton.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
