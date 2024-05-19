package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static UserDAO instance;
    public static UserDAO getInstance(Context context){
        if (instance == null){
            instance = new UserDAO(context);
        }
        return instance;
    }
    public UserDAO(Context context) {
        super(context);
        db = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    private final SQLiteDatabase db;
    @SuppressLint({"Recycle", "Range"})
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String username = c.getString(c.getColumnIndex("username"));
            String password = c.getString(c.getColumnIndex("password"));
            String picture = c.getString(c.getColumnIndex("picture"));
            String fullname = c.getString(c.getColumnIndex("fullname"));
            String birthdate = c.getString(c.getColumnIndex("birthdate"));
            String address = c.getString(c.getColumnIndex("address"));
            String phone = c.getString(c.getColumnIndex("phone"));
            String email = c.getString(c.getColumnIndex("email"));
            int gender = c.getInt(c.getColumnIndex("gender"));
            int role = c.getInt(c.getColumnIndex("role"));
            users.add(new User(id, username, password, picture, fullname, gender, address, birthdate, phone, email, role));
        }
        return users;
    }
    @SuppressLint({"Recycle","Range"})
    public User getById(int user_id){
        User user = new User();
        String sql = "SELECT * FROM user WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        while (c.moveToNext()) {
            user.setId(c.getInt(c.getColumnIndex("id")));
            user.setUsername(c.getString(c.getColumnIndex("username")));
            user.setPassword(c.getString(c.getColumnIndex("password")));
            user.setPicture(c.getString(c.getColumnIndex("picture")));
            user.setFullname(c.getString(c.getColumnIndex("fullname")));
            user.setGender(c.getInt(c.getColumnIndex("gender")));
            user.setBirthdate(c.getString(c.getColumnIndex("birthdate")));
            user.setAddress(c.getString(c.getColumnIndex("address")));
            user.setPhone(c.getString(c.getColumnIndex("phone")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setRole(c.getInt(c.getColumnIndex("role")));
        }
        c.close();
        return user;
    }
    @SuppressLint({"Recycle","Range"})
    public String getNamebyID(int user_id){
        User user = new User();
        String sql = "SELECT fullname FROM user WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        while (c.moveToNext()) {
            user.setFullname(c.getString(c.getColumnIndex("fullname")));
        }
        c.close();
        return user.getFullname();
    }
    @SuppressLint({"Recycle","Range"})
    public User login(String username, String password){
        User user = new User();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Cursor c = db.rawQuery(sql, new String[]{username, password});
        if (c.moveToNext()) {
            user.setId(c.getInt(c.getColumnIndex("id")));
            user.setUsername(c.getString(c.getColumnIndex("username")));
            user.setPassword(c.getString(c.getColumnIndex("password")));
            user.setPicture(c.getString(c.getColumnIndex("picture")));
            user.setFullname(c.getString(c.getColumnIndex("fullname")));
            user.setGender(c.getInt(c.getColumnIndex("gender")));
            user.setBirthdate(c.getString(c.getColumnIndex("birthdate")));
            user.setAddress(c.getString(c.getColumnIndex("address")));
            user.setPhone(c.getString(c.getColumnIndex("phone")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setRole(c.getInt(c.getColumnIndex("role")));
            return user;
        }
        else{
            return null;
        }
    }
    public boolean register(String username, String fullname, String password, String email){
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("fullname",fullname);
        values.put("password", password);
        values.put("email", email);
        values.put("role", 2);
        long flag = db.insert("user", null, values);
        return flag > 0;
    }

    public int updateUser(int id, String fullname,String date,String address, String email, String phone,int gender) {
        ContentValues values = new ContentValues();
        values.put("fullname", fullname);

        values.put("email", email);
        values.put("phone", phone);
        values.put("gender", gender);
        values.put("birthdate", date);
        values.put("address", address);
        values.put("gender", gender);
        return db.update("user", values, "id = ?", new String[]{String.valueOf(id)});

    }
    // Thêm tài khoản mới (Create)
    public long insertUser(String username, String email, String phone, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("phone", phone);
        values.put("password", password);

        return db.insert("user", null, values); // Chèn vào bảng "Users"
    }


    // Cập nhật tài khoản (Update)
    public int updateUser(int id, String username, String email, String phone, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("phone", phone);
        values.put("password", password);

        return db.update("user", values, "id = ?", new String[]{String.valueOf(id)}); // Cập nhật theo ID
    }

    // Xóa tài khoản (Delete)
    public int deleteUser(int id) {
        return db.delete("user", "id = ?", new String[]{String.valueOf(id)});
    }
}
