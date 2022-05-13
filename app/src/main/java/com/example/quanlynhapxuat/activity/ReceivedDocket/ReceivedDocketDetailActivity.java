package com.example.quanlynhapxuat.activity.ReceivedDocket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.adapter.ReceivedDocketDetailAdapter;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.ReceivedDocket;
import com.example.quanlynhapxuat.utils.CustomAlertDialog;
import com.example.quanlynhapxuat.utils.CustomToast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceivedDocketDetailActivity extends AppCompatActivity {
    private ReceivedDocket receivedDocket;
    private ReceivedDocketDetailAdapter rddAdapter;

    private TextView tvTittle, tvMaPN, tvMaNV, tvTongGiaTri;
    private EditText etNgayDat, etNhaCungCap;
    private ImageView ivDatePicker;
    private Button btnThemSP, btnTaoPhieuNhap, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_docket_detail);

        setControl();

        Intent intent = getIntent();
        int maPN = intent.getIntExtra("maPN",-99);
        if(maPN<0) {
            CustomToast.makeText(this,"maPN = " + maPN,CustomToast.LENGTH_LONG,CustomToast.CONFUSING).show();
        }
        else if(maPN==0){
            int maNV = 999;
            tvMaPN.setText("<none>");
            tvMaNV.setText(maNV+"");
            tvTongGiaTri.setText(NumberFormat.getNumberInstance(Locale.US).format(0)+"VND");

            receivedDocket = new ReceivedDocket();
        }
        else {
            //TODO
        }

        ivDatePicker.setOnClickListener(view -> {
            dateTimePicker();
        });

        btnThemSP.setOnClickListener(view -> {
            themSP();
        });

        btnTaoPhieuNhap.setOnClickListener(view -> {
            Log.e("Linh đẹp trai", "Linh đẹp trai");
            taoPhieuNhap();
        });

        btnHuy.setOnClickListener(view -> {
            huy();
        });
    }

    private void setControl() {
        tvTittle = findViewById(R.id.tvTittle_activityRDD);
        tvMaPN = findViewById(R.id.tvMaPN_activityRDD);
        tvMaNV = findViewById(R.id.tvMaNV_activityRDD);
        tvTongGiaTri = findViewById(R.id.tvTongGiaTri_activityRDD);
        etNgayDat = findViewById(R.id.etNgayDat_activityRDD);
        etNhaCungCap = findViewById(R.id.etNhaCungCap_activityRDD);
        ivDatePicker = findViewById(R.id.ivDatePicker_activityRDD);
        btnThemSP = findViewById(R.id.btnThemSanPham_activityRDD);
        btnHuy = findViewById(R.id.btnHuy_activityRDD);
        btnTaoPhieuNhap = findViewById(R.id.btnTaoPhieuNhap_activityRDD);
    }

    private void themSP() {
        //TODO
        Log.e("vnl","Vũ Ngọc Linh");
    }

    private void taoPhieuNhap() {
        String ngayDat = etNgayDat.getText().toString();
        if(ngayDat.equals("")) {
            CustomToast.makeText(this,"Không được để trống ngày đặt!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }
        String nhaCungCap = etNhaCungCap.getText().toString();
        if(nhaCungCap.equals("")) {
            CustomToast.makeText(this,"Không được để trống nhà cung cấp!",CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }

        receivedDocket.setId(69);
        receivedDocket.setCreatedAt(ngayDat);
        receivedDocket.setEmployee_id(Integer.parseInt(tvMaNV.getText().toString()));
        receivedDocket.setStatus(1);
        receivedDocket.setSupplier_name(nhaCungCap);

        Log.e("receivedDocket",receivedDocket.toString());

        ApiUtils.getReceivedDocketService().postReceivedDocket(receivedDocket).enqueue(new Callback<ReceivedDocket>() {

            @Override
            public void onResponse(Call<ReceivedDocket> call, Response<ReceivedDocket> response) {
                if(response.isSuccessful()) {
                    CustomToast.makeText(ReceivedDocketDetailActivity.this,"Thêm phiếu nhập thành công!",CustomToast.LENGTH_SHORT,CustomToast.SUCCESS).show();
                    finish();
                }
                else {
                    CustomToast.makeText(ReceivedDocketDetailActivity.this,response.body().toString(),CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedDocket> call, Throwable t) {
                CustomToast.makeText(ReceivedDocketDetailActivity.this,"Thêm phiếu nhập thất bại!",CustomToast.LENGTH_SHORT,CustomToast.ERROR).show();
                finish();
            }
        } );
    }

    private void huy() {
        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics metrics=getResources().getDisplayMetrics();
        alertDialog.getWindow().setLayout((7*metrics.widthPixels)/8, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

        alertDialog.setMessage("Những Thay đổi/Thêm mới\nsẽ không được lưu?");
        alertDialog.setBtnPositive("Hủy và Thoát");
        alertDialog.setBtnNegative("Không");

        alertDialog.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alertDialog.btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    private void dateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, i1, i2, i3) -> {
            calendar.set(Calendar.YEAR, i1);
            calendar.set(Calendar.MONTH, i2);
            calendar.set(Calendar.DAY_OF_MONTH, i3);

            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, i4, i5) -> {
                calendar.set(Calendar.HOUR_OF_DAY, i4);
                calendar.set(Calendar.MINUTE, i5);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                etNgayDat.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(this,onTimeSetListener
                    ,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true)
                    .show();
        };

        new DatePickerDialog(this,onDateSetListener
                ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}