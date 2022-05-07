package com.example.quanlynhapxuat.activity.KhachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlynhapxuat.R;

public class AddKHActivity extends AppCompatActivity {

    private TextView txtChooseImgae;
    private EditText edtAddNameKH, edtAddAddressKH, edtAddPhoneKH,edtAddEmailKH;
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
        btnAddKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnCancelAddKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AddKHActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

    }
}