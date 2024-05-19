package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Show;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowDAO extends DBHelper {
    private static ShowDAO instance;
    public static ShowDAO getInstance(Context context){
        if (instance == null){
            instance = new ShowDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;
    public ShowDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @SuppressLint("Range")
    public ArrayList<Show> getShowByCinema(int cinema_id, int movie_id, Date date) {
        ArrayList<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM viewShowByCinema WHERE threater_id = ? AND movie_id = ? AND datetime like ? ORDER BY datetime";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String str = "%" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR) + "%";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(cinema_id),String.valueOf(movie_id), str});
        while (c.moveToNext()) {
            Show show = new Show();
            show.setId(c.getInt(c.getColumnIndex("id")));
            show.setMovie_id(c.getInt(c.getColumnIndex("movie_id")));
            show.setThreater_id(c.getInt(c.getColumnIndex("threater_id")));
            show.setDatetime(c.getString(c.getColumnIndex("datetime")));
            shows.add(show);
        }
        c.close();
        return shows;
    }

    @SuppressLint("Range")
    public ArrayList<Show> getAll() {
        ArrayList<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM show";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Show show = new Show();

            show.setId(c.getInt(c.getColumnIndex("id"))); // ID của bộ phim
            show.setMovie_id(c.getInt(c.getColumnIndex("movie_id"))); // Tên bộ phim
            show.setThreater_id(c.getInt(c.getColumnIndex("threater_id"))); // Thời lượng
            show.setDatetime(c.getString(c.getColumnIndex("datetime")));

            shows.add(show);
        }
        c.close();
        return shows;
    }

    public void insertShow(int movieID, int theaterID, String date) {
        ContentValues values = new ContentValues();
        values.put("movie_id", movieID);
        values.put("threater_id", theaterID);
        values.put("datetime", date);

        db.insert("show", null, values);
    }
    public void updateShow(int id, int movieID, int theaterID, String date) {
        ContentValues values = new ContentValues();
        values.put("movie_id", movieID);
        values.put("threater_id", theaterID);
        values.put("datetime", date);

        db.update("show",values, "id = ?", new String[]{String.valueOf(id)});
    }
    public int deleteShow(int id) {
        return db.delete("show", "id = ?", new String[]{String.valueOf(id)});
    }
}
