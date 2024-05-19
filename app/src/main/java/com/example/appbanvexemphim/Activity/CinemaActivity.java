package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanvexemphim.Adapter.CinemaAdapter;
import com.example.appbanvexemphim.Adapter.ShowAdapter;
import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CinemaActivity extends AppCompatActivity {
    private Cinema cinema;
    private TextView tvCinema;
    private ImageButton ibtnBack;
    private HorizontalScrollView hsvCinema;
    private ListView lvMovie;
    private LinearLayout llDatetimePicker;
    private View dateSelected;
    private ArrayList<Movie> movies;
    private ArrayList<Show> shows;
    private ShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        cinema = (Cinema) b.getSerializable("cinema");
        loadWidgets();
        addEvents();
        loadData();
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists", "SetTextI18n"})
    private void loadData() {
        // Thêm 14 item vào LinearLayout
        for (int i = 0; i < 14; i++) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date); // Set thời gian của calendar thành ngày hiện tại

            calendar.add(Calendar.DAY_OF_YEAR, i); // Tăng ngày lên i

            LinearLayout item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_item_date, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.date_item_width),
                    getResources().getDimensionPixelSize(R.dimen.date_item_height)
            );

            TextView dayOfWeek = (TextView) item.getChildAt(0);
            dayOfWeek.setText(Constant.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));

            TextView datetime = (TextView) item.getChildAt(1);
            datetime.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1));

            if (i==0){
                item.setSelected(true);
                dayOfWeek.setTextColor(getResources().getColorStateList(R.drawable.button_datepicker_text_special));;
                datetime.setTextColor(getResources().getColorStateList(R.drawable.button_datepicker_text_special));
                dateSelected = item;
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dateSelected != null)
                        dateSelected.setSelected(false);

                    dateSelected = v;
                    v.setSelected(true);
                    getMovie(calendar.getTime());
                }

                private void getMovie(Date newDate) {
                    movies = new ArrayList<>();
                    ArrayList<Integer> moviesId = MovieDAO.getInstance(CinemaActivity.this).getMovieIdByShowAndCinema(cinema.getId(), newDate);
                    movies.clear();
                    for (Integer movieId : moviesId){
                        movies.add(MovieDAO.getInstance(CinemaActivity.this).getById(movieId.intValue()));
                    }
                    adapter.notifyDataSetChanged();
                    lvMovie = (ListView) findViewById(R.id.lvMovie);
                    adapter = new ShowAdapter(CinemaActivity.this, R.layout.layout_item_show, movies, cinema, newDate);
                    lvMovie.setAdapter(adapter);

                }
            });
            layoutParams.setMarginEnd(5);
            llDatetimePicker.addView(item, layoutParams);
        }

        tvCinema.setText(cinema.getName());

    }
    private void addEvents() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "ResourceType", "UseCompatLoadingForColorStateLists"})
    private void loadWidgets() {
        tvCinema = (TextView) this.findViewById(R.id.tvCinema);
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        hsvCinema = (HorizontalScrollView) findViewById(R.id.hsvCinema);
        llDatetimePicker = (LinearLayout) findViewById(R.id.llDatetimePicker);

        movies = new ArrayList<>();
        ArrayList<Integer> moviesId = MovieDAO.getInstance(this).getMovieIdByShowAndCinema(cinema.getId(), new Date());
        for (Integer movieId : moviesId){
            movies.add(MovieDAO.getInstance(this).getById(movieId.intValue()));
        }
        lvMovie = (ListView) findViewById(R.id.lvMovie);
        adapter = new ShowAdapter(this, R.layout.layout_item_show, movies, cinema, new Date());
        lvMovie.setAdapter(adapter);
    }

}
