package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatDAO extends DBHelper {
    private static SeatDAO instance;
    public static SeatDAO getInstance(Context context){
        if (instance == null){
            instance = new SeatDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;
    public SeatDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    @SuppressLint({"Recycle","Range"})
    public List<Seat> getListSeated(int ThreaterId){
        List<Seat> listSeat = new ArrayList<>();

        String sql = "SELECT * FROM seat WHERE threater_id= ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(ThreaterId)});
        while (c.moveToNext()) {
            Seat seat=new Seat();
            seat.setThreaterId(c.getInt(c.getColumnIndex("threater_id")));

            seat.setName(c.getString(c.getColumnIndex("name")));
            listSeat.add(seat);

        }
        c.close();

        return listSeat;
    }
}
