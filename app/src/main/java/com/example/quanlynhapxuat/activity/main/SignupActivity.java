package com.example.quanlynhapxuat.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhapxuat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivBack;
    Button btnSignup;
    TextView tvResendOTP;
    TextInputEditText etEmail,etName,etPhoneSignup;
    ProgressBar progressBar;
EditText etPassword,etPassword2;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        mAuth=FirebaseAuth.getInstance();
        ivBack.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }
    private void initViews(){
        ivBack=findViewById(R.id.iv_backsignin);
        btnSignup=findViewById(R.id.btn_signup);
        tvResendOTP=findViewById(R.id.tv_resendotp);
        etEmail=findViewById(R.id.et_email);
        etName=findViewById(R.id.et_fullname);
        etPhoneSignup=findViewById(R.id.et_phone_signup);
        etPassword=findViewById(R.id.et_password);
        etPassword2=findViewById(R.id.et_confirm_password);
        progressBar=findViewById(R.id.progessSignup);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                progressBar.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.INVISIBLE);
                String phoneNumber=etPhoneSignup.getText().toString().trim();
                verifyPhoneNumber(phoneNumber);
                Log.e("TAG", "Phone: " );
                break;
            case R.id.iv_backsignin:
                onBackPressed();
                break;
        }
    }

    private void verifyPhoneNumber(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnSignup.setVisibility(View.VISIBLE);
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnSignup.setVisibility(View.VISIBLE);
                                Toast.makeText(SignupActivity.this,"Xác thực không thành công",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnSignup.setVisibility(View.VISIBLE);
                                super.onCodeSent(s, forceResendingToken);
                                gotoEnterOTPActivity(phoneNumber,s);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            gotoMainActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignupActivity.this,"The verification" +
                                        "code entered was invalid",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void gotoMainActivity(String phoneNumber) {
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        intent.putExtra("phone_num",phoneNumber);
        startActivity(intent);
    }

    private void gotoEnterOTPActivity(String phoneNumber, String s) {
        Intent intent=new Intent(SignupActivity.this,VerifyOTPActivity.class);
        intent.putExtra("phone_num",phoneNumber);
        intent.putExtra("verification_id",s);
        startActivity(intent);
    }
}