package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.Model.User;

import java.util.ArrayList;

public class TicketDAO extends DBHelper {
    private static TicketDAO instance;
    public static TicketDAO getInstance(Context context){
        if (instance == null){
            instance = new TicketDAO(context);
        }
        return instance;
    }
    private SQLiteDatabase db;
    private TicketDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    @SuppressLint({"Recycle","Range"})
    public Ticket getByBill(int bill_id){
        Ticket ticket = new Ticket();
        String sql = "SELECT * FROM viewTicketAndBill_id WHERE bill_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(bill_id)});
        while (c.moveToNext()) {
            ticket.setId(c.getInt(c.getColumnIndex("id")));
            ticket.setShow_id(c.getInt(c.getColumnIndex("show_id")));
            ticket.setSeatName(c.getString(c.getColumnIndex("seatName")));
            ticket.setPrice(c.getFloat(c.getColumnIndex("price")));
            ticket.setState(c.getInt(c.getColumnIndex("state")));
        }
        return ticket;
    }
    @SuppressLint({"Recycle","Range"})
    public Ticket getById(int ticket_id){
        Ticket ticket = new Ticket();
        String sql = "SELECT * FROM ticket WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(ticket_id)});
        while (c.moveToNext()) {
            ticket.setId(c.getInt(c.getColumnIndex("id")));
            ticket.setShow_id(c.getInt(c.getColumnIndex("show_id")));
            ticket.setSeatName(c.getString(c.getColumnIndex("seatName")));
            ticket.setPrice(c.getFloat(c.getColumnIndex("price")));
            ticket.setState(c.getInt(c.getColumnIndex("state")));
        }
        return ticket;
    }
    @SuppressLint({"Recycle","Range"})
    public Ticket getByShowAndName(int show_id, String name){
        Ticket ticket = new Ticket();
        String sql = "SELECT * FROM ticket WHERE show_id = ? AND seatName like ?";
        Cursor c = db.rawQuery(sql, new String[]{show_id+"", "%" + name + "%"});
        while (c.moveToNext()) {
            ticket.setId(c.getInt(c.getColumnIndex("id")));
            ticket.setShow_id(c.getInt(c.getColumnIndex("show_id")));
            ticket.setSeatName(c.getString(c.getColumnIndex("seatName")));
            ticket.setPrice(c.getFloat(c.getColumnIndex("price")));
            ticket.setState(c.getInt(c.getColumnIndex("state")));
        }
        return ticket;
    }
    @SuppressLint({"Recycle","Range"})
    public ArrayList<Ticket> getByShow(int show_id){
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        String sql = "SELECT * FROM ticket WHERE show_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{show_id+""});
        while (c.moveToNext()) {
            Ticket ticket = new Ticket();
            ticket.setId(c.getInt(c.getColumnIndex("id")));
            ticket.setShow_id(c.getInt(c.getColumnIndex("show_id")));
            ticket.setSeatName(c.getString(c.getColumnIndex("seatName")));
            ticket.setPrice(c.getFloat(c.getColumnIndex("price")));
            ticket.setState(c.getInt(c.getColumnIndex("state")));
            tickets.add(ticket);
        }
        return tickets;
    }
}
