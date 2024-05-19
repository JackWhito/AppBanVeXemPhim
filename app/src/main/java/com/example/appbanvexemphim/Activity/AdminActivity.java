package com.example.appbanvexemphim.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.appbanvexemphim.R;

public class AdminActivity extends AppCompatActivity {

    private Button btnCinema;
    private Button btnMovie;
    private Button btnService;
    private Button btnShow;
    private Button btnHistory;
    private Button btnReview;
    private Button btnNotify;
    private Button btnUser;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        loadWidget();
        addEvent();
    }
    void loadWidget()
    {
        btnBack = findViewById(R.id.ibtnBackAdmin);
        btnCinema = findViewById(R.id.tvAdminCinema); // Nút "Rạp phim"
        btnMovie = findViewById(R.id.tvAdminMovie);// Nút " phim"
        btnService = findViewById(R.id.tvAdminService);// Nút "dịch vụ"
        btnShow = findViewById(R.id.tvAdminShow);
        btnHistory = findViewById(R.id.tvAdminHistory);
        btnReview = findViewById(R.id.tvAdminReview);
        btnNotify = findViewById(R.id.tvAdminNotify);
        btnUser = findViewById(R.id.tvAdminUser);
    }
    void addEvent(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, CinemaAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });

        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến MovieAdminActivity
                Intent intent = new Intent(AdminActivity.this, MovieAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến ServiceAdminActivity
                Intent intent = new Intent(AdminActivity.this, ServiceAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, ShowAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, ReviewAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, HistoryAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, NotifyAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến CinemaAdminActivity
                Intent intent = new Intent(AdminActivity.this, UserAdminActivity.class);
                startActivity(intent); // Bắt đầu hoạt động mới
            }
        });
    }
}