package com.example.quanlynhapxuat.activity.KhachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlynhapxuat.R;

public class UpdateKHActivity extends AppCompatActivity {

    private TextView txtEditChooseImgae;
    private EditText edtEditNameKH, edtEditAddressKH, edtEditPhoneKH, edtEditEmailKH;
    private Button btnEditKH, btnCancelEditKH;
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
        setContentView(R.layout.activity_update_khactivity);
    }

    private void setControl() {
        edtEditNameKH = findViewById(R.id.edtEditNameKH);
        edtEditAddressKH = findViewById(R.id.edtEditAddressKH);
        edtEditPhoneKH = findViewById(R.id.edtEditPhoneKH);
        edtEditEmailKH = findViewById(R.id.edtEditEmailKH);
        btnEditKH = findViewById(R.id.btnEditKH);
        btnCancelEditKH = findViewById(R.id.btnCancelEditKH);
        txtEditChooseImgae = findViewById(R.id.txtEditChooseImgae);
    }


    private void setEvent() {
        btnEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnCancelEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(UpdateKHActivity.this, MainActivity.class);
               // startActivity(intent);
            }
        });

    }

}