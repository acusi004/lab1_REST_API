package com.example.demo1_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class logout extends AppCompatActivity {
    Button  btn_logout, btn_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        btn_logout = findViewById(R.id.btn_Logout);
        btn_otp = findViewById(R.id.btn_OTP);

        btn_otp.setOnClickListener(v -> {
            startActivity(new Intent(logout.this, RegisterPhone.class));
            finish();
        });
        btn_logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(logout.this, MainActivity.class));
        });
    }
}