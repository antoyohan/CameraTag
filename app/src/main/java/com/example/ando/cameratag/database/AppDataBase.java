package com.example.ando.cameratag.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.ando.cameratag.database.dao.PrescriptionDao;
import com.example.ando.cameratag.database.entity.PrescriprionEntity;
import com.example.ando.cameratag.utils.Constants;

@Database(entities = {PrescriprionEntity.class}, version = Constants.DB_VERSION, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase sInstance;

    public abstract PrescriptionDao prescriptionDao();

    public static AppDataBase getAppDatabase(Context context) {
        if (sInstance == null) {
            sInstance =
                    Room.databaseBuilder(context, AppDataBase.class, Constants.DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}