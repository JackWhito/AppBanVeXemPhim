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

import com.example.appbanvexemphim.DAO.BillDAO;
import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;

import java.util.List;

public class HistoryAdminActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Bill> adapter;
    private BillDAO billDAO;

    private LinearLayout layoutAddEditHistory;
    private EditText editTextUserID;
    private EditText editTextCreateDate;
    private EditText editTextPrice;
    private EditText editTextState;
    private EditText editTextPayment;
    private EditText editTextService;
    private Button buttonAddHistory;
    private Button buttonSaveHistory;
    private Button buttonDeleteHistory;
    private Button buttonCancel;
    private Button btncancel;
    private Bill selectedHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);
        loadWidget();
        addEvent();
    }
    private void loadWidget(){
        layoutAddEditHistory = findViewById(R.id.layoutAddEditHistory); // Layout thêm/cập nhật
        editTextUserID = findViewById(R.id.editTextHistoryUserID);
        editTextCreateDate = findViewById(R.id.editTextCreateDate);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextState = findViewById(R.id.editTextState);
        editTextPayment = findViewById(R.id.editTextPayment);
        editTextService = findViewById(R.id.editTextServiceID);

        btncancel = findViewById(R.id.btncancel);
        buttonAddHistory = findViewById(R.id.buttonAddHistory); // Nút thêm rạp phim
        buttonSaveHistory = findViewById(R.id.buttonSaveHistory); // Nút lưu thay đổi
        buttonDeleteHistory= findViewById(R.id.buttonDeleteHistory); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ
    }
    private void addEvent()
    {
        listView = findViewById(R.id.listViewHistory);
        billDAO = new BillDAO(this);

        final List<Bill>[] billList = new List[]{billDAO.getAll()};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billList[0]);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedHistory = billList[0].get(position); // Rạp phim được chọn
                layoutAddEditHistory.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                editTextUserID.setText(String.valueOf(selectedHistory.getUser_id()));
                editTextCreateDate.setText(selectedHistory.getCreate_date());
                editTextPrice.setText(String.valueOf(selectedHistory.getPrice()));
                editTextState.setText(String.valueOf(selectedHistory.getState()));
                editTextPayment.setText(String.valueOf(selectedHistory.getPayment()));
                editTextService.setText(String.valueOf(selectedHistory.getService_id()));
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userID = Integer.parseInt(editTextUserID.getText().toString());
                String createdate = editTextCreateDate.getText().toString();
                float price = Float.parseFloat(editTextPrice.getText().toString());
                int state = Integer.parseInt(editTextState.getText().toString());
                int payment = Integer.parseInt(editTextPayment.getText().toString());
                int serviceID = Integer.parseInt(editTextService.getText().toString());

                if (selectedHistory == null) {
                    // Thêm rạp phim mới
                    billDAO.insertBill(userID,createdate,price,state,payment,serviceID);
                    Toast.makeText(getApplicationContext(), "Lịch sử đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedHistory.setUser_id(userID);
                    selectedHistory.setCreate_date(createdate);
                    selectedHistory.setPrice(price);
                    selectedHistory.setState(state);
                    selectedHistory.setPayment(payment);
                    selectedHistory.setService_id(serviceID);

                    billDAO.update(selectedHistory);
                    Toast.makeText(getApplicationContext(), "Lịch sử đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                billList[0] = billDAO.getAll();
                adapter.clear();
                adapter.addAll(billList[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditHistory.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedHistory = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedHistory != null) {
                    billDAO.deleteBill(selectedHistory.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Lịch sử đã được xóa", Toast.LENGTH_SHORT).show();

                    billList[0] = billDAO.getAll();
                    adapter.clear();
                    adapter.addAll(billList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditHistory.setVisibility(View.GONE); // Ẩn layout
                    selectedHistory = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditHistory.setVisibility(View.GONE); // Ẩn layout
                selectedHistory = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditHistory.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextUserID.setText("");
                editTextCreateDate.setText("");
                editTextPrice.setText("");
                editTextState.setText("");
                editTextPayment.setText("");
                editTextService.setText("");

                selectedHistory = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
}