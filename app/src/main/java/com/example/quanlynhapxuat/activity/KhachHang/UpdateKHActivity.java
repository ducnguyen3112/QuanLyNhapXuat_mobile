package com.example.quanlynhapxuat.activity.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.utils.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateKHActivity extends AppCompatActivity {

    private TextView txtEditChooseImgae;
    private EditText edtEditNameKH, edtEditAddressKH, edtEditPhoneKH, edtEditEmailKH;
    private Button btnEditKH, btnCancelEditKH;
    private static final int REQUEST_CODE_FOLDER = 123;
    KhachHang dto;

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
        setControl();
        getAndSetIntentData();
        setEvent();

    }

    void getAndSetIntentData() {
        Intent intent = getIntent();
        dto = (KhachHang) intent.getSerializableExtra("KH");
        edtEditNameKH.setText(dto.getFullName());
        edtEditAddressKH.setText(dto.getAddress());
        edtEditPhoneKH.setText(dto.getPhoneNumber());
        edtEditEmailKH.setText(dto.getEmail());
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
                if (validationKH()) {
                    String fullName = edtEditNameKH.getText().toString().trim();
                    String address = edtEditAddressKH.getText().toString().trim();
                    String phoneNumber = edtEditPhoneKH.getText().toString().trim();
                    String email = edtEditEmailKH.getText().toString().trim();

                    KhachHang kh = new KhachHang(fullName, phoneNumber, address, email);
                    ApiUtils.getKhachHangService().updateKHById(dto.getId(), kh).enqueue(new Callback<KhachHang>() {
                        @Override
                        public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                            if (response.isSuccessful()) {
                                CustomToast.makeText(UpdateKHActivity.this, "Cập nhật thành công",
                                        CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<KhachHang> call, Throwable t) {
                            CustomToast.makeText(UpdateKHActivity.this, "Cập nhật thất bại",
                                    CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                            Log.e("Error", t.getMessage());
                        }
                    });
                }

            }
        });
        btnCancelEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateKHActivity.this, ProfileKHActivity.class);
                intent.putExtra("Check", 1);
                startActivity(intent);
            }
        });
    }

    boolean validationKH() {
        String fullName = edtEditNameKH.getText().toString().trim();
        String address = edtEditAddressKH.getText().toString().trim();
        String phoneNumber = edtEditPhoneKH.getText().toString().trim();
        String email = edtEditEmailKH.getText().toString().trim();

        if (fullName.isEmpty()) {
            CustomToast.makeText(UpdateKHActivity.this, "Không được để trống trường tên",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (fullName.length() < 2) {
            CustomToast.makeText(UpdateKHActivity.this, "Tên khách hàng tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (fullName.length() > 30) {
            CustomToast.makeText(UpdateKHActivity.this, "Tên khách hàng không 30 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (address.isEmpty()) {
            CustomToast.makeText(UpdateKHActivity.this, "Không được để trống trường địa chỉ",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (address.length() < 2) {
            CustomToast.makeText(UpdateKHActivity.this, "Địa chỉ tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (phoneNumber.length() > 12) {
            CustomToast.makeText(UpdateKHActivity.this, "Số điện thoại tối đa 11 số",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (phoneNumber.length() < 10) {
            CustomToast.makeText(UpdateKHActivity.this, "Số điện thoại tối thiểu 10 số",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (email.isEmpty()) {
            CustomToast.makeText(UpdateKHActivity.this, "Không được để trống trường email",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (email.length() < 2) {
            CustomToast.makeText(UpdateKHActivity.this, "Email tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateKHActivity.this, ListKHActivity.class);
        startActivity(intent);
    }
}