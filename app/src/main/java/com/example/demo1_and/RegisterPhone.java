package com.example.demo1_and;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterPhone extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String mVerification;

    Button btn_getOTP, btn_sendOTP;
    EditText edt_phone, edt_OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        mAuth = FirebaseAuth.getInstance();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                final  String code = phoneAuthCredential.getSmsCode();

                if(code != null){
                    // neu nhan dc otp thi se tu dong set du lieu vao edittext
                    edt_OTP.setText(code);

                }else{

                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                //khi chúng tôi nhận được OTP, nó
                // chứa một id duy nhất
                // chúng tôi đang lưu trữ trong chuỗi
                // mà chúng tôi đã tạo
                mVerification = s;
            }
        };

        btn_getOTP = findViewById(R.id.btn_getOTP);
        btn_sendOTP = findViewById(R.id.btn_otp_login);
        edt_phone = findViewById(R.id.edt_dk_phone);
        edt_OTP = findViewById(R.id.edt_dk_otp);

        btn_getOTP.setOnClickListener(v -> {
           String sdt = "+84"+ edt_phone.getText().toString();
            getOTP(sdt);
        });
        btn_sendOTP.setOnClickListener(v -> {
           String otp =   edt_OTP.getText().toString();
            verifycode(otp);
        });
    }

    private void getOTP(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallback)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifycode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, code);
        signInWithCredential(credential);

    }
    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(RegisterPhone.this, logout.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterPhone.this, "dang nhap that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
