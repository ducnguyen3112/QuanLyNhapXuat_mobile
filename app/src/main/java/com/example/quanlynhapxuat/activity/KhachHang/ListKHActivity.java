package com.example.quanlynhapxuat.activity.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.main.MainActivity;
import com.example.quanlynhapxuat.adapter.KhachHangAdapter;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKHActivity extends AppCompatActivity {

    private FloatingActionButton fabAddKh;
    private RecyclerView rcvKH;
    private KhachHangAdapter khachHangAdapter;
    public static int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khactivity);
        DisplayMetrics metrics=getResources().getDisplayMetrics();
        width=metrics.widthPixels;
        setControl();
        setEvent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvKH.setLayoutManager(linearLayoutManager);
        khachHangAdapter = new KhachHangAdapter(ListKHActivity.this, ListKHActivity.this);
        rcvKH.setAdapter(khachHangAdapter);
        getAllKH();
    }

    private void setControl() {
        rcvKH = findViewById(R.id.rcv_kh);
        fabAddKh = findViewById(R.id.fabAddKh);
    }

    public void getAllKH() {
        ApiUtils.getKhachHangService().getAllKH().enqueue(new Callback<List<KhachHang>>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                Log.e("list", response.body().toString());
                if (response.isSuccessful()) {
                    List<KhachHang> list = response.body();
                    khachHangAdapter.setDate(list);
                }
            }

            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    private void setEvent() {
        fabAddKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListKHActivity.this, AddKHActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}