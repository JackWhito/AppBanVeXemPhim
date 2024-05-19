package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.MovieType;
import com.example.appbanvexemphim.Model.Ticket;

public class MovieTypeDAO extends DBHelper {
    private static MovieTypeDAO instance;
    public static MovieTypeDAO getInstance(Context context){
        if (instance == null){
            instance = new MovieTypeDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;
    public MovieTypeDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    @SuppressLint({"Recycle","Range"})
    public MovieType getById(int id){
        MovieType movie_type = new MovieType();
        String sql = "SELECT * FROM movie_type WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            movie_type.setId(c.getInt(c.getColumnIndex("id")));
            movie_type.setName(c.getString(c.getColumnIndex("name")));
        }
        return movie_type;
    }
}
