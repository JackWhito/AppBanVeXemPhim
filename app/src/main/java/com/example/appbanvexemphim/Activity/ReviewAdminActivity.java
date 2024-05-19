package com.example.appbanvexemphim.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.NotifyDAO;
import com.example.appbanvexemphim.DAO.ReviewDAO;
import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.Notify;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.R;

import java.util.List;

public class ReviewAdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Review> adapter;
    private ReviewDAO reviewDAO;
    private UserDAO userDAO;
    private MovieDAO movieDAO;
    private LinearLayout layoutAddEditReview;
    private EditText editTextReviewUserID;
    private EditText editTextReviewMovieID;
    private EditText editTextReviewTitle;
    private EditText editTextReviewContent;
    private EditText editTextReviewImg;

    private Button buttonAddReview;
    private Button buttonSaveReview;
    private Button buttonDeleteReview;
    private Button buttonCancel;
    private Button btncancel;
    private Review selectedReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review);
        loadWidget();
        addEvent();
    }
    private void loadWidget(){
        layoutAddEditReview = findViewById(R.id.layoutAddEditReview); // Layout thêm/cập nhật
        editTextReviewUserID = findViewById(R.id.editTextReviewUserID);
        editTextReviewMovieID = findViewById(R.id.editTextReviewMovieID);
        editTextReviewTitle = findViewById(R.id.editTextReviewTitle);
        editTextReviewContent = findViewById(R.id.editTextReviewContent);
        editTextReviewImg = findViewById(R.id.editTextReviewPicture);

        btncancel = findViewById(R.id.btncancel);
        buttonAddReview = findViewById(R.id.buttonAddReview); // Nút thêm rạp phim
        buttonSaveReview = findViewById(R.id.buttonSaveReview); // Nút lưu thay đổi
        buttonDeleteReview = findViewById(R.id.buttonDeleteReview); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ
        listView = findViewById(R.id.listViewReview);
    }
    private void addEvent()
    {

        reviewDAO = new ReviewDAO(this);
        userDAO = new UserDAO(this);
        movieDAO = new MovieDAO(this);


        final List<Review>[] reviewList = new List[]{reviewDAO.getAll()};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviewList[0]);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedReview = reviewList[0].get(position); // Rạp phim được chọn
                layoutAddEditReview.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                String username = getNamebyID(selectedReview.getUser_id());
                String movie = getMoviebyID(selectedReview.getMovie_id());
                // Điền thông tin rạp phim vào EditText
                editTextReviewUserID.setText(username + " - " + String.valueOf(selectedReview.getUser_id()));
                editTextReviewMovieID.setText(movie + " - " + String.valueOf(selectedReview.getMovie_id()));
                editTextReviewTitle.setText(selectedReview.getTitle());
                editTextReviewContent.setText(selectedReview.getContent());
                editTextReviewImg.setText(selectedReview.getPicture());
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userID = Integer.parseInt(editTextReviewUserID.getText().toString());
                int movieID = Integer.parseInt(editTextReviewMovieID.getText().toString());
                String title = editTextReviewTitle.getText().toString();
                String content = editTextReviewContent.getText().toString();
                String img = editTextReviewImg.getText().toString();

                if (selectedReview == null) {
                    // Thêm rạp phim mới
                    reviewDAO.insertReview(userID,movieID,title,content,img);
                    Toast.makeText(getApplicationContext(), "Đánh giá đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedReview.setTitle(title);
                    selectedReview.setContent(content);
                    selectedReview.setUser_id(userID);
                    selectedReview.setMovie_id(movieID);
                    selectedReview.setPicture(img);

                    reviewDAO.updateReview(selectedReview.getId(),userID, movieID, title, content, img);
                    Toast.makeText(getApplicationContext(), "Đánh giá đã được thêm", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                reviewList[0] = reviewDAO.getAll();
                adapter.clear();
                adapter.addAll(reviewList[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditReview.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedReview = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedReview != null) {
                    reviewDAO.deleteReview(selectedReview.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Thông báo đã được xóa", Toast.LENGTH_SHORT).show();

                    reviewList[0] = reviewDAO.getAll();
                    adapter.clear();
                    adapter.addAll(reviewList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditReview.setVisibility(View.GONE); // Ẩn layout
                    selectedReview = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditReview.setVisibility(View.GONE); // Ẩn layout
                selectedReview = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditReview.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextReviewTitle.setText("");
                editTextReviewContent.setText("");
                editTextReviewImg.setText("");
                editTextReviewUserID.setText("");
                editTextReviewMovieID.setText("");

                selectedReview = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
    private String getNamebyID(int id){
        return userDAO.getNamebyID(id);
    }
    private String getMoviebyID(int id){
        return movieDAO.getNamebyID(id);
    }
}
