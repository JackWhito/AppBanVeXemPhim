package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.Activity.MovieDetailActivity;
import com.example.appbanvexemphim.Activity.SeatActivity;
import com.example.appbanvexemphim.Activity.ShowActivity;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowPieceAdapter extends ArrayAdapter<Show> {
    private Context context;
    private int layout;
    private ArrayList<Show> shows;
    private Cinema cinema = null;
    public ShowPieceAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Show> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.shows = objects;
    }

    public ShowPieceAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Show> objects, Cinema cinema) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.shows = objects;
        this.cinema = cinema;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);
        }

        Button btn = convertView.findViewById(R.id.btnShow);

        Show show = shows.get(position);
        String[] str = show.getDatetime().split(" - ");
        btn.setText(str[1]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("shows", shows);
                b.putSerializable("show", show);
                b.putSerializable("movie", MovieDAO.getInstance(context).getById(show.getMovie_id()));
                if (cinema != null){
                    b.putSerializable("cinema", cinema);
                }
                else{
                    // Nếu như không có cinema thì set một cinema rỗng và truyền đi
                    cinema = new Cinema();
                    cinema.setName("[Không có]");
                    b.putSerializable("cinema", cinema);
                }
                intent.putExtra("Data", b);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
