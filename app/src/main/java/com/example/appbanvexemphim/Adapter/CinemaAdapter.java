package com.example.appbanvexemphim.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanvexemphim.Activity.CinemaActivity;
import com.example.appbanvexemphim.Activity.MainActivity;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdapter extends ArrayAdapter<Cinema> {
    private Context context;
    private int layout;
    private ArrayList<Cinema> cinemas;
    public CinemaAdapter(@NonNull Context context, int resource, ArrayList<Cinema> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.cinemas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }

        ImageView cinemaImageView = (ImageView) convertView.findViewById(R.id.cinemaImageView);
        TextView cinemaNameTextView = (TextView) convertView.findViewById(R.id.cinemaNameTextView);
        TextView cinemaAddressTextView = (TextView) convertView.findViewById(R.id.cinemaAddressTextView);

        Cinema cinema = cinemas.get(position);

        cinemaImageView.setImageBitmap(ImageConverter.getImage(cinema.getPicture(), context));
        cinemaNameTextView.setText(cinema.getName());
        cinemaAddressTextView.setText(cinema.getAddress());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CinemaActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("cinema", cinema);
                i.putExtra("Data", b);
                context.startActivity(i);
            }
        });

        return convertView;
    }

}
