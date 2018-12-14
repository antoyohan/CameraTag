package com.example.ando.cameratag.database;

import android.arch.persistence.room.TypeConverter;

import com.example.ando.cameratag.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converter {
    @TypeConverter
    public static ArrayList<Marker> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Marker>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ArrayList<Marker> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}