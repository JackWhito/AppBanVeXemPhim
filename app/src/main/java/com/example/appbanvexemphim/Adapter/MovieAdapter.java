package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context mContext;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            listItemView = inflater.inflate( R.layout.layout_item_movie,null);
        }
        Movie currentMovie = getItem(position);
        ImageView movieImage = listItemView.findViewById(R.id.movieImage);
        TextView movieName = listItemView.findViewById(R.id.txtNameMoive);

        if (currentMovie != null) {
            try {
                String imagePath = "images/" + currentMovie.getImage();
                InputStream inputStream = mContext.getAssets().open(imagePath);

                Drawable drawable = Drawable.createFromStream(inputStream, null);

                movieImage.setImageDrawable(drawable);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            movieName.setText(currentMovie.getName());
        }
        return listItemView;
    }
}


