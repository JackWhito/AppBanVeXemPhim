package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.DAO.BillDAO;
import com.example.appbanvexemphim.DAO.TicketDAO;
import com.example.appbanvexemphim.DAO.TransactionDAO;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.Model.Transaction;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity {
    ImageButton ibtnBackTicket;
    ImageView ivTicketPicture, ivQRCode;
    TextView tvTicketTitle, tvTicketType, tvTicketRequiredAge, tvTicketCinema, tvTicketShow, tvTicketSeat, tvTicketBillID, tvTicketQuantity, tvTicketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        loadWidget();
        loadData();
        addEvent();
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        assert b != null;
        Bill bill = (Bill) b.getSerializable("Bill");
        assert bill != null;
        // [bill_id, name, type, age, cinema, threater, datetime, image]
        String[] history = BillDAO.getInstance(this)
                .getHistoryByBill_id(new String[]{String.valueOf(bill.getId())});

        tvTicketTitle.setText(history[1]);
        tvTicketType.setText(history[2]);
        tvTicketRequiredAge.setText(history[3]);
        tvTicketCinema.setText(history[4] + " - " + history[5]);
        tvTicketShow.setText(history[6]);

        ivTicketPicture.setImageBitmap(ImageConverter.getImage(history[7], this));

        tvTicketBillID.setText("["+bill.getId()+"]");
        ArrayList<Transaction> trans = TransactionDAO.getInstance(this).getAllByBill_id(bill.getId());
        tvTicketQuantity.setText(String.valueOf(trans.size()));
        // Định dạng số tiền sang định dạng tiền tệ của Việt Nam
        float bill_price = BillDAO.getInstance(this).getById(bill.getId()).getPrice();
        Locale vietnameseLocale = new Locale("vi", "VN");
        NumberFormat vietnameseCurrencyFormat = NumberFormat.getCurrencyInstance(vietnameseLocale);
        String formattedPrice = vietnameseCurrencyFormat.format(bill_price);
        tvTicketPrice.setText(String.valueOf(formattedPrice));
        StringBuilder seatName = new StringBuilder();
        for (Transaction tran : trans){
            Ticket ticket = TicketDAO.getInstance(this).getById(tran.getTicket_id());
            seatName.append(ticket.getSeatName());
            seatName.append(", ");
        }
        seatName.delete(seatName.length()-2, seatName.length());
        tvTicketSeat.setText(seatName);
    }

    private void addEvent() {
        ibtnBackTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadWidget() {
        ibtnBackTicket = (ImageButton) findViewById(R.id.ibtnBackTicket);
        ivTicketPicture = (ImageView) findViewById(R.id.ivTicketPicture);
        ivQRCode = (ImageView) findViewById(R.id.ivQRCode);
        tvTicketTitle = (TextView) findViewById(R.id.tvTicketTitle);
        tvTicketType = (TextView) findViewById(R.id.tvTicketType);
        tvTicketRequiredAge = (TextView) findViewById(R.id.tvTicketRequiredAge);
        tvTicketCinema = (TextView) findViewById(R.id.tvTicketCinema);
        tvTicketShow = (TextView) findViewById(R.id.tvTicketShow);
        tvTicketSeat = (TextView) findViewById(R.id.tvTicketSeat);
        tvTicketBillID = (TextView) findViewById(R.id.tvTicketBillID);
        tvTicketQuantity = (TextView) findViewById(R.id.tvTicketQuantity);
        tvTicketPrice = (TextView) findViewById(R.id.tvTicketPrice);
    }
}