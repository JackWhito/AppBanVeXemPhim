package com.example.appbanvexemphim.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.PreLogin;
import com.example.appbanvexemphim.R;

import java.io.IOException;
import java.io.InputStream;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView  productNameTextView,txtContent;

    private ImageView productImageView;

    private String movieName;
    private String movieImage;
    private Button btnXemRap,btnSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getWidget();
        addEvent();
    }
    private void getWidget(){
        Intent intent = getIntent();
        movieName = intent.getStringExtra("movie_name");
        movieImage = intent.getStringExtra("movie_image_resource");
        String decription=intent.getStringExtra("movie_decription");

        productNameTextView= findViewById(R.id.product_name_text_view);
        productImageView = findViewById(R.id.product_image_view);
        btnXemRap=findViewById(R.id.btnXemRap);
        btnSeat=findViewById(R.id.btnSeat);
        productNameTextView.setText(movieName);

        txtContent= findViewById(R.id.txtContent);
        txtContent.setText(decription);
        try {
            String imagePath = "images/" + movieImage;
            InputStream inputStream = getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            productImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addEvent(){
        btnXemRap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MovieDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", "tabCinema");
                startActivity(intent);
            }
        });
        btnSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, SeatActivity.class);
                startActivity(intent);
            }
        });
    }


}