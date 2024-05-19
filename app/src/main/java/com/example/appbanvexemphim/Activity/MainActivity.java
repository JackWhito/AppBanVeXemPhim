package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.Adapter.AdapterReview;
import com.example.appbanvexemphim.Adapter.CinemaAdapter;
import com.example.appbanvexemphim.Adapter.MovieAdapter;
import com.example.appbanvexemphim.DAO.CinemaDAO;
import com.example.appbanvexemphim.DAO.MovieDAO;
import com.example.appbanvexemphim.DAO.ReviewDAO;
import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.Review;
import com.example.appbanvexemphim.PreLogin;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;
import com.example.appbanvexemphim.Utils.ImageConverter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int MO_ACTIVITY_REVIEW = 1;
    public static final int MO_ACTIVITY_PROFILE = 2;
    public static final int MO_ACTIVITY_HISTORY = 3;
    public static final int MO_ACTIVITY_NOTIFY = 4;
    public static final int MO_ACTIVITY_HOTLINE = 5;
    public static final int MO_ACTIVITY_TICKET = 6;
    public static final int MO_ACTIVITY_LOGIN = 7;
    public static final int MO_ACTIVITY_REGISTER = 8;
    public static final int LOGIN_SUCCESFULLY = 11;
    public static final int REGISTER_SUCCESFULLY = 12;
    private TextView tvProfile, tvHistory, tvNotify, tvHotline, tvLogout;
    //private ImageButton ibtnSearch;
    private Button btnLogout, btnLogin, btnRegister, btnAdmin;
    private ImageButton ibtnBackReview;
    private ListView lvReview, lvCinema;
    private ArrayList<Review> reviews;
    private AdapterReview adapterReview;
    private ArrayList<Cinema> cinemas;
    private CinemaAdapter adapterCinema;
    private View layoutLogin, layoutProfile;
    private View llSearch;
    private TabHost tab;
    private SearchView svReview;
    Animation in, out;
    GridView gridMovie;
    ViewFlipper viewFlipper;
    private ReviewDAO db = null;
    private SharedPreferences sharedPreferences;
    public static List<Movie> listMovie= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            Constant.user = UserDAO.getInstance(this).login(savedUsername, savedPassword);
        }
        getWidget();
        loadTabs();
        addEvent();
        actionHome();
    }

    private void addEvent() {
        lvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                doOpenReview(reviews.get(i));
            }
        });
        gridMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = (Movie) parent.getItemAtPosition(position);
                showProductDetail(selectedMovie);
            }
        });
        if (Constant.user != null) {
            tvProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doOpenProfile();
                }
            });
            tvHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doOpenHistory();
                }
            });
            tvNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doOpenNotify();
                }
            });
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.putBoolean("isLoggedIn",false);
                    editor.apply(); // Lưu thay đổi
                    Constant.user = null;
                    Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, PreLogin.class);
                    startActivity(i);
                    finish();
                }
            });
        } else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(i, MO_ACTIVITY_LOGIN);
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivityForResult(i, MO_ACTIVITY_REGISTER);
                }
            });
        }
        svReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi SearchView được nhấp vào, thiết lập layout_width là match_parent
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) svReview.getLayoutParams();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                svReview.setLayoutParams(params);
                ibtnBackReview.setVisibility(View.VISIBLE);
            }
        });
        ibtnBackReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi SearchView được nhấp vào, thiết lập layout_width là match_parent
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) svReview.getLayoutParams();
                params.width = getResources().getDimensionPixelSize(R.dimen.actionbar_size);
                svReview.setLayoutParams(params);
                ibtnBackReview.setVisibility(View.GONE);
                svReview.clearFocus();
                svReview.setQuery("", false);
                svReview.setIconified(true);
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });
        svReview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    reviews = ReviewDAO.getInstance(MainActivity.this).getAll();
                } else {
                    reviews = ReviewDAO.getInstance(MainActivity.this).search(query);
                }
                adapterReview = new AdapterReview(MainActivity.this, R.layout.layout_item_review, reviews);
                lvReview.setAdapter(adapterReview);
                svReview.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    reviews = ReviewDAO.getInstance(MainActivity.this).getAll();
                } else {
                    reviews = ReviewDAO.getInstance(MainActivity.this).search(newText);
                }
                adapterReview = new AdapterReview(MainActivity.this, R.layout.layout_item_review, reviews);
                lvReview.setAdapter(adapterReview);
                return true;
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == LOGIN_SUCCESFULLY) {
//            if (Constant.user.getRole() == 1) {
//                Intent i = new Intent(MainActivity.this, AdminActivity.class);
//                startActivity(i);
//            } else {
//                recreate();
//            }
//        }
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getWidget() {
        tab = (TabHost) findViewById(R.id.tabMain);
        layoutProfile = getLayoutInflater().inflate(R.layout.layout_app_profile, null); // layout_app_new là layout bạn muốn thêm
        layoutLogin = getLayoutInflater().inflate(R.layout.layout_app_login, null); // layout_app_new là layout bạn muốn thêm
        tvProfile = (TextView) layoutProfile.findViewById(R.id.tvProfile);
        tvHistory = (TextView) layoutProfile.findViewById(R.id.tvHistory);
        tvNotify = (TextView) layoutProfile.findViewById(R.id.tvNotify);

        lvReview = (ListView) findViewById(R.id.lvReview);
        reviews = ReviewDAO.getInstance(this).getAll();
        adapterReview = new AdapterReview(MainActivity.this, R.layout.layout_item_review, reviews);
        lvReview.setAdapter(adapterReview);

        lvCinema = findViewById(R.id.lvCinema);
        cinemas = CinemaDAO.getInstance(this).getAll();
        adapterCinema = new CinemaAdapter(this, R.layout.layout_item_cinema, cinemas);
        lvCinema.setAdapter(adapterCinema);

        ibtnBackReview = (ImageButton) findViewById(R.id.ibtnBackReview);
        btnLogout = (Button) layoutProfile.findViewById(R.id.btnLogout);
        btnLogin = (Button) layoutLogin.findViewById(R.id.btnLogin);
        btnRegister = (Button) layoutLogin.findViewById(R.id.btnRegister);
        svReview = (SearchView) findViewById(R.id.svReview);
        btnAdmin = layoutProfile.findViewById(R.id.btnAdmin);
        if(Constant.user.getRole() != 1){
            btnAdmin.setVisibility(View.GONE);
        }else{
            btnAdmin.setVisibility(View.VISIBLE);
        }

        in = AnimationUtils.loadAnimation(this, R.anim.menu_in);
        out = AnimationUtils.loadAnimation(this, R.anim.menu_out);
        viewFlipper = findViewById(R.id.viewlipper);
        listMovie = MovieDAO.getInstance(this).getListMovie();
        gridMovie = findViewById(R.id.gridMovie);
    }

    private void loadTabs() {

        tab.setup();
        TabHost.TabSpec spec;
        //Tab Home
        spec = tab.newTabSpec("tabHome");
        spec.setContent(R.id.tabHome);
        spec.setIndicator("Trang chủ");
        tab.addTab(spec);
        //Tab Cinema
        spec = tab.newTabSpec("tabCinema");
        spec.setContent(R.id.tabCinema);
        spec.setIndicator("Rạp phim");
        tab.addTab(spec);
        //Tab Review
        spec = tab.newTabSpec("tabReview");
        spec.setContent(R.id.tabReview);
        spec.setIndicator("Đánh giá");
        tab.addTab(spec);
        // Tạo một LinearLayout mới
        LinearLayout tabNew = new LinearLayout(this);
        tabNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tabNew.setOrientation(LinearLayout.VERTICAL);
        tabNew.setBackgroundResource(R.color.background);

        //Tab Login
        spec = tab.newTabSpec("tabProfile");
        spec.setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                if (Constant.user == null) {
                    // Thêm view vào LinearLayout
                    tabNew.addView(layoutLogin);
                } else {
                    // Thêm view vào LinearLayout
                    tabNew.addView(layoutProfile);
                }
                return tabNew;
            }
        });
        spec.setIndicator("Tài khoản");
        tab.addTab(spec);

        tab.setCurrentTab(0);
        Intent intent = getIntent();
        if (intent.hasExtra("tab")) {
            String tabTag = intent.getStringExtra("tab");
            Toast.makeText(this,"Hãy chọn rạp phim",Toast.LENGTH_LONG).show();
            tab.setCurrentTabByTag(tabTag);
        } else {
            tab.setCurrentTab(0); // Tab mặc định
        }
    }

    private void actionViewFlipper() {
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        for (int i = 0; i < listMovie.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(ImageConverter.getImage(listMovie.get(i).getImage(), MainActivity.this));
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentIndex = viewFlipper.getDisplayedChild();
                ArrayList<Movie> movies = MovieDAO.getInstance(MainActivity.this).get3MovieNew();
                switch (currentIndex) {
                    case 0:
                        Movie selectedMovie1 = movies.get(0);
                        showProductDetail(selectedMovie1);
                        break;
                    case 1:
                        Movie selectedMovie2 = movies.get(1);
                        showProductDetail(selectedMovie2);
                        break;
                    case 2:
                        Movie selectedMovie3 = movies.get(2);
                        showProductDetail(selectedMovie3);
                        break;

                }
            }
        });

    }

    private void showProductDetail(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);

        // Đưa thông tin sản phẩm vào Intent
        Bundle b = new Bundle();
        b.putSerializable("movie", movie);
        intent.putExtra("Data", b);
        startActivity(intent);
    }

    public void actionHome() {
        actionViewFlipper();
        MovieAdapter adapter = new MovieAdapter(this, R.layout.layout_item_movie, listMovie);
        gridMovie.setAdapter(adapter);
    }


    private void doOpenNotify() {
        Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
        startActivityForResult(intent, MO_ACTIVITY_NOTIFY);
    }

    private void doOpenHistory() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivityForResult(intent, MO_ACTIVITY_HISTORY);
    }

    private void doOpenProfile() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivityForResult(intent, MO_ACTIVITY_PROFILE);
    }

    private void doOpenReview(Review review) {
        Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Review", review);
        intent.putExtra("Data", b);
        startActivityForResult(intent, MO_ACTIVITY_REVIEW);
    }

}