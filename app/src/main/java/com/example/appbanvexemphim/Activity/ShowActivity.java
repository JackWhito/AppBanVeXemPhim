package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanvexemphim.Adapter.SeatAdapter;
import com.example.appbanvexemphim.DAO.MovieTypeDAO;
import com.example.appbanvexemphim.DAO.SeatDAO;
import com.example.appbanvexemphim.DAO.TicketDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Seat;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.R;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    private Cinema cinema;
    private ArrayList<Show> shows;
    private Show curShow;
    private Movie movie;
    private ImageButton ibtnBack;
    private Button btnContinue;
    private TextView tvCinema, tvMovieName, tvTypeAndAge, tvTotal,tvSeatSelected;
    private Spinner spnShow;
    private ArrayAdapter spnShowAdapter = null;
    private GridView gvSeat;
    private ArrayList<Seat> seats;
    private SeatAdapter adapterSeat = null;
    private ArrayList<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getData();
        getWidget();
        loadData();
        addEvents();
    }
    private void addEvents() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowActivity.this, ServiceActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("movie", movie);
                b.putSerializable("show", curShow);
                b.putSerializable("cinema", cinema);
                b.putSerializable("tickets", adapterSeat.getTicketsSelected());
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
        spnShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curShow = shows.get(position);
                seats = (ArrayList<Seat>) SeatDAO.getInstance(ShowActivity.this).getListSeated(curShow.getThreater_id());
                tickets = new ArrayList<>();
                int num_column = findNumColumnOfGridView(seats);
                gvSeat.setNumColumns(num_column);
                adapterSeat = new SeatAdapter(ShowActivity.this, R.layout.layout_item_seat, seats, curShow, tickets, tvSeatSelected, tvTotal);
                gvSeat.setAdapter(adapterSeat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        tvCinema.setText(cinema.getName());
        tvMovieName.setText(movie.getName());
        tvTypeAndAge.setText(MovieTypeDAO.getInstance(this).getById(movie.getType_id()).getName() + " - " + movie.getRequireAge());

    }

    private void getWidget() {
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        btnContinue = (Button) this.findViewById(R.id.btnContinue);
        tvCinema = (TextView) this.findViewById(R.id.tvCinema);
        tvMovieName = (TextView) this.findViewById(R.id.tvMovieName);
        tvTypeAndAge = (TextView) this.findViewById(R.id.tvTypeAndAge);
        tvSeatSelected = (TextView) this.findViewById(R.id.tvSeatSelected);
        tvTotal = (TextView) this.findViewById(R.id.tvTotal);
        spnShow = (Spinner) this.findViewById(R.id.spnShow);
        spnShowAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, convertShowsToStrings(shows));
        spnShowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShow.setAdapter(spnShowAdapter);
        spnShow.setSelection(indexOf(shows, curShow));

        gvSeat = (GridView) this.findViewById(R.id.gvSeat);
        seats = (ArrayList<Seat>) SeatDAO.getInstance(this).getListSeated(curShow.getThreater_id());
        tickets = new ArrayList<>();
        int num_column = findNumColumnOfGridView(seats);
        gvSeat.setNumColumns(num_column);
        adapterSeat = new SeatAdapter(this, R.layout.layout_item_seat, seats, curShow, tickets, tvSeatSelected, tvTotal);
        gvSeat.setAdapter(adapterSeat);
    }

    private void getData() {
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        assert b != null;
        cinema = (Cinema) b.getSerializable("cinema");
        shows = (ArrayList<Show>) b.getSerializable("shows");
        curShow = (Show) b.getSerializable("show");
        movie = (Movie) b.getSerializable("movie");
    }

    private String[] convertShowsToStrings(ArrayList<Show> shows) {
        String[] result = new String[shows.size()];
        for (int i = 0; i < shows.size(); i++) {
            result[i] = shows.get(i).getDatetime();
        }
        return result;
    }

    // Từ seats tính toán ra số lượng cột trong gridview
    private int findNumColumnOfGridView(ArrayList<Seat> seats) {
        // Ý tưởng là tìm số lớn nhất từ tên của seat, ví dụ A1 -> A8 thì số cột là 8
        int result = 0;
        for (Seat s : seats) {
            int temp = Integer.parseInt(s.getName().substring(1));
            result = Math.max(result, temp);
        }
        return result;
    }
    private int indexOf(ArrayList<Show> shows, Show show){
        for (int i = 0;i<shows.size();i++){
            if (shows.get(i).getId() == show.getId())
                return i;
        }
        return -1;
    }
}