package com.example.appbanvexemphim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanvexemphim.Activity.AdminActivity;
import com.example.appbanvexemphim.Activity.CinemaAdminActivity;
import com.example.appbanvexemphim.Activity.LoginActivity;
import com.example.appbanvexemphim.Activity.MainActivity;
import com.example.appbanvexemphim.Activity.RegisterActivity;
import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.Utils.Constant;

public class PreLogin extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_app_prelogin);
        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // If user is already logged in, redirect to MainActivity
            Intent intent = new Intent(PreLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        loadWidget();
        addEvent();
    }
    private void loadWidget()
    {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }
    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(PreLogin.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (Exception e){
                    Log.d("LoginActivity Error", "Lỗi button login: " + e.getMessage());
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PreLogin.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}