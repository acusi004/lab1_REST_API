package com.example.demo1_and;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKy extends AppCompatActivity {
    EditText edt_Email, edt_Password;
    Button btn_DangKy;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edt_Email = findViewById(R.id.edt_dk_email);
        edt_Password = findViewById(R.id.edt_dk_password);
        btn_DangKy = findViewById(R.id.btn_SignUp);
        FirebaseApp.initializeApp(DangKy.this);
        mAuth = FirebaseAuth.getInstance();

        btn_DangKy.setOnClickListener(v -> {
            Register();
        });
    }


    void Register(){
        String email = edt_Email.getText().toString();
        String password = edt_Password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "dang ky thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangKy.this, MainActivity.class));
                        finish();
                    }else{

                        Toast.makeText(this, "dang ky thai bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void logout(){
        FirebaseAuth.getInstance().signOut();
    }
}