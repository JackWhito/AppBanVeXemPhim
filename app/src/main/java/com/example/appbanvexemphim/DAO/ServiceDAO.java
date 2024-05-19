package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends DBHelper {
    private static ServiceDAO instance;
    public static ServiceDAO getInstance(Context context){
        if (instance == null){
            instance = new ServiceDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;

    public ServiceDAO(Context context) {
        super(context); // Kế thừa từ DBHelper
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null); // Mở cơ sở dữ liệu
    }

    // Thêm dịch vụ mới (Create)
    public void insertService(String name, String description, int price, String imageResource) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("picture", imageResource);

        db.insert("service", null, values);
    }

    // Đọc tất cả dịch vụ (Read)
    @SuppressLint("Range")
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service"; // Truy vấn tất cả dịch vụ
        Cursor c = db.rawQuery(sql, null); // Thực hiện truy vấn
        while (c.moveToNext()) {
            Service service = new Service(); // Tạo đối tượng Service
            service.setId(c.getInt(c.getColumnIndex("id"))); // ID của dịch vụ
            service.setName(c.getString(c.getColumnIndex("name"))); // Tên dịch vụ
            service.setDescription(c.getString(c.getColumnIndex("description"))); // Mô tả
            service.setPrice(c.getInt(c.getColumnIndex("price"))); // Giá
            service.setPicture(c.getString(c.getColumnIndex("picture"))); // Hình ảnh

            services.add(service); // Thêm vào danh sách
        }
        c.close(); // Đóng Cursor
        return services; // Trả về danh sách dịch vụ
    }

    // Cập nhật dịch vụ (Update)
    public int updateService(int id, String name, String description, int price, String imageResource) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("picture", imageResource);

        return db.update("service", values, "id = ?", new String[]{String.valueOf(id)}); // Cập nhật theo ID
    }

    // Xóa dịch vụ (Delete)
    public int deleteService(int id) {
        return db.delete("service", "id = ?", new String[]{String.valueOf(id)}); // Xóa theo ID
    }
}
