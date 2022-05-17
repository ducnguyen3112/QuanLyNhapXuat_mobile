package com.example.quanlynhapxuat.activity.ReceivedDocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;
import com.example.quanlynhapxuat.model.RestErrorResponse;
import com.example.quanlynhapxuat.utils.CustomAlertDialog;
import com.example.quanlynhapxuat.utils.CustomToast;
import com.google.gson.Gson;

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
    private RecyclerView rcvListChiTietPN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_docket_detail);

        //setControl
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
        rcvListChiTietPN = findViewById(R.id.rcvListChiTietPN_activityRDD);

        //get&showList
        rddAdapter = new ReceivedDocketDetailAdapter(ReceivedDocketDetailActivity.this);
        rcvListChiTietPN.setLayoutManager(new LinearLayoutManager(ReceivedDocketDetailActivity.this));
        rcvListChiTietPN.setAdapter(rddAdapter);



        //
        Intent intent = getIntent();
        int maPN = intent.getIntExtra("maPN",-99);
        if(maPN<0) {
            CustomToast.makeText(this,"maPN = " + maPN,CustomToast.LENGTH_LONG,CustomToast.CONFUSING).show();
        }
        else if(maPN==0){
            // --  tạo phiếu nhập
            int maNV = 1;
            tvMaPN.setText(maPN+"");
            tvMaNV.setText(maNV+"");
            tvTongGiaTri.setText(NumberFormat.getNumberInstance(Locale.US).format(rddAdapter.getTotalList())+"VND");

            receivedDocket = new ReceivedDocket();
        }
        else {
            // -- sửa phiếu nhập
            getReceivedDocket(maPN);
        }

        //setEvent
        ivDatePicker.setOnClickListener(view -> {
            dateTimePicker();
        });

        btnThemSP.setOnClickListener(view -> {
            themSP();
        });

        btnTaoPhieuNhap.setOnClickListener(view -> {
            taoPhieuNhap(maPN);
        });

        btnHuy.setOnClickListener(view -> {
            huy();
        });
    }

    private void themSP() {
        //TODO -- thêm sản phẩm vào phiếu nhập
    }

    private void taoPhieuNhap(int maPN) {
        String ngayDat = etNgayDat.getText().toString();
        if(ngayDat.equals("")) {
            CustomToast.makeText(this,"Không được để trống ngày đặt!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }

        String nhaCungCap = etNhaCungCap.getText().toString();
        if(nhaCungCap.equals("")) {
            CustomToast.makeText(this,"Không được để trống nhà cung cấp!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }

        if(rddAdapter.getItemCount()==0) {
            CustomToast.makeText(this,"Phiếu nhập chưa có sản phẩm!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }

        receivedDocket = new ReceivedDocket(maPN,ngayDat,Integer.parseInt(tvMaNV.getText().toString()),1,nhaCungCap,null);

        ApiUtils.getReceivedDocketService().postReceivedDocket(receivedDocket).enqueue(new Callback<ReceivedDocket>() {

            @Override
            public void onResponse(Call<ReceivedDocket> call, Response<ReceivedDocket> response) {
                if(response.isSuccessful()) {
                    CustomToast.makeText(ReceivedDocketDetailActivity.this,"Thêm phiếu nhập thành công!"
                            ,CustomToast.LENGTH_SHORT,CustomToast.SUCCESS).show();
                    finish();
                }
                else {
                    try {
                        Gson g = new Gson();
                        RestErrorResponse errorResponse = g.fromJson(response.errorBody().string(),RestErrorResponse.class);
                        Log.e("errorResponseGetMessage",errorResponse.getMessage());
                        CustomToast.makeText(ReceivedDocketDetailActivity.this,errorResponse.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                    catch (Exception e) {
                        Log.e("e.getMessage()",e.getMessage());
                        CustomToast.makeText(ReceivedDocketDetailActivity.this,e.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceivedDocket> call, Throwable t) {
                CustomToast.makeText(ReceivedDocketDetailActivity.this,"CALL API FAIL!!!"
                        ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                finish();
            }
        });
    }

    private void  huy() {
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    private void getReceivedDocket(int maPN) {
        ApiUtils.getReceivedDocketService().getReceivedDocket(maPN).enqueue(new Callback<ReceivedDocket>() {
            @Override
            public void onResponse(Call<ReceivedDocket> call, Response<ReceivedDocket> response) {
                if(response.isSuccessful()) {
                    ReceivedDocket rd = response.body();
                    if(rd==null) {
                        CustomToast.makeText(ReceivedDocketDetailActivity.this,"rd==null"
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                        finish();
                    }
                    else {
                        rddAdapter.setRddList(rd.getReceivedDocketDetails());
                        tvTittle.setText("SỬA PHIẾU NHẬP");
                        tvMaPN.setText(rd.getId()+"");
                        tvMaNV.setText(rd.getEmployee_id()+"");
                        etNgayDat.setText(rd.getCreatedAt());
                        etNhaCungCap.setText(rd.getSupplier_name());
                        tvTongGiaTri.setText(NumberFormat.getNumberInstance(Locale.US).format(rddAdapter.getTotalList())+"VND");
                        btnTaoPhieuNhap.setText("CẬP NHẬT");
                    }
                }
                else {
                    try {
                        Gson g = new Gson();
                        RestErrorResponse errorResponse = g.fromJson(response.errorBody().string(),RestErrorResponse.class);
                        CustomToast.makeText(ReceivedDocketDetailActivity.this,"TRY: " + errorResponse.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                    catch (Exception e) {
                        CustomToast.makeText(ReceivedDocketDetailActivity.this,"CATCH: " + e.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceivedDocket> call, Throwable t) {
                CustomToast.makeText(ReceivedDocketDetailActivity.this,"CALL API FAIL!!!"
                        ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
            }
        });
    }

    private void capNhatDuLieu() {
        rddAdapter.notifyDataSetChanged();
        tvTongGiaTri.setText(NumberFormat.getNumberInstance(Locale.US).format(rddAdapter.getTotalList())+"VND");
    }
}