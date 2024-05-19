package com.example.appbanvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.DatChoAdapter;
import com.example.appbanvexemphim.DAO.SeatDAO;
import com.example.appbanvexemphim.Model.Seat;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.R;

import java.util.ArrayList;

public class SeatActivity extends AppCompatActivity {

    Button button_book_tickets;
    TextView txtTong;
    DatChoAdapter datChoAdapter;

    GridView grid_view_seats;
    TextView txtDate, txtShowtime, txtNameMoive;
    private int total = 0;
    private static ArrayList<Seat> listSeated = null;
    private ArrayList<String> selectedCheckBoxes = new ArrayList<>();
    int threater_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        button_book_tickets=findViewById(R.id.button_book_tickets);
        txtTong = findViewById(R.id.txtTong);
        grid_view_seats = findViewById(R.id.grid_view_seats);
//            // Lấy dữ liệu từ Intent
//            Intent intent = getIntent();
//            String selectedShowTime = intent.getStringExtra("showtime");
//            txtShowtime = findViewById(R.id.txtshowTime);
//            txtShowtime.setText("Giờ:" + selectedShowTime);
//
//            String imageName = intent.getStringExtra("imageName");
//            txtNameMoive = findViewById(R.id.txtName);
//            txtNameMoive.setText("Phim: " + imageName);
//            Calendar calendar = (Calendar) intent.getSerializableExtra("dateshow");
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            String showDate = dateFormat.format(calendar.getTime());
//            txtDate = findViewById(R.id.txtDate);
//            txtDate.setText("Ngày: " + showDate);

        threater_id = 1;
        SeatDAO seatDAO = new SeatDAO(this);
        listSeated = (ArrayList<Seat>) seatDAO.getListSeated(threater_id);

        // Tạo hàng ghế
        ArrayList<String> items = new ArrayList<>();
        char kt = 'A';
        for (int i = 0; i <= 35; i++) {
            if (i % 6 == 0) {
                items.add(String.valueOf(kt));
                kt++;
                grid_view_seats.setSelector(android.R.color.transparent);
            } else {
                items.add("");
            }
            datChoAdapter = new DatChoAdapter(this, items, listSeated);

            grid_view_seats.setAdapter(datChoAdapter);
            datChoAdapter.setOnCheckedChangeListener(new DatChoAdapter.OnCheckedChangeListener() {

                public void onCheckedChanged(boolean isChecked, String nameSeat) {
                    // Nếu CheckBox được chọn, tăng giá trị tổng lên 100, ngược lại giảm đi 100
                    if (isChecked) {
                        total += 100;
                        selectedCheckBoxes.add(nameSeat);
                    } else {
                        total -= 100;
                        selectedCheckBoxes.add(nameSeat);
                    }
                    // Cập nhật giá trị TextView "Tổng"
                    txtTong.setText("Tổng: " + total + ".000VND");
                }
            });
        }
        button_book_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeatActivity.this, ServiceActivity.class);
                intent.putStringArrayListExtra("selectedCheckBoxes", selectedCheckBoxes);
                intent.putExtra("threater",threater_id);
                intent.putExtra("total",total);
                startActivity(intent);
            }
        });

    }



}
