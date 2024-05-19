package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;

public class AdapterReview extends ArrayAdapter<Review> {
    private Context context;
    private int layout;
    private ArrayList<Review> reviews;
    public AdapterReview(@NonNull Context context, int resource, ArrayList<Review> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.reviews = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
        }
        ImageView ivReviewItemImage = (ImageView) convertView.findViewById(R.id.ivReviewItemImage);
        TextView tvReviewItemName = (TextView) convertView.findViewById(R.id.tvReviewItemName);
        TextView tvReviewItemMore = (TextView) convertView.findViewById(R.id.tvReviewItemMore);

        Review review = reviews.get(position);


        ivReviewItemImage.setImageBitmap(ImageConverter.getImage(review.getPicture(), context));
        tvReviewItemName.setText(review.getTitle());

        return convertView;
    }
}
