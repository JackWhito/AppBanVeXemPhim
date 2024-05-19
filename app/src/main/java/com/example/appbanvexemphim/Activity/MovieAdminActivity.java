package com.example.appbanvexemphim.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.R;

import java.util.List;

public class MovieAdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Movie> adapter;
    private MovieDAO movieDAO;

    private LinearLayout layoutAddEditMovie;
    private EditText editTextMovieName;
    private EditText editTextMovieDuration, editTextMovieReqAge, editTextMovieTypeID, editTextMovieDescription, editTextMovieRating, editTextMovieImage;

    private Button buttonAddMovie;
    private Button buttonSaveMovie;
    private Button buttonDeleteMovie;
    private Button buttonCancel;
    private Button btncancel;

    private Movie selectedMovie; // Rạp phim được chọn để cập nhật/xóa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movie);

        listView = findViewById(R.id.listViewMovies); // ListView hiển thị rạp phim
        movieDAO = new MovieDAO(this); // Khởi tạo CinemaDAO

        final List<Movie>[] movies = new List[]{movieDAO.getAllMovies()}; // Lấy danh sách rạp phim
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies[0]);
        listView.setAdapter(adapter); // Liên kết ListView với adapter

        layoutAddEditMovie = findViewById(R.id.layoutAddEditMovie); // Layout thêm/cập nhật
        editTextMovieName = findViewById(R.id.editTextMovieName);
        editTextMovieDuration = findViewById(R.id.editTextMovieDuration);
        editTextMovieImage = findViewById(R.id.editTextMovieImageResource);
        editTextMovieReqAge = findViewById(R.id.editTextMovieRequireAge);
        editTextMovieDescription = findViewById(R.id.editTextMovieDescription);
        editTextMovieRating = findViewById(R.id.editTextMovieRating);
        editTextMovieTypeID = findViewById(R.id.editTextMovieTypeId);

        btncancel = findViewById(R.id.btncancel);
        buttonAddMovie = findViewById(R.id.buttonAddMovie); // Nút thêm rạp phim
        buttonSaveMovie = findViewById(R.id.buttonSaveMovie); // Nút lưu thay đổi
        buttonDeleteMovie = findViewById(R.id.buttonDeleteMovie); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ

        // Xử lý khi nhấp vào một movie trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMovie = movies[0].get(position); // Rạp phim được chọn
                layoutAddEditMovie.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                // Điền thông tin rạp phim vào EditText
                editTextMovieName.setText(selectedMovie.getName());
                editTextMovieDuration.setText(String.valueOf(selectedMovie.getDuration()));
                editTextMovieDescription.setText(selectedMovie.getDescription());
                editTextMovieImage.setText(selectedMovie.getImage());
                editTextMovieReqAge.setText(selectedMovie.getRequireAge());
                editTextMovieTypeID.setText(String.valueOf(selectedMovie.getType_id()));
                editTextMovieRating.setText(String.valueOf(selectedMovie.getRating()));
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextMovieName.getText().toString();
                int duration = Integer.parseInt(editTextMovieDuration.getText().toString());
                String desc = editTextMovieDescription.getText().toString();
                String img = editTextMovieImage.getText().toString();
                String requireAge = editTextMovieReqAge.getText().toString();
                int typeID = Integer.parseInt(editTextMovieTypeID.getText().toString());
                float rating = Float.parseFloat(editTextMovieRating.getText().toString());

                if (selectedMovie == null) {
                    // Thêm rạp phim mới
                    movieDAO.insertMovie(name, duration,img,requireAge,typeID,desc,rating);
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedMovie.setName(name);
                    selectedMovie.setDuration(duration);
                    selectedMovie.setDescription(desc);
                    selectedMovie.setRequireAge(requireAge);
                    selectedMovie.setType_id(typeID);
                    selectedMovie.setRating(rating);
                    selectedMovie.setImage(img);

                    movieDAO.updateMovie(selectedMovie.getId(), name, duration,img,requireAge,typeID,desc,rating);
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                movies[0] = movieDAO.getAllMovies();
                adapter.clear();
                adapter.addAll(movies[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditMovie.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedMovie = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMovie != null) {
                    movieDAO.deleteMovie(selectedMovie.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được xóa", Toast.LENGTH_SHORT).show();

                    movies[0] = movieDAO.getAllMovies();
                    adapter.clear();
                    adapter.addAll(movies[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditMovie.setVisibility(View.GONE); // Ẩn layout
                    selectedMovie = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditMovie.setVisibility(View.GONE); // Ẩn layout
                selectedMovie = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditMovie.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextMovieName.setText("");
                editTextMovieDuration.setText("");
                editTextMovieDescription.setText("");
                editTextMovieReqAge.setText("");
                editTextMovieImage.setText("");
                editTextMovieRating.setText("");
                editTextMovieTypeID.setText("");
                selectedMovie = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
}
