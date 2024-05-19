package com.example.appbanvexemphim.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    ImageButton ibtnBackLogin;
    TextView tvRegister, tvForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadWidget();
        addEvent();
    }

    private void loadWidget() {
        ibtnBackLogin = (ImageButton) this.findViewById(R.id.ibtnBack);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        edtUsername = (EditText) this.findViewById(R.id.edtUsername);
        edtPassword = (EditText) this.findViewById(R.id.edtPassword);
        tvRegister = (TextView) this.findViewById(R.id.tvRegister);
        tvForgotPassword = (TextView) this.findViewById(R.id.tvForgotPassword);
    }

    private void addEvent() {
        ibtnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (edtUsername.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this, "Mời nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                    else if(edtPassword.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this, "Mời nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        User user = UserDAO.getInstance(LoginActivity.this).login(edtUsername.getText().toString(), edtPassword.getText().toString());

                        if (user != null){
                            SharedPreferences sharedPre = getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPre.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("username", edtUsername.getText().toString());
                            editor.putString("password", edtPassword.getText().toString());
                            editor.apply(); // Save changes

                            Constant.user = user;
                            setResult(MainActivity.LOGIN_SUCCESFULLY);
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            edtPassword.setText("");
                            edtPassword.requestFocus();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e){
                    Log.d("LoginActivity Error", "Lỗi button login: " + e.getMessage());
                }
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}