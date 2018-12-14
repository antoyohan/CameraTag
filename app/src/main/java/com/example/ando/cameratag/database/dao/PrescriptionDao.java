package com.example.ando.cameratag.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ando.cameratag.database.entity.PrescriprionEntity;

import java.util.List;

@Dao
public interface PrescriptionDao {
    @Insert
    void insert(PrescriprionEntity info);

    @Delete
    void delete(PrescriprionEntity info);

    @Update
    void update(PrescriprionEntity info);

    @Query("SELECT * FROM " + "PRESCRIPTION_TABLE")
    List<PrescriprionEntity> getData();

    @Query("SELECT COUNT(*) FROM PRESCRIPTION_TABLE where image_loc == :loc LIMIT 1")
    Long getCount(String loc);
}
