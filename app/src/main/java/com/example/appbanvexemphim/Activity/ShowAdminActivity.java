package com.example.appbanvexemphim.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;

import java.util.List;

public class ShowAdminActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Show> adapter;
    private ShowDAO showDAO;
    private MovieDAO movieDAO;
    private CinemaDAO cinemaDAO;

    private LinearLayout layoutAddEditShow;
    private EditText editTextMovieID;
    private EditText editTextTheaterID;
    private EditText editTextDate;

    private Button buttonAddShow;
    private Button buttonSaveShow;
    private Button buttonDeleteShow;
    private Button buttonCancel;
    private Button btncancel;
    private Show selectedShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show);
        loadWidget();
        addEvent();
    }
    private void loadWidget(){
        layoutAddEditShow = findViewById(R.id.layoutAddEditShow); // Layout thêm/cập nhật
        editTextMovieID = findViewById(R.id.editTextMovieID);
        editTextTheaterID = findViewById(R.id.editTextTheaterID);
        editTextDate = findViewById(R.id.editTextDate);

        btncancel = findViewById(R.id.btncancel);
        buttonAddShow = findViewById(R.id.buttonAddShow); // Nút thêm rạp phim
        buttonSaveShow = findViewById(R.id.buttonSaveShow); // Nút lưu thay đổi
        buttonDeleteShow = findViewById(R.id.buttonDeleteShow); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ
    }
    private void addEvent()
    {
        listView = findViewById(R.id.listViewShows);
        showDAO = new ShowDAO(this);
        movieDAO = new MovieDAO(this);
        cinemaDAO = new CinemaDAO(this);

        final List<Show>[] showList = new List[]{showDAO.getAll()};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showList[0]);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedShow = showList[0].get(position); // Rạp phim được chọn
                layoutAddEditShow.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                String movieName = getMovieNamebyID(selectedShow.getMovie_id());
                String theaterName = getTheaterbyID(selectedShow.getThreater_id());
                // Điền thông tin rạp phim vào EditText
                editTextMovieID.setText(movieName + " - " + String.valueOf(selectedShow.getMovie_id()));
                editTextTheaterID.setText(theaterName + " - " + String.valueOf(selectedShow.getThreater_id()));
                editTextDate.setText(selectedShow.getDatetime());
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movieID = Integer.parseInt(editTextMovieID.getText().toString());
                int theaterID = Integer.parseInt(editTextTheaterID.getText().toString());
                String date = editTextDate.getText().toString();

                if (selectedShow == null) {
                    // Thêm rạp phim mới
                    showDAO.insertShow(movieID,theaterID,date);
                    Toast.makeText(getApplicationContext(), "Xuất chiếu đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedShow.setMovie_id(movieID);
                    selectedShow.setThreater_id(theaterID);
                    selectedShow.setDatetime(date);

                    showDAO.updateShow(selectedShow.getId(), movieID, theaterID, date);
                    Toast.makeText(getApplicationContext(), "Xuất chiếu đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                showList[0] = showDAO.getAll();
                adapter.clear();
                adapter.addAll(showList[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditShow.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedShow = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedShow != null) {
                    showDAO.deleteShow(selectedShow.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Xuất chiếu đã được xóa", Toast.LENGTH_SHORT).show();

                    showList[0] = showDAO.getAll();
                    adapter.clear();
                    adapter.addAll(showList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditShow.setVisibility(View.GONE); // Ẩn layout
                    selectedShow = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditShow.setVisibility(View.GONE); // Ẩn layout
                selectedShow = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditShow.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextMovieID.setText("");
                editTextTheaterID.setText("");
                editTextDate.setText("");

                selectedShow = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
    private String getMovieNamebyID(int id){
        return movieDAO.getNamebyID(id);
    }
    private String getTheaterbyID(int id){
        return cinemaDAO.getNamebyId(id);
    }
}