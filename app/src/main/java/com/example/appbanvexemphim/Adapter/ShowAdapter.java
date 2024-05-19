package com.example.appbanvexemphim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieTypeDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class ShowAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private int layout;
    private ArrayList<Movie> movies;
    private ArrayList<Show> shows;
    private Cinema cinema = null;
    Calendar calendar = Calendar.getInstance();
    public ShowAdapter(@NonNull Context context, int resource, ArrayList<Movie> movies, Cinema cinema, Date date) {
        super(context, resource, movies);
        this.context = context;
        this.layout = resource;
        this.movies = movies;
        this.cinema = cinema;
        calendar.setTime(date); // Set thời gian của calendar thành ngày hiện tại
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }

        ImageView ivMovie = convertView.findViewById(R.id.ivMovie);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvAge = convertView.findViewById(R.id.tvAge);
        TextView tvDuration = convertView.findViewById(R.id.tvDuration);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvRating = convertView.findViewById(R.id.tvRating);
        GridView gvShow = convertView.findViewById(R.id.gvShow);

        Movie movie = movies.get(position);

        ivMovie.setImageBitmap(ImageConverter.getImage(movie.getImage(), context));
        tvTitle.setText(movie.getName());
        tvType.setText(MovieTypeDAO.getInstance(context).getById(movie.getType_id()).getName());
        tvAge.setText(String.valueOf(movie.getRequireAge()));
        tvDuration.setText(movie.getDuration() + " phút");
        tvDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR));
        tvRating.setText(String.valueOf(movie.getRating()));

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
