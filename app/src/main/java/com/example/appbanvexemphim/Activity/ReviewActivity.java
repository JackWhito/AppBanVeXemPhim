package com.example.appbanvexemphim.Activity;

import static java.io.File.createTempFile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {
    private ImageButton ibtnBack, ibtnShare;
    private ImageView ivReviewImage;
    private TextView tvReviewTitle, tvReviewAuthor, tvReviewContent;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
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
        //TODO: share review lên mạng xã hội
        ibtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT, tvReviewTitle.getText().toString() + "\n"
                        + tvReviewAuthor.getText().toString() + "\n"
                        + tvReviewContent.getText().toString());

                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void loadData() {
        try {
            Intent i = getIntent();
            Bundle b = i.getBundleExtra("Data");

            assert b != null;
            review = (Review) b.getSerializable("Review");

            InputStream inputStream = null;
            try {
                assert review != null;
                inputStream = this.getAssets().open("images/" + review.getPicture());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Đọc dữ liệu hình ảnh từ InputStream và tạo đối tượng Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ivReviewImage.setImageBitmap(bitmap);
            tvReviewTitle.setText(review.getTitle());
            //TODO: khi làm xong UserService thì lấy tên author ra.
            tvReviewAuthor.setText("Tác giả: " + review.getUser_id());
            tvReviewContent.setText(review.getContent());
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void loadWidgets() {
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        ibtnShare = (ImageButton) this.findViewById(R.id.ibtnShare);
        ivReviewImage = (ImageView) findViewById(R.id.ivReviewImage);
        tvReviewTitle = (TextView) findViewById(R.id.tvReviewTitle);
        tvReviewAuthor = (TextView) findViewById(R.id.tvReviewAuthor);
        tvReviewContent = (TextView) findViewById(R.id.tvReviewContent);

    }
}