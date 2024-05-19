package com.example.appbanvexemphim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.DAO.BillDAO;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;

public class AdapterHistory extends ArrayAdapter<Bill> {
    private Context context;
    private int layout;
    private ArrayList<Bill> bills;
    public AdapterHistory(@NonNull Context context, int resource, ArrayList<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.bills = objects;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }
        ImageView ivHistoryItemImage = (ImageView) convertView.findViewById(R.id.ivHistoryItemPicture);
        TextView tvHistoryItemTitle = (TextView) convertView.findViewById(R.id.tvHistoryItemTitle);
        TextView tvHistoryItemType = (TextView) convertView.findViewById(R.id.tvHistoryItemType);
        TextView tvHistoryItemThreater = (TextView) convertView.findViewById(R.id.tvHistoryItemThreater);
        TextView tvHistoryItemDatetime = (TextView) convertView.findViewById(R.id.tvHistoryItemDatetime);
        TextView tvState = (TextView) convertView.findViewById(R.id.tvState);

        // [bill_id, name, type, age, cinema, threater, datetime, image]
        String[] history = BillDAO.getInstance(context)
                .getHistoryByBill_id(new String[]{String.valueOf(bills.get(position).getId())});

        tvHistoryItemTitle.setText(history[1]);
        tvHistoryItemType.setText(history[2] + " - " + history[3]);
        tvHistoryItemThreater.setText(history[4] + " - " + history[5]);
        tvHistoryItemDatetime.setText(history[6]);
        ivHistoryItemImage.setImageBitmap(ImageConverter.getImage(history[7], context));

        if (bills.get(position).getState() == 0){
            tvState.setText("Chưa thanh toán");
            tvState.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if (bills.get(position).getState() == 1){
            tvState.setText("Đã thanh toán");
            tvState.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            tvState.setText("Hết hạn");
            tvState.setTextColor(context.getResources().getColor(R.color.black));
        }

        return convertView;
    }
}
