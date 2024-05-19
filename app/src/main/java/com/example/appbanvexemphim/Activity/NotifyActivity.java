package com.example.appbanvexemphim.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.AdapterNotify;
import com.example.appbanvexemphim.DAO.NotifyDAO;
import com.example.appbanvexemphim.Model.Notify;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

import java.util.ArrayList;

public class NotifyActivity extends AppCompatActivity {
    private ImageButton ibtnBackNotify;
    private ListView lvNotify;
    private ArrayList<Notify> notifies;
    private AdapterNotify adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        loadWidgets();
        addEvents();
    }

    private void addEvents() {
        ibtnBackNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadWidgets() {
        ibtnBackNotify = (ImageButton) findViewById(R.id.ibtnBackNotify);
        lvNotify = (ListView) findViewById(R.id.lvNotify);
        notifies = NotifyDAO.getInstance(this).getByUser(Constant.user.getId());
        adapter = new AdapterNotify(NotifyActivity.this, R.layout.layout_item_notify, notifies);
        lvNotify.setAdapter(adapter);
    }
}