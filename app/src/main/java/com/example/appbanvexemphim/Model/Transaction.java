package com.example.appbanvexemphim.Model;

public class Transaction {
    private int id;
    private int bill_id;
    private int ticket_id;

    public Transaction() {
    }

    public Transaction(int id, int bill_id, int ticket_id) {
        this.id = id;
        this.bill_id = bill_id;
        this.ticket_id = ticket_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }
}
