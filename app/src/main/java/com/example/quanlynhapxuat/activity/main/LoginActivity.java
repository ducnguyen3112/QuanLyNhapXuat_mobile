package com.example.quanlynhapxuat.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quanlynhapxuat.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnForgot,btnSignin,btnGoSignup;
    TextInputEditText etPhoneLogin;
    EditText etPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        btnGoSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);

    }
    private void initViews(){
        btnForgot=findViewById(R.id.btn_forgot_password);
        btnSignin=findViewById(R.id.btn_signin);
        btnGoSignup=findViewById(R.id.btn_go_signup);
        etPhoneLogin=findViewById(R.id.et_phone_login);
        etPasswordLogin=findViewById(R.id.et_password_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forgot_password:
                Intent intent=new Intent(LoginActivity.this,ForgotPasswdActivity.class);
                startActivity(intent);
            case R.id.btn_signin:
                intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_go_signup:
                intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                break;
        }
    }
}