package com.example.appbanvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.AdapterHistory;
import com.example.appbanvexemphim.DAO.BillDAO;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ImageButton ibtnBackHistory;
    private ListView lvHistory;
    private ArrayList<Bill> bills;
    private AdapterHistory adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        loadWidgets();
        addEvents();
    }

    private void addEvents() {
        ibtnBackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(HistoryActivity.this, TicketActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Bill", bills.get(position));
                i.putExtra("Data", b);
                startActivityForResult(i, MainActivity.MO_ACTIVITY_TICKET);
            }
        });
    }

    private void loadWidgets() {
        ibtnBackHistory = (ImageButton) findViewById(R.id.ibtnBackHistory);
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        bills = BillDAO.getInstance(this).getAllByUser_id(new String[]{String.valueOf(Constant.user.getId())});
        adapter = new AdapterHistory(HistoryActivity.this, R.layout.layout_item_history, bills);
        lvHistory.setAdapter(adapter);
    }
}