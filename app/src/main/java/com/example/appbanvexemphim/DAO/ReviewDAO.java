package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanvexemphim.Activity.MainActivity;
import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.R;

import java.util.ArrayList;

public class ReviewDAO extends DBHelper{
    @SuppressLint("StaticFieldLeak")
    private static ReviewDAO instance;
    public static ReviewDAO getInstance(Context context){
        if (instance == null){
            instance = new ReviewDAO(context);
        }
        return instance;
    }

    private final SQLiteDatabase db;
    public ReviewDAO(Context context) {
        super(context);
        db = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    @SuppressLint("Recycle")
    public ArrayList<Review> getAll() {
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int movie_id = c.getInt(2);
            String title = c.getString(3);
            String content = c.getString(4);
            String picture = c.getString(5);
            reviews.add(new Review(id, user_id, movie_id, title, content, picture));
        }
        c.close();
        return reviews;
    }
    public ArrayList<Review> search(String searchString){
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE title LIKE ?";
        Cursor c = db.rawQuery(sql, new String[]{"%" + searchString + "%"});
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int movie_id = c.getInt(2);
            String title = c.getString(3);
            String content = c.getString(4);
            String picture = c.getString(5);
            reviews.add(new Review(id, user_id, movie_id, title, content, picture));
        }
        return reviews;
    }
    public void deleteReview(int reviewId) {
        db.delete("review", "id = ?", new String[]{String.valueOf(reviewId)}); // Xóa theo ID
    }
    public void insertReview(int userID, int movieID, String title, String content, String picture) {
        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("movie_id", movieID);
        values.put("title", title);
        values.put("content", content);
        values.put("picture", picture);

        db.insert("review", null, values);
    }
    public void updateReview(int id, int userID, int movieID, String title, String content, String picture) {
        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("movie_id", movieID);
        values.put("title", title);
        values.put("content", content);
        values.put("picture", picture);

        db.update("review", values, "id = ?", new String[]{String.valueOf(id)}); // Cập nhật theo ID
    }
}
