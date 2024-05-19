package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.R;


public class BillAdapter {

    private Context context;
    private Bill bill;

    public BillAdapter(Context context, Bill bill) {
        this.context = context;
        this.bill = bill;
    }

    public View getView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_bill, parent, false);

        TextView movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
        TextView movieOldTextView = view.findViewById(R.id.movieOldTextView);
        TextView movieTimeTextView = view.findViewById(R.id.movieTimeTextView);
        TextView movieDateTextView = view.findViewById(R.id.movieDateTextView);
        TextView movieGenreTextView = view.findViewById(R.id.movieGenreTextView);
        TextView ticketQuantityTextView = view.findViewById(R.id.textView_ticket_quantity);
        TextView ticketPriceTextView = view.findViewById(R.id.textView_ticket_price);
        TextView comboTextView = view.findViewById(R.id.textView_combo);
        TextView comboPriceTextView = view.findViewById(R.id.textView_combo_price);
        TextView totalAmountTextView = view.findViewById(R.id.textView_total_amount);

        return view;
    }
}

