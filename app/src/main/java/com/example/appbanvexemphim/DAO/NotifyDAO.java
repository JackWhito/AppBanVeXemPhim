package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Notify;

import java.util.ArrayList;

public class NotifyDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static NotifyDAO instance;
    public static NotifyDAO getInstance(Context context){
        if (instance == null){
            instance = new NotifyDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;
    public NotifyDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    @SuppressLint({"Recycle","Range"})
    public ArrayList<Notify> getByUser(int user_id){
        ArrayList<Notify> notifies = new ArrayList<>();
        String sql = "SELECT * FROM notify WHERE user_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        while (c.moveToNext()) {
            Notify notify = new Notify();
            notify.setId(c.getInt(c.getColumnIndex("id")));
            notify.setTitle(c.getString(c.getColumnIndex("title")));
            notify.setContent(c.getString(c.getColumnIndex("content")));
            notify.setUser_id(c.getInt(c.getColumnIndex("user_id")));
            notifies.add(notify);
        }
        c.close();
        return notifies;
    }
    @SuppressLint("Range")
    public ArrayList<Notify> getAll() {
        ArrayList<Notify> notifies = new ArrayList<>();
        String sql = "SELECT * FROM notify";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Notify notify = new Notify();

            notify.setId(c.getInt(c.getColumnIndex("id"))); // ID của bộ phim
            notify.setTitle(c.getString(c.getColumnIndex("title"))); // Tên bộ phim
            notify.setContent(c.getString(c.getColumnIndex("content"))); // Thời lượng
            notify.setUser_id(c.getInt(c.getColumnIndex("user_id")));

            notifies.add(notify);
        }
        c.close();
        return notifies;
    }
    public void insertNotify(String title, String content, int userID) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("user_id", userID);

        db.insert("notify", null, values);
    }
    public void updateNotify(int id, String title, String content, int userID) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("user_id", userID);

        db.update("notify",values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void deleteNotify(int id) {
        db.delete("notify", "id = ?", new String[]{String.valueOf(id)});
    }
}
