package com.example.appbanvexemphim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.DAO.TicketDAO;
import com.example.appbanvexemphim.Model.Seat;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SeatAdapter extends ArrayAdapter<Seat> {
    private Context context;
    private int layout;
    private ArrayList<Seat> seats;
    private ArrayList<Ticket> tickets = null;
    private ArrayList<Ticket> ticketsSelected = null;
    private Show curShow;
    private TextView tvSeatSelected, tvTotal;

    public SeatAdapter(@NonNull Context context, int resource, ArrayList<Seat> seats, Show curShow, ArrayList<Ticket> ticketsSelected, TextView tvSeatSelected, TextView tvTotal) {
        super(context, resource, seats);
        this.context = context;
        this.layout = resource;
        this.seats = seats;
        this.ticketsSelected = ticketsSelected;
        this.curShow = curShow;
        this.tvSeatSelected = tvSeatSelected;
        this.tvTotal = tvTotal;
        tickets = TicketDAO.getInstance(context).getByShow(curShow.getId());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_seat, null);
        }
        Button btnSeat = convertView.findViewById(R.id.btnSeat);

        Seat seat = seats.get(position);

        btnSeat.setText(seat.getName());
        if (tickets.get(position).getState() == 0){
            convertView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    if (v.isSelected()) {
                        Log.d("ticket", ticketsSelected.size() + "");
                        v.setSelected(false);
                        Ticket ticket = TicketDAO.getInstance(context).getByShowAndName(curShow.getId(), ((Button) v).getText() + "");
                        if (ticket != null && remove(ticketsSelected, ticket)) {
                            Log.d("ticket", ticketsSelected.size() + "");
                            tvTotal.setText(format.format(Constant.calculateTotal(ticketsSelected, null)));
                            tvSeatSelected.setText(Constant.calculateSeatSelected(ticketsSelected));
                        }
                    } else {
                        v.setSelected(true);
                        Ticket ticket = TicketDAO.getInstance(context).getByShowAndName(curShow.getId(), ((Button) v).getText() + "");
                        if (ticket != null) {
                            ticketsSelected.add(ticket);
                            tvTotal.setText(format.format(Constant.calculateTotal(ticketsSelected, null)));
                            tvSeatSelected.setText(Constant.calculateSeatSelected(ticketsSelected));
                        }
                    }
                }
            });
        }
        else{
            ((Button) convertView).setTextColor(context.getColor(R.color.gray));
            ((Button) convertView).setEnabled(false);
        }

        return convertView;
    }

    private boolean remove(ArrayList<Ticket> tickets, Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (Objects.equals(ticket.getSeatName(), tickets.get(i).getSeatName())) {
                tickets.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Ticket> getTicketsSelected() {
        return ticketsSelected;
    }

    public void setTicketsSelected(ArrayList<Ticket> ticketsSelected) {
        this.ticketsSelected = ticketsSelected;
    }
}
