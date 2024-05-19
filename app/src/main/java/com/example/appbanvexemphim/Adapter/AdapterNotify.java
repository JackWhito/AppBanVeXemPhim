package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.Model.Notify;
import com.example.appbanvexemphim.R;

import java.util.ArrayList;

public class AdapterNotify extends ArrayAdapter<Notify> {
    private Context context;
    private int layout;
    private ArrayList<Notify> notifies;
    public AdapterNotify(@NonNull Context context, int resource, ArrayList<Notify> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.notifies = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }
        TextView tvNotifyItemTitle = (TextView) convertView.findViewById(R.id.tvNotifyItemTitle);
        TextView tvNotifyItemContent = (TextView) convertView.findViewById(R.id.tvNotifyItemContent);
        Notify notify = notifies.get(position);

        tvNotifyItemTitle.setText(notify.getTitle());
        tvNotifyItemContent.setText(notify.getContent());

        return convertView;
    }
}
