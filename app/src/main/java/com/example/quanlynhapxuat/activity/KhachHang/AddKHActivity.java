package com.example.quanlynhapxuat.activity.KhachHang;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.KhachHang;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKHActivity extends AppCompatActivity {

    private TextView txtChooseImgae;
    private EditText edtAddNameKH, edtAddAddressKH, edtAddPhoneKH, edtAddEmailKH;
    private Button btnAddKH, btnCancelAddKH;
    private static final int REQUEST_CODE_FOLDER = 123;

//    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK) {
//
//                    }
//                }
//            }
//    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khactivity);
        setControl();
        setEvent();
    }

    private void setControl() {
        edtAddNameKH = findViewById(R.id.edtAddNameKH);
        edtAddAddressKH = findViewById(R.id.edtAddAddressKH);
        edtAddPhoneKH = findViewById(R.id.edtAddPhoneKH);
        edtAddEmailKH = findViewById(R.id.edtAddEmailKH);
        btnAddKH = findViewById(R.id.btnAddKH);
        btnCancelAddKH = findViewById(R.id.btnCancelAddKH);
        txtChooseImgae = findViewById(R.id.txtChooseImgae);
    }


    private void setEvent() {
        btnAddKH.setOnClickListener(view -> {
            String fullName = edtAddNameKH.getText().toString().trim();
            String phoneNumber = edtAddAddressKH.getText().toString().trim();
            String address = edtAddPhoneKH.getText().toString().trim();
            String email = edtAddEmailKH.getText().toString().trim();
            KhachHang kh = new KhachHang(fullName, phoneNumber, address, email);
            ApiUtils.getKhachHangService().createKH(kh).enqueue(new Callback<KhachHang>() {
                @Override
                public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddKHActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<KhachHang> call, Throwable t) {

                }
            });
        });
        btnCancelAddKH.setOnClickListener(view -> {
            Intent intent = new Intent(AddKHActivity.this, ListKHActivity.class);
            startActivity(intent);
        });

    }
}