package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private List<User> userList;
    private Context context;

    public UserAdapter(@NonNull Context context, @NonNull List<User> userList) {
        super(context, R.layout.layout_item_user, userList); // Bố cục cho từng mục trong ListView
        this.userList = userList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng LayoutInflater để tạo View cho mỗi mục trong ListView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_user, parent, false);
        }

        // Lấy dữ liệu người dùng tại vị trí hiện tại
        User user = userList.get(position);

        // Liên kết các thành phần trong bố cục
        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView userEmailTextView = convertView.findViewById(R.id.userEmailTextView);
        TextView userPhoneTextView = convertView.findViewById(R.id.userPhoneTextView);

        // Liên kết dữ liệu với giao diện
        userNameTextView.setText(user.getUsername());
        userEmailTextView.setText(user.getEmail());
        userPhoneTextView.setText(user.getPhone());

        return convertView; // Trả về View đã được liên kết dữ liệu
    }
}
