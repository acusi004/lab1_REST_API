package com.example.demo1_and;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button btn_dangNhap;
    TextView tv_dangKy, tv_quenMk, tv_loginPhone;
    EditText edt_email, edt_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        tv_dangKy = findViewById(R.id.btn_dn_sign_up);
        btn_dangNhap = findViewById(R.id.btn_login);
        edt_email = findViewById(R.id.edt_dn_email);
        edt_password = findViewById(R.id.edt_dn_password);
        tv_quenMk = findViewById(R.id.btn_forgotPassword);
        tv_loginPhone = findViewById(R.id.btn_loginPhone);
        tv_loginPhone.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterPhone.class));
            finish();
        });
        tv_quenMk.setOnClickListener(v -> {
            String email = edt_email.getText().toString().trim();
           mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(MainActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        });
        tv_dangKy.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DangKy.class));
            finish();
        });
        btn_dangNhap.setOnClickListener(v -> {
           login();
        });
    }

   public void login(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "can nhap du lieu", Toast.LENGTH_SHORT).show();
        }else {
           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(MainActivity.this, "dang nhap thanh cong"+ email, Toast.LENGTH_SHORT).show();
                       FirebaseUser user = mAuth.getCurrentUser();
                       startActivity(new Intent(MainActivity.this, logout.class));
                   }else{
                       Toast.makeText(MainActivity.this, "dang nhap thai bai", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }


    }
}