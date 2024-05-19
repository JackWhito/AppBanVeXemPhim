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
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.R;

import java.util.List;

public class CinemaAdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Cinema> adapter;
    private CinemaDAO cinemaDAO;

    private LinearLayout layoutAddEditCinema;
    private EditText editTextCinemaName;
    private EditText editTextCinemaAddress;

    private Button buttonAddCinema;
    private Button buttonSaveCinema;
    private Button buttonDeleteCinema;
    private Button buttonCancel;
    private Button btncancel;

    private Cinema selectedCinema; // Rạp phim được chọn để cập nhật/xóa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cinema);

        listView = findViewById(R.id.listViewCinemas); // ListView hiển thị rạp phim
        cinemaDAO = new CinemaDAO(this); // Khởi tạo CinemaDAO

        final List<Cinema>[] cinemaList = new List[]{cinemaDAO.getAll()}; // Lấy danh sách rạp phim
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cinemaList[0]);
        listView.setAdapter(adapter); // Liên kết ListView với adapter

        layoutAddEditCinema = findViewById(R.id.layoutAddEditCinema); // Layout thêm/cập nhật
        editTextCinemaName = findViewById(R.id.editTextCinemaName);
        editTextCinemaAddress = findViewById(R.id.editTextCinemaAddress);

        btncancel = findViewById(R.id.btncancel);
        buttonAddCinema = findViewById(R.id.buttonAddCinema); // Nút thêm rạp phim
        buttonSaveCinema = findViewById(R.id.buttonSaveCinema); // Nút lưu thay đổi
        buttonDeleteCinema = findViewById(R.id.buttonDeleteCinema); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ

        // Xử lý khi nhấp vào một rạp phim trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCinema = cinemaList[0].get(position); // Rạp phim được chọn
                layoutAddEditCinema.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                // Điền thông tin rạp phim vào EditText
                editTextCinemaName.setText(selectedCinema.getName());
                editTextCinemaAddress.setText(selectedCinema.getAddress());
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextCinemaName.getText().toString();
                String address = editTextCinemaAddress.getText().toString();

                if (selectedCinema == null) {
                    // Thêm rạp phim mới
                    cinemaDAO.insertCinema(name, address);
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedCinema.setName(name);
                    selectedCinema.setAddress(address);

                    cinemaDAO.updateCinema(selectedCinema.getId(), name, address);
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                cinemaList[0] = cinemaDAO.getAll();
                adapter.clear();
                adapter.addAll(cinemaList[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditCinema.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedCinema = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCinema != null) {
                    cinemaDAO.deleteCinema(selectedCinema.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Rạp phim đã được xóa", Toast.LENGTH_SHORT).show();

                    cinemaList[0] = cinemaDAO.getAll();
                    adapter.clear();
                    adapter.addAll(cinemaList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditCinema.setVisibility(View.GONE); // Ẩn layout
                    selectedCinema = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditCinema.setVisibility(View.GONE); // Ẩn layout
                selectedCinema = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Xử lý khi thêm rạp phim mới
        buttonAddCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditCinema.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextCinemaName.setText("");
                editTextCinemaAddress.setText("");
                selectedCinema = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
}
