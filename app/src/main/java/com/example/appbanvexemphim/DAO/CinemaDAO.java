package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;


import java.util.ArrayList;

public class CinemaDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static CinemaDAO instance;

    public static CinemaDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CinemaDAO(context);
        }
        return instance;
    }

    private final SQLiteDatabase db;

    public CinemaDAO(Context context) {
        super(context);
        db = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @SuppressLint("Recycle")
    public ArrayList<Cinema> getAll() {
        ArrayList<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM cinema";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String picture = c.getString(1);
            String name = c.getString(2);
            String address = c.getString(3);
            cinemas.add(new Cinema(id, picture, name, address));
        }
        c.close();
        return cinemas;
    }
    @SuppressLint({"Range", "Recycle"})
    public Cinema getById(int cinema_id){
        Cinema cinema = new Cinema();
        String sql = "SELECT * FROM cinema WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(cinema_id)});
        while (c.moveToNext()) {
            cinema.setId(c.getInt(c.getColumnIndex("id")));
            cinema.setPicture(c.getString(c.getColumnIndex("picture")));
            cinema.setName(c.getString(c.getColumnIndex("name")));
            cinema.setAddress(c.getString(c.getColumnIndex("address")));
        }
        return cinema;
    }
    @SuppressLint({"Range", "Recycle"})
    public String getNamebyId(int cinema_id){
        Cinema cinema = new Cinema();
        String sql = "SELECT name FROM cinema WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(cinema_id)});
        while (c.moveToNext()) {
            cinema.setName(c.getString(c.getColumnIndex("name")));
        }
        c.close();
        return cinema.getName();
    }
    public long insertCinema(String name, String address) {
        ContentValues values = new ContentValues(); // Lưu trữ dữ liệu để chèn
        values.put("name", name);
        values.put("address", address);

        return db.insert("cinema", null, values); // Chèn vào bảng "cinema"
    }
    public int updateCinema(long id, String name, String address) {
        ContentValues values = new ContentValues(); // Dữ liệu cần cập nhật
        values.put("name", name);
        values.put("address", address);

        return db.update("cinema", values, "id = ?", new String[]{String.valueOf(id)}); // Cập nhật theo ID
    }
    public int deleteCinema(long id) {
        return db.delete("cinema", "id = ?", new String[]{String.valueOf(id)});
    }
}