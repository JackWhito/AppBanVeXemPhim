package com.example.appbanvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanvexemphim.DAO.UserDAO;
import com.example.appbanvexemphim.Model.User;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;
import com.example.appbanvexemphim.Utils.ImageConverter;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton ibtnBackProfile;
    private ImageView ivProfileImage;
    private TextView edtFullname, edtBirthdate, edtAddress, edtPhone, edtEmail;
    private RadioGroup rdgGender;
    private Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadWidget();
        loadData();
        addEvent();
    }

    private void addEvent() {
        ibtnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UserDAO userDAO  = new UserDAO(this);
        User user = Constant.user;
        assert user != null;
        edtFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constant.user.setFullname(s.toString());           }
            @Override
            public void afterTextChanged(Editable editable) {   }});
        edtBirthdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constant.user.setBirthdate(s.toString());           }
            @Override
            public void afterTextChanged(Editable editable) {   }});

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constant.user.setPhone(s.toString());           }
            @Override
            public void afterTextChanged(Editable editable) {   }});
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constant.user.setEmail(s.toString());           }
            @Override
            public void afterTextChanged(Editable editable) {   }});
        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constant.user.setAddress(s.toString());           }
            @Override
            public void afterTextChanged(Editable editable) {   }});
        rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdbMale) {
                    Constant.user.setGender(0);
                } else if (checkedId == R.id.rdbFemale) {
                    Constant.user.setGender(1);
                }
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userDAO.updateUser(
                        Constant.user.getId(),
                        Constant.user.getFullname(),
                        Constant.user.getBirthdate(),
                        Constant.user.getAddress(),
                        Constant.user.getEmail(),
                        Constant.user.getPhone(), user.getGender()
                );
                Toast.makeText(getApplicationContext(), "Tài khoản đã được cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        User user = Constant.user;
        assert user != null;
        ivProfileImage.setImageBitmap(ImageConverter.getImage(user.getPicture(), getApplicationContext()));
        edtFullname.setText(user.getFullname());
        edtBirthdate.setText(user.getBirthdate());
        edtAddress.setText(user.getAddress());
        edtPhone.setText(user.getPhone());
        edtEmail.setText(user.getEmail());
        if (user.getGender()==0){
            rdgGender.check(R.id.rdbMale);
        }
        else{
            rdgGender.check(R.id.rdbFemale);
        }
    }

    private void loadWidget() {
        ibtnBackProfile = (ImageButton) findViewById(R.id.ibtnBackProfile);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtBirthdate = (EditText) findViewById(R.id.edtBirthdate);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        rdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfile);
    }
}