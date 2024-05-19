package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanvexemphim.Adapter.ShowAdapter;
import com.example.appbanvexemphim.Adapter.ShowByCinemaAdapter;
import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.MovieTypeDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MovieActivity extends AppCompatActivity {
    private ArrayList<Cinema> cinemas;
    private Movie movie;
    private HorizontalScrollView hsvCinema;
    private LinearLayout llDatetimePicker;
    private View dateSelected;
    private ImageButton ibtnBack;
    private ImageView ivPicture;
    private TextView tvTitle, tvType, tvAge, tvDuration, tvRating, txtDesc;
    private ListView lvCinema;
    private ArrayList<Show> shows;
    private ShowByCinemaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getData();
        loadWidgets();
        loadData();
        addEvents();
    }
    private void addEvents() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint({"SetTextI18n", "ResourceType", "UseCompatLoadingForColorStateLists"})
    private void loadData() {
        ivPicture.setImageBitmap(ImageConverter.getImage(movie.getImage(), this));
        tvTitle.setText(movie.getName());
        tvType.setText(MovieTypeDAO.getInstance(this).getById(movie.getType_id()).getName());
        tvAge.setText(String.valueOf(movie.getRequireAge()));
        tvDuration.setText(movie.getDuration() + " phút");
        tvRating.setText(String.valueOf(movie.getRating()));
        txtDesc.setText(movie.getDescription());
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
                    cinemas = new ArrayList<>();
                    ArrayList<Integer> cinemasId = MovieDAO.getInstance(MovieActivity.this).getCinemaIdByShowAndMovie(movie.getId(), newDate);
                    for (Integer cinemaId : cinemasId){
                        cinemas.add(CinemaDAO.getInstance(MovieActivity.this).getById(cinemaId));
                    }
                    lvCinema = (ListView) findViewById(R.id.lvMovie);
                    adapter = new ShowByCinemaAdapter(MovieActivity.this, R.layout.layout_item_movie_cinema, cinemas, movie, newDate);
                    lvCinema.setAdapter(adapter);

                }
            });
            layoutParams.setMarginEnd(5);
            llDatetimePicker.addView(item, layoutParams);
        }
    }

    private void loadWidgets() {
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        ivPicture = (ImageView) this.findViewById(R.id.ivMovie);
        tvTitle = (TextView) this.findViewById(R.id.tvMovie);
        tvType = (TextView) this.findViewById(R.id.tvType);
        tvAge = (TextView) this.findViewById(R.id.tvAge);
        tvDuration = (TextView) this.findViewById(R.id.tvDuration);
        tvRating = (TextView) this.findViewById(R.id.tvRating);
        hsvCinema = (HorizontalScrollView) findViewById(R.id.hsvCinema);
        llDatetimePicker = (LinearLayout) findViewById(R.id.llDatetimePicker);
        txtDesc = findViewById(R.id.txtDesc);

        lvCinema = (ListView) this.findViewById(R.id.lvCinema);
        cinemas = new ArrayList<>();
        ArrayList<Integer> cinemasId = MovieDAO.getInstance(this).getCinemaIdByShowAndMovie(movie.getId(), new Date());
        for (Integer cinemaId : cinemasId){
            cinemas.add(CinemaDAO.getInstance(this).getById(cinemaId));
        }

        lvCinema = (ListView) findViewById(R.id.lvMovie);
        adapter = new ShowByCinemaAdapter(this, R.layout.layout_item_movie_cinema, cinemas, movie, new Date());
        lvCinema.setAdapter(adapter);
    }

    private void getData() {
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        assert b != null;
        movie = (Movie) b.getSerializable("movie");
    }
}