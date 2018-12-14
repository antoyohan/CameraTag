package com.example.ando.cameratag.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoUtils {

    public static String getImageName(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = context.getExternalFilesDir("PICTURES/").toString() + "Picture_" + date + ".png";
        return photoFile;
    }

    public static Uri getImageUri(Context context) {
        return FileProvider.getUriForFile(
                context,
                "com.example.ando.cameratag",
                new File(PhotoUtils.getImageName(context)));
    }

    public static String getFilePath(String path, Context context) {
        String filePath = null;
        Uri uri = Uri.parse(path);
        if (uri != null && "content".equals(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }
        return filePath;
    }
}
