package com.example.appbanvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.ServiceAdapter;

import com.example.appbanvexemphim.DAO.ServiceDAO; // DAO để lấy dữ liệu từ cơ sở dữ liệu
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Service;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ServiceActivity extends AppCompatActivity {
    private Cinema cinema;
    private Show show;
    private Movie movie;
    private ArrayList<Ticket> tickets;

    private ImageButton ibtnBack;
    private Button btnContinue;
    private TextView tvSeatSelected, tvTotal;
    private ListView lvService; // ListView để hiển thị danh sách dịch vụ
    private ServiceAdapter adapter; // Adapter cho ListView
    private ArrayList<Service> services; // Danh sách các dịch vụ
    private Service serviceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_service);
        getData();
        loadWidgets();
        loadData();
        addEvents();
    }

    private void addEvents() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceActivity.this, BillActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("movie", movie);
                b.putSerializable("cinema", cinema);
                b.putSerializable("show", show);
                b.putSerializable("tickets", tickets);
                b.putSerializable("service", serviceSelected);
                i.putExtra("Data", b);
                startActivity(i);
            }
        });
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (serviceSelected == services.get(position)){
                    view.setSelected(false);
                    serviceSelected = null;
                }
                else{
                    for (int i =0;i<services.size();i++){
                        parent.getChildAt(i).setSelected(false);
                    }
                    view.setSelected(true);
                    serviceSelected = services.get(position);
                }
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                tvTotal.setText(format.format(Constant.calculateTotal(tickets, serviceSelected)));
                tvSeatSelected.setText(Constant.calculateSeatSelected(tickets));
            }
        });
    }

    private void loadData() {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTotal.setText(format.format(Constant.calculateTotal(tickets, serviceSelected)));
        tvSeatSelected.setText(Constant.calculateSeatSelected(tickets));
    }

    private void loadWidgets() {;
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        btnContinue = (Button) this.findViewById(R.id.btnContinue);
        tvSeatSelected = (TextView) this.findViewById(R.id.tvSeatSelected);
        tvTotal = (TextView) this.findViewById(R.id.tvTotal);

        lvService = (ListView) this.findViewById(R.id.lvService);
        services = (ArrayList<Service>) ServiceDAO.getInstance(this).getAllServices();
        adapter = new ServiceAdapter(this, services);
        lvService.setAdapter(adapter);
    }

    private void getData() {
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        cinema = (Cinema) b.getSerializable("cinema");
        movie = (Movie) b.getSerializable("movie");
        show = (Show) b.getSerializable("show");
        tickets = (ArrayList<Ticket>) b.getSerializable("tickets");
    }
}
