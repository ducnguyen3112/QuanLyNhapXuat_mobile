package com.example.quanlynhapxuat.activity.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.main.MainActivity;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.utils.CustomToast;

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
            if (validationKH()) {
                String fullName = edtAddNameKH.getText().toString().trim();
                String phoneNumber = edtAddPhoneKH.getText().toString().trim();
                String address = edtAddAddressKH.getText().toString().trim();
                String email = edtAddEmailKH.getText().toString().trim();
                KhachHang kh = new KhachHang(fullName, phoneNumber, address, email);
                ApiUtils.getKhachHangService().createKH(kh).enqueue(new Callback<KhachHang>() {
                    @Override
                    public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                        if (response.isSuccessful()) {
                            CustomToast.makeText(AddKHActivity.this, "Thêm thành công",
                                    CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KhachHang> call, Throwable t) {
                        CustomToast.makeText(AddKHActivity.this, "Thêm thất bại",
                                CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                        Log.e("Error", t.getMessage());
                    }
                });
            }

        });
        btnCancelAddKH.setOnClickListener(view -> {
            Intent intent = new Intent(AddKHActivity.this, ListKHActivity.class);
            startActivity(intent);
        });
    }

    boolean validationKH() {
        String fullName = edtAddNameKH.getText().toString().trim();
        String address = edtAddAddressKH.getText().toString().trim();
        String phoneNumber = edtAddPhoneKH.getText().toString().trim();
        String email = edtAddEmailKH.getText().toString().trim();

        if (fullName.isEmpty()) {
            CustomToast.makeText(AddKHActivity.this, "Không được để trống trường tên",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (fullName.length() < 2) {
            CustomToast.makeText(AddKHActivity.this, "Tên khách hàng tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (fullName.length() > 30) {
            CustomToast.makeText(AddKHActivity.this, "Tên khách hàng không 30 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (address.isEmpty()) {
            CustomToast.makeText(AddKHActivity.this, "Không được để trống trường địa chỉ",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (address.length() < 2) {
            CustomToast.makeText(AddKHActivity.this, "Địa chỉ tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (phoneNumber.length() > 12) {
            CustomToast.makeText(AddKHActivity.this, "Số điện thoại tối đa 11 số",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (phoneNumber.length() < 10) {
            CustomToast.makeText(AddKHActivity.this, "Số điện thoại tối thiểu 10 số",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (email.isEmpty()) {
            CustomToast.makeText(AddKHActivity.this, "Không được để trống trường email",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        } else if (email.length() < 2) {
            CustomToast.makeText(AddKHActivity.this, "Email tối thiểu 2 ký tự",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        }

        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddKHActivity.this, ListKHActivity.class);
        startActivity(intent);
    }
}