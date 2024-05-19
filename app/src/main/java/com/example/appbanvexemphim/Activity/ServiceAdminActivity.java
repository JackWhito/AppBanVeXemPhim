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

import com.example.appbanvexemphim.Adapter.ServiceAdapter;
import com.example.appbanvexemphim.DAO.ServiceDAO;
import com.example.appbanvexemphim.Model.Service;
import com.example.appbanvexemphim.R;

import java.util.List;

public class ServiceAdminActivity extends AppCompatActivity {

    private ListView listView;
    private ServiceAdapter adapter;
    private ServiceDAO serviceDAO;

    private LinearLayout layoutAddEditService; // Layout thêm/cập nhật
    private EditText editTextServiceName;
    private EditText editTextServiceDescription;
    private EditText editTextServicePrice;
    private EditText editTextServiceImage;

    private Button buttonSaveService;
    private Button buttonDeleteService;
    private Button buttonAddService;
    private Button buttonCancelService;
    private Button btnCancel;

    private Service selectedService; // Dịch vụ được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service); // Bố cục chứa ListView và layout thêm/cập nhật

        listView = findViewById(R.id.listViewServices); // ListView chứa các dịch vụ
        layoutAddEditService = findViewById(R.id.layoutAddEditService); // Layout thêm/cập nhật

        editTextServiceName = findViewById(R.id.editTextServiceName);
        editTextServiceDescription = findViewById(R.id.editTextServiceDescription);
        editTextServicePrice = findViewById(R.id.editTextServicePrice);
        editTextServiceImage = findViewById(R.id.editTextServiceImage);

        buttonSaveService = findViewById(R.id.buttonSaveService);
        buttonDeleteService = findViewById(R.id.buttonDeleteService);
        buttonAddService = findViewById(R.id.buttonAddService);
        buttonCancelService = findViewById(R.id.buttonCancelService);
        btnCancel = findViewById(R.id.btncancel);

        serviceDAO = new ServiceDAO(this);
        final List<Service>[] serviceList = new List[]{ServiceDAO.getInstance(this).getAllServices()}; // Lấy danh sách dịch vụ
        adapter = new ServiceAdapter(this, serviceList[0]); // Tạo adapter
        listView.setAdapter(adapter); // Liên kết ListView với adapter

        // Xử lý khi chọn một dịch vụ trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedService = serviceList[0].get(position); // Dịch vụ được chọn
                layoutAddEditService.setVisibility(View.VISIBLE); // Hiển thị layout thêm/cập nhật

                // Điền dữ liệu của dịch vụ vào các trường
                editTextServiceName.setText(selectedService.getName());
                editTextServiceDescription.setText(selectedService.getDescription());
                editTextServicePrice.setText(String.valueOf(selectedService.getPrice()));
                editTextServiceImage.setText(String.valueOf(selectedService.getPicture()));
            }
        });

        // Xử lý khi lưu dịch vụ (thêm mới hoặc cập nhật)
        buttonSaveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextServiceName.getText().toString();
                String description = editTextServiceDescription.getText().toString();
                int price = Integer.parseInt(editTextServicePrice.getText().toString());
                String imageResource = editTextServiceImage.getText().toString();

                if (selectedService == null) {
                    // Thêm dịch vụ mới
                    serviceDAO.insertService(name, description, price, imageResource);
                    Toast.makeText(getApplicationContext(), "Dịch vụ đã được thêm", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật dịch vụ hiện có
                    serviceDAO.updateService(
                            selectedService.getId(),
                            name,
                            description,
                            price,
                            imageResource
                    );
                    Toast.makeText(getApplicationContext(), "Dịch vụ đã được cập nhật", Toast.LENGTH_SHORT).show();
                }

                // Cập nhật danh sách dịch vụ
                serviceList[0] = serviceDAO.getAllServices();
                adapter.clear();
                adapter.addAll(serviceList[0]); // Làm mới adapter
                adapter.notifyDataSetChanged(); // Cập nhật ListView

                layoutAddEditService.setVisibility(View.GONE); // Ẩn layout
                selectedService = null; // Đặt lại dịch vụ được chọn
            }
        });

        // Xử lý khi xóa dịch vụ
        buttonDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedService != null) {
                    serviceDAO.deleteService(selectedService.getId()); // Xóa dịch vụ
                    Toast.makeText(getApplicationContext(), "Dịch vụ đã được xóa", Toast.LENGTH_SHORT).show();

                    // Cập nhật danh sách dịch vụ
                    serviceList[0] = serviceDAO.getAllServices();
                    adapter.clear();
                    adapter.addAll(serviceList[0]); // Làm mới adapter
                    adapter.notifyDataSetChanged(); // Thông báo thay đổi cho ListView

                    layoutAddEditService.setVisibility(View.GONE); // Ẩn layout
                    selectedService = null; // Đặt lại dịch vụ được chọn
                }
            }
        });

        // Khi nhấp nút Hủy, ẩn layout thêm/cập nhật
        buttonCancelService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditService.setVisibility(View.GONE); // Ẩn layout
                selectedService = null; // Đặt lại dịch vụ được chọn
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khi nhấp "Thêm Dịch vụ mới"
        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddEditService.setVisibility(View.VISIBLE); // Hiển thị layout thêm dịch vụ
                editTextServiceName.setText(""); // Xóa các trường dữ liệu
                editTextServiceDescription.setText("");
                editTextServicePrice.setText("");
                editTextServiceImage.setText("");
                selectedService = null; // Đảm bảo không có dịch vụ nào được chọn
            }
        });
    }
}
