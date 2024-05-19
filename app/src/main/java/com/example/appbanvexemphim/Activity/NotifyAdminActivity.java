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
import com.example.appbanvexemphim.DAO.NotifyDAO;
import com.example.appbanvexemphim.DAO.ShowDAO;
import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.Notify;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.R;

import java.util.List;

public class NotifyAdminActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Notify> adapter;
    private NotifyDAO notifyDAO;
    private UserDAO userDAO;

    private LinearLayout layoutAddEditNotify;
    private EditText editTextTitle;
    private EditText editTextContent;
    private EditText editTextUserID;

    private Button buttonAddNotify;
    private Button buttonSaveNotify;
    private Button buttonDeleteNotify;
    private Button buttonCancel;
    private Button btncancel;
    private Notify selectedNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notify);
        loadWidget();
        addEvent();
    }
    private void loadWidget(){
        layoutAddEditNotify = findViewById(R.id.layoutAddEditNotify); // Layout thêm/cập nhật
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        editTextUserID = findViewById(R.id.editTextUserID);

        btncancel = findViewById(R.id.btncancel);
        buttonAddNotify = findViewById(R.id.buttonAddNotify); // Nút thêm rạp phim
        buttonSaveNotify = findViewById(R.id.buttonSaveNotify); // Nút lưu thay đổi
        buttonDeleteNotify = findViewById(R.id.buttonDeleteNotify); // Nút xóa rạp phim
        buttonCancel = findViewById(R.id.buttonCancel); // Nút hủy bỏ
    }
    private void addEvent()
    {
        listView = findViewById(R.id.listViewNotify);
        notifyDAO = new NotifyDAO(this);
        userDAO = new UserDAO(this);


        final List<Notify>[] notifyList = new List[]{notifyDAO.getAll()};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notifyList[0]);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNotify = notifyList[0].get(position); // Rạp phim được chọn
                layoutAddEditNotify.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                String username = getNamebyID(selectedNotify.getUser_id());
                // Điền thông tin rạp phim vào EditText
                editTextTitle.setText(selectedNotify.getTitle());
                editTextContent.setText(selectedNotify.getContent());
                editTextUserID.setText(username + " - " + String.valueOf(selectedNotify.getUser_id()));
            }
        });

        // Xử lý khi lưu rạp phim mới hoặc cập nhật rạp phim đã chọn
        buttonSaveNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                int userID = Integer.parseInt(editTextUserID.getText().toString());

                if (selectedNotify == null) {
                    // Thêm rạp phim mới
                    notifyDAO.insertNotify(title,content,userID);
                    Toast.makeText(getApplicationContext(), "Thông báo đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật rạp phim hiện có
                    selectedNotify.setTitle(title);
                    selectedNotify.setContent(content);
                    selectedNotify.setUser_id(userID);

                    notifyDAO.updateNotify(selectedNotify.getId(), title, content, userID);
                    Toast.makeText(getApplicationContext(), "Thông báo đã được thêm", Toast.LENGTH_SHORT).show();
                }

                // Làm mới danh sách rạp phim
                notifyList[0] = notifyDAO.getAll();
                adapter.clear();
                adapter.addAll(notifyList[0]);
                adapter.notifyDataSetChanged();

                layoutAddEditNotify.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedNotify = null; // Đặt lại rạp phim được chọn
            }
        });

        // Xử lý khi xóa rạp phim
        buttonDeleteNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedNotify != null) {
                    notifyDAO.deleteNotify(selectedNotify.getId()); // Xóa rạp phim
                    Toast.makeText(getApplicationContext(), "Thông báo đã được xóa", Toast.LENGTH_SHORT).show();

                    notifyList[0] = notifyDAO.getAll();
                    adapter.clear();
                    adapter.addAll(notifyList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditNotify.setVisibility(View.GONE); // Ẩn layout
                    selectedNotify = null; // Đặt lại rạp phim được chọn
                }
            }
        });

        // Hủy bỏ khi người dùng không muốn thêm/cập nhật
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditNotify.setVisibility(View.GONE); // Ẩn layout
                selectedNotify = null; // Đặt lại rạp phim được chọn
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditNotify.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextTitle.setText("");
                editTextContent.setText("");
                editTextUserID.setText("");

                selectedNotify = null; // Đảm bảo không có rạp phim được chọn
            }
        });
    }
    private String getNamebyID(int id){
        return userDAO.getNamebyID(id);
    }
}