package com.example.appbanvexemphim.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.R;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton ibtnBack;
    private EditText edtUsername, edtPassword, edtPasswordAgain, edtEmail, edtFullname;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWidgets();
        addEvents();
    }

    private void addEvents() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   String username = edtUsername.getText().toString();
                   String email = edtEmail.getText().toString();
                   String fullname = edtFullname.getText().toString();
                   String password = "";
                   if (edtPassword.getText().toString().equals(edtPasswordAgain.getText().toString())){
                       password = edtPassword.getText().toString();
                   }
                   if (UserDAO.getInstance(RegisterActivity.this).register(username,fullname, password, email)){
                       SharedPreferences sharedPre = getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPre.edit();
                       editor.putBoolean("isLoggedIn", true);
                       editor.putString("username", edtUsername.getText().toString());
                       editor.putString("password", edtPassword.getText().toString());
                       editor.apply();

                       Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                       setResult(MainActivity.REGISTER_SUCCESFULLY);
                       Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                       startActivity(i);
                       finish();
                   }
                   else{
                       Toast.makeText(RegisterActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                       edtUsername.setText("");
                       edtUsername.requestFocus();
                       edtEmail.setText("");
                       edtPassword.setText("");
                       edtPasswordAgain.setText("");
                   }
               }
               catch (Exception e){
                   e.printStackTrace();

               }
            }
        });
    }

    private void getWidgets() {
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        edtUsername = (EditText) this.findViewById(R.id.edtUsername);
        edtEmail = (EditText) this.findViewById(R.id.edtEmail);
        edtPassword = (EditText) this.findViewById(R.id.edtPassword);
        edtPasswordAgain = (EditText) this.findViewById(R.id.edtPasswordAgain);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        edtFullname = findViewById(R.id.edtFullname);
    }
}