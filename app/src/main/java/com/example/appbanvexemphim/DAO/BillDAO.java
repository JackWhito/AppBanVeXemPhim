package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Bill;

import java.util.ArrayList;

public class BillDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static BillDAO instance;

    public static BillDAO getInstance(Context context) {
        if (instance == null) {
            instance = new BillDAO(context);
        }
        return instance;
    }

    private final SQLiteDatabase db;

    public BillDAO(Context context) {
        super(context);
        db = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @SuppressLint({"Recycle", "Range", "SimpleDateFormat"})
    public ArrayList<Bill> getAll() {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill";
        Cursor c = db.rawQuery(sql, null);
        try {
            while (c.moveToNext()) {
                Log.d("info", "getAll Bill");
                int id = c.getInt(c.getColumnIndex("id"));
                int user_id = c.getInt(c.getColumnIndex("user_id"));
                String create_date = c.getString(c.getColumnIndex("create_date"));
                float price = c.getFloat(c.getColumnIndex("price"));
                int state = c.getInt(c.getColumnIndex("state"));
                int payment = c.getInt(c.getColumnIndex("payment"));
                int service_id = c.getInt(c.getColumnIndex("service_id"));
                bills.add(new Bill(id, user_id, create_date, price, state, payment, service_id));
            }
            c.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bills;
    }
    @SuppressLint({"Recycle", "Range", "SimpleDateFormat"})
    public ArrayList<Bill> getAllByUser_id(String [] params) {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE user_id = ?";
        Cursor c = db.rawQuery(sql, params);
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("id"));
                int user_id = c.getInt(c.getColumnIndex("user_id"));
                String create_date = c.getString(c.getColumnIndex("create_date"));
                float price = c.getFloat(c.getColumnIndex("price"));
                int state = c.getInt(c.getColumnIndex("state"));
                int payment = c.getInt(c.getColumnIndex("payment"));
                int service_id = c.getInt(c.getColumnIndex("service_id"));
                bills.add(new Bill(id, user_id, create_date, price, state, payment, service_id));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bills;
    }
    @SuppressLint({"Recycle","Range", "SimpleDateFormat"})
    public Bill getById(int bill_id){
        Bill bill = new Bill();
        String sql = "SELECT * FROM bill WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(bill_id)});
        try{
            while (c.moveToNext()) {
                bill.setId(c.getInt(c.getColumnIndex("id")));
                bill.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                bill.setCreate_date(c.getString(c.getColumnIndex("create_date")));
                bill.setPrice(c.getFloat(c.getColumnIndex("price")));
                bill.setState(c.getInt(c.getColumnIndex("state")));
                bill.setPayment(c.getInt(c.getColumnIndex("payment")));
                bill.setService_id(c.getInt(c.getColumnIndex("service_id")));
            }
            return bill;
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    @SuppressLint({"Recycle","Range", "SimpleDateFormat"})
    public Bill getLastBill(){
        Bill bill = new Bill();
        String sql = "SELECT * FROM bill ORDER BY id desc LIMIT 1";
        Cursor c = db.rawQuery(sql, null);
        try{
            while (c.moveToNext()) {
                bill.setId(c.getInt(c.getColumnIndex("id")));
                bill.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                bill.setCreate_date(c.getString(c.getColumnIndex("create_date")));
                bill.setPrice(c.getFloat(c.getColumnIndex("price")));
                bill.setState(c.getInt(c.getColumnIndex("state")));
                bill.setPayment(c.getInt(c.getColumnIndex("payment")));
                bill.setService_id(c.getInt(c.getColumnIndex("service_id")));
            }
            return bill;
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    @SuppressLint({"Recycle", "Range"})
    public String[] getHistoryByBill_id(String[] params) {
        String[] history = new String[8];
        String sql = "SELECT * FROM viewHistories WHERE bill_id = ?";
        Cursor c = db.rawQuery(sql, params);
        while (c.moveToNext()) {
            history[0] = String.valueOf(c.getInt(c.getColumnIndex("bill_id")));
            history[1] = c.getString(c.getColumnIndex("name"));
            history[2] = c.getString(c.getColumnIndex("type"));
            history[3] = c.getString(c.getColumnIndex("age"));
            history[4] = c.getString(c.getColumnIndex("cinema"));
            history[5] = c.getString(c.getColumnIndex("threater"));
            history[6] = c.getString(c.getColumnIndex("datetime"));
            history[7] = c.getString(c.getColumnIndex("image"));
        }
        return history;
    }
    public boolean insert(Bill bill){
        ContentValues values = new ContentValues();
        values.put("user_id", bill.getUser_id());
        values.put("create_date", bill.getCreate_date());
        values.put("price", bill.getPrice());
        values.put("state", bill.getState());
        values.put("payment", bill.getPayment());
        values.put("service_id", bill.getService_id());
        long flag = db.insert("bill", null, values);
        return flag > 0;
    }
    public int update(Bill bill) {
        ContentValues values = new ContentValues();
        values.put("user_id", bill.getUser_id());
        values.put("create_date", bill.getCreate_date());
        values.put("price", bill.getPrice());
        values.put("state", bill.getState());
        values.put("payment", bill.getPayment());
        values.put("service_id", bill.getService_id());

        return db.update("bill", values, "id = ?", new String[]{String.valueOf(bill.getId())}); // Cập nhật theo ID
    }
    public void insertBill(int userID, String createdate, float price, int state, int payment, int service) {
        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("create_date", createdate);
        values.put("price", price);
        values.put("state", state);
        values.put("payment", payment);
        values.put("service_id", service);

        db.insert("bill", null, values);
    }
    public void updateBill(int id, int userID, String createdate, float price, int state, int payment, int service) {
        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("create_date", createdate);
        values.put("price", price);
        values.put("state", state);
        values.put("payment", payment);
        values.put("service_id", service);


        db.update("bill",values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void deleteBill(int id) {
        db.delete("bill", "id = ?", new String[]{String.valueOf(id)});
    }
}
