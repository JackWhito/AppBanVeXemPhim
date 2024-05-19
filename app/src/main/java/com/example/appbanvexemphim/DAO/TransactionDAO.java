package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.Model.Transaction;

import java.util.ArrayList;

public class TransactionDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static TransactionDAO instance;

    public static TransactionDAO getInstance(Context context) {
        if (instance == null) {
            instance = new TransactionDAO(context);
        }
        return instance;
    }

    private final SQLiteDatabase db;

    public TransactionDAO(Context context) {
        super(context);
        db = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @SuppressLint({"Recycle", "Range"})
    public ArrayList<Transaction> getAll() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM review";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int bill_id = c.getInt(c.getColumnIndex("bill_id"));
            int ticket_id = c.getInt(c.getColumnIndex("ticket_id"));
            transactions.add(new Transaction(id,bill_id,ticket_id));
        }
        return transactions;
    }
    @SuppressLint({"Recycle", "Range"})
    public ArrayList<Transaction> getAllByBill_id(int bill_ID) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM trans WHERE bill_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(bill_ID)});
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int bill_id = c.getInt(c.getColumnIndex("bill_id"));
            int ticket_id = c.getInt(c.getColumnIndex("ticket_id"));
            transactions.add(new Transaction(id,bill_id,ticket_id));
        }
        return transactions;
    }
    public boolean insert(Transaction trans){
        ContentValues values = new ContentValues();
        values.put("bill_id", trans.getBill_id());
        values.put("ticket_id", trans.getTicket_id());
        long flag = db.insert("trans", null, values);
        return flag > 0;
    }
}
