package com.example.appbanvexemphim.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanvexemphim.Database.DBHelper;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Ticket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieDAO extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static MovieDAO instance;

    public static MovieDAO getInstance(Context context){
        if (instance == null){
            instance = new MovieDAO(context);
        }
        return instance;
    }

    private final SQLiteDatabase db;

    public MovieDAO(Context context) {
        super(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @SuppressLint({"Recycle","Range"})
    public Ticket getMovieByBill(int bill_id){
        Ticket ticket = new Ticket();
        String sql = "SELECT * FROM viewTicketAndBill_id WHERE bill_id = ? ";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(bill_id)});
        try {
            while (c.moveToNext()) {
                ticket.setId(c.getInt(c.getColumnIndex("id")));
                ticket.setShow_id(c.getInt(c.getColumnIndex("show_id")));
                ticket.setSeatName(c.getString(c.getColumnIndex("seatName")));
                ticket.setPrice(c.getFloat(c.getColumnIndex("price")));
                ticket.setState(c.getInt(c.getColumnIndex("state")));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return ticket;
    }

    @SuppressLint("Range")
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie";
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Movie movie = new Movie(); // Tạo đối tượng Movie
            movie.setId(c.getInt(c.getColumnIndex("id"))); // ID của bộ phim
            movie.setName(c.getString(c.getColumnIndex("name"))); // Tên bộ phim
            movie.setDuration(c.getInt(c.getColumnIndex("duration"))); // Thời lượng
            movie.setImage(c.getString(c.getColumnIndex("image")));
            movie.setRequireAge(c.getString(c.getColumnIndex("requiredAge"))); // Tuổi tối thiểu
            movie.setType_id(c.getInt(c.getColumnIndex("type_id"))); // ID thể loại
            movie.setDescription(c.getString(c.getColumnIndex("desciption"))); // Mô tả
            movie.setRating(c.getFloat(c.getColumnIndex("rating"))); // Đánh giá

            movies.add(movie); // Thêm vào danh sách
        }
        c.close(); // Đóng Cursor
        return movies; // Trả về danh sách bộ phim
    }

    @SuppressLint({"Range", "Recycle"})
    public Movie getById(int movie_id){
        Movie movie = new Movie();
        String sql = "SELECT * FROM movie WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(movie_id)});
        while (c.moveToNext()) {
            movie.setId(c.getInt(c.getColumnIndex("id")));
            movie.setName(c.getString(c.getColumnIndex("name")));
            movie.setDuration(c.getInt(c.getColumnIndex("duration")));
            movie.setType_id(c.getInt(c.getColumnIndex("type_id")));
            movie.setRequireAge(c.getString(c.getColumnIndex("requiredAge")));
            movie.setRating(c.getInt(c.getColumnIndex("rating")));
            movie.setDescription(c.getString(c.getColumnIndex("desciption")));
            movie.setImage(c.getString(c.getColumnIndex("image")));
        }
        c.close();
        return movie;
    }
    @SuppressLint({"Range", "Recycle"})
    public String getNamebyID(int id){
        Movie movie = new Movie();
        String sql = "SELECT name FROM movie WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (c.moveToNext()){
            movie.setName(c.getString(c.getColumnIndex("name")));
        }
        c.close();
        return movie.getName();
    }

    @SuppressLint("Recycle")
    public ArrayList<Integer> getMovieIdByShowAndCinema(int cinema_id, Date date){
        ArrayList<Integer> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT movie_id FROM viewShowByCinema WHERE cinema_id = ? AND datetime like ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String str = "%" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR) + "%";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(cinema_id), str});
            while(c.moveToNext()){
                movies.add(c.getInt(0));
            }
            c.close();
        return movies;
    }

    @SuppressLint("Recycle")
    public ArrayList<Integer> getCinemaIdByShowAndMovie(int movie_id, Date date){
        ArrayList<Integer> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT cinema_id FROM viewShowByCinema WHERE movie_id = ? AND datetime like ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String str = "%" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR) + "%";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(movie_id), str});
            while(c.moveToNext()){
                movies.add(c.getInt(0));
            }
        c.close();
        return movies;
    }

    @SuppressLint({"Recycle","Range"})
    public ArrayList<Movie> get3MovieNew() {
        ArrayList<Movie> listMovie = new ArrayList<>();
        String sql = "SELECT * FROM movie ORDER BY id LIMIT 3";

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(c.getInt(c.getColumnIndex("id")));
            movie.setName(c.getString(c.getColumnIndex("name")));
            movie.setDuration(c.getInt(c.getColumnIndex("duration")));
            movie.setImage(c.getString(c.getColumnIndex("image")));
            movie.setRequireAge(c.getString(c.getColumnIndex("requiredAge"))); // Tuổi tối thiểu
            movie.setType_id(c.getInt(c.getColumnIndex("type_id"))); // ID thể loại
            movie.setDescription(c.getString(c.getColumnIndex("desciption"))); // Mô tả
            movie.setRating(c.getFloat(c.getColumnIndex("rating"))); // Đánh giá
            listMovie.add(movie);
        }
        c.close();

        return listMovie;
    }
    public long insertMovie(String name, int duration, String imageResource, String requireAge, int type_id, String description, float rating) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("duration", duration);
        values.put("image", imageResource);
        values.put("requiredAge", requireAge);
        values.put("type_id", type_id);
        values.put("desciption", description);
        values.put("rating", rating);

        return db.insert("movie", null, values); // Chèn vào bảng "movie"
    }

    @SuppressLint({"Recycle","Range"})
    public List<Movie> getListMovie() {
        List<Movie> listMovie = new ArrayList<>();
        String sql = "SELECT * FROM movie";

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(c.getInt(c.getColumnIndex("id")));
            movie.setName(c.getString(c.getColumnIndex("name")));
            movie.setDuration(c.getInt(c.getColumnIndex("duration")));
            movie.setImage(c.getString(c.getColumnIndex("image")));
            movie.setRequireAge(c.getString(c.getColumnIndex("requiredAge"))); // Tuổi tối thiểu
            movie.setType_id(c.getInt(c.getColumnIndex("type_id"))); // ID thể loại
            movie.setDescription(c.getString(c.getColumnIndex("desciption"))); // Mô tả
            movie.setRating(c.getFloat(c.getColumnIndex("rating"))); // Đánh giá
            listMovie.add(movie);
        }
        c.close();

        return listMovie;
    }

    // Cập nhật bộ phim (Update)
    public int updateMovie(int id, String name, int duration, String imageResource, String requireAge, int type_id, String description, float rating) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("duration", duration);
        values.put("image", imageResource);
        values.put("requiredAge", requireAge);
        values.put("type_id", type_id);
        values.put("desciption", description);
        values.put("rating", rating);

        return db.update("movie", values, "id = ?", new String[]{String.valueOf(id)}); // Cập nhật theo ID
    }
    // Xóa bộ phim (Delete)
    public int deleteMovie(int id) {
        return db.delete("movie", "id = ?", new String[]{String.valueOf(id)});
    }

}
