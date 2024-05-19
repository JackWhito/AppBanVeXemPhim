package com.example.appbanvexemphim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.DAO.MovieTypeDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowByCinemaAdapter extends ArrayAdapter<Cinema> {
    private Context context;
    private int layout;
    private ArrayList<Cinema> cinemas;
    private ArrayList<Show> shows;
    private Movie movie = null;
    Calendar calendar = Calendar.getInstance();
    public ShowByCinemaAdapter(@NonNull Context context, int resource, ArrayList<Cinema> cinemas, Movie movie, Date date) {
        super(context, resource, cinemas);
        this.context = context;
        this.layout = resource;
        this.cinemas = cinemas;
        this.movie = movie;
        calendar.setTime(date); // Set thời gian của calendar thành ngày hiện tại
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }

        TextView tvCinema = convertView.findViewById(R.id.tvCinema);
        GridView gvShow = convertView.findViewById(R.id.gvShow);

        Cinema cinema = cinemas.get(position);

        tvCinema.setText(cinema.getName());

        shows = ShowDAO.getInstance(context).getShowByCinema(cinema.getId(), movie.getId(), calendar.getTime());
        ShowPieceAdapter adapter = new ShowPieceAdapter(context, R.layout.layout_item_show_piece, shows, cinema);
        int row = -1;
        if (shows.size()%4 == 0){
            row = shows.size()/4;
        }
        else{
            row = shows.size()/4 + 1;
        }
        ViewGroup.LayoutParams layoutParams = gvShow.getLayoutParams();
        layoutParams.height = 126*row + 8*2*row; // Đặt chiều cao ở đây (đơn vị pixel)
        gvShow.setLayoutParams(layoutParams);
        gvShow.setAdapter(adapter);

        return convertView;
    }
}
