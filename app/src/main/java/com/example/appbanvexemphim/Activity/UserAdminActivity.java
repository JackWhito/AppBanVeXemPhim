package com.example.appbanvexemphim.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.UserAdapter;
import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.R;

import java.util.List;

public class UserAdminActivity extends AppCompatActivity {

    private ListView listView; // ListView chứa danh sách tài khoản
    private UserAdapter adapter; // Adapter cho ListView
    private UserDAO UserDAO;

    private LinearLayout layoutAddEditUser; // Layout thêm/cập nhật tài khoản
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPassword;

    private Button buttonSaveUser;
    private Button buttonDeleteUser;
    private Button buttonAddUser;
    private Button buttonCancelUser;
    private Button btnCancel;

    private User selectedUser; // Tài khoản được chọn để cập nhật hoặc xóa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user); // Bố cục chứa ListView và layout thêm/cập nhật

        listView = findViewById(R.id.listViewUsers); // ListView chứa tài khoản
        layoutAddEditUser = findViewById(R.id.layoutAddEditUser); // Layout thêm/cập nhật

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSaveUser = findViewById(R.id.buttonSaveUser);
        buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonCancelUser = findViewById(R.id.buttonCancelUser);
        btnCancel = findViewById(R.id.btncancel);

        UserDAO = new UserDAO(this); // Tạo đối tượng DAO

        final List<User>[] UserList = new List[]{UserDAO.getAll()}; // Lấy danh sách tài khoản
        adapter = new UserAdapter(this, UserList[0]); // Tạo adapter
        listView.setAdapter(adapter); // Liên kết ListView với adapter

        // Khi chọn một tài khoản trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = UserList[0].get(position); // Tài khoản được chọn
                layoutAddEditUser.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                // Điền dữ liệu vào các trường
                editTextUsername.setText(selectedUser.getUsername());
                editTextEmail.setText(selectedUser.getEmail());
                editTextPhone.setText(selectedUser.getPhone());
                editTextPassword.setText(selectedUser.getPassword());
            }
        });


        // Khi nhấp "Lưu"
        buttonSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();

                if (selectedUser == null) {
                    // Thêm tài khoản mới
                    UserDAO.insertUser(username, email, phone, password);
                    Toast.makeText(getApplicationContext(), "Tài khoản đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật tài khoản hiện có
                    UserDAO.updateUser(
                            selectedUser.getId(),
                            username,
                            email,
                            phone,
                            password
                    );
                    Toast.makeText(getApplicationContext(), "Tài khoản đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Cập nhật danh sách tài khoản
                UserList[0] = UserDAO.getAll();
                adapter.clear();
                adapter.addAll(UserList[0]); // Làm mới adapter
                adapter.notifyDataSetChanged(); // Cập nhật ListView

                layoutAddEditUser.setVisibility(View.GONE); // Ẩn layout sau khi lưu
                selectedUser = null; // Đặt lại tài khoản được chọn
            }
        });

        // Khi nhấp "Xóa"
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser != null) {
                    UserDAO.deleteUser(selectedUser.getId()); // Xóa tài khoản
                    Toast.makeText(getApplicationContext(), "Tài khoản đã được xóa", Toast.LENGTH_SHORT).show();

                    // Cập nhật danh sách tài khoản
                    UserList[0] = UserDAO.getAll();
                    adapter.clear();
                    adapter.addAll(UserList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo cho ListView

                    layoutAddEditUser.setVisibility(View.GONE); // Ẩn layout
                    selectedUser = null; // Đặt lại tài khoản được chọn
                }
            }
        });

        // Khi nhấp "Hủy"
        buttonCancelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditUser.setVisibility(View.GONE); // Ẩn layout
                selectedUser = null; // Đặt lại tài khoản được chọn
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khi nhấp "Thêm Tài khoản"
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditUser.setVisibility(View.VISIBLE); // Hiển thị layout
                editTextUsername.setText(""); // Xóa các trường
                editTextEmail.setText("");
                editTextPhone.setText("");
                editTextPassword.setText("");
                selectedUser = null; // Đảm bảo không có tài khoản nào được chọn
            }
        });
    }
}
