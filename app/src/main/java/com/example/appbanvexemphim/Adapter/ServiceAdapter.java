package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appbanvexemphim.Model.Service;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.List;


public class ServiceAdapter extends ArrayAdapter<Service> {
    private List<Service> serviceList;
    private Context context;

    public ServiceAdapter(@NonNull Context context, @NonNull List<Service> serviceList) {
        super(context, R.layout.layout_service_item, serviceList); // Bố cục mục tùy chỉnh
        this.serviceList = serviceList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng LayoutInflater để tạo View cho mỗi mục
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_service_item, parent, false);
        }

        // Lấy dữ liệu của dịch vụ tại vị trí hiện tại
        Service service = serviceList.get(position);

        // Liên kết các thành phần trong bố cục
        ImageView serviceImageView = convertView.findViewById(R.id.comboImageView);
        TextView serviceNameTextView = convertView.findViewById(R.id.comboNameTextView);
        TextView serviceDescriptionTextView = convertView.findViewById(R.id.comboDescriptionTextView);
        TextView servicePriceTextView = convertView.findViewById(R.id.comboPriceTextView);

        // Liên kết dữ liệu với giao diện
        serviceImageView.setImageBitmap(ImageConverter.getImage(service.getPicture(), context));
        serviceNameTextView.setText(service.getName());
        serviceDescriptionTextView.setText(service.getDescription());
        servicePriceTextView.setText(service.getPrice()+"");

        convertView.setTag(service.getId()+"");

        return convertView;
    }
}
