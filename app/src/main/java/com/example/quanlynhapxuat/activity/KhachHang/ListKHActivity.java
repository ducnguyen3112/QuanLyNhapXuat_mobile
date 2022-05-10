package com.example.quanlynhapxuat.activity.KhachHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quanlynhapxuat.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khactivity);
        setControl();
        setEvent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvKH.setLayoutManager(linearLayoutManager);
        getAllKH();

    }

    private void setControl() {
        rcvKH = findViewById(R.id.rcv_kh);
        fabAddKh = findViewById(R.id.fabAddKh);

    }

    public void getAllKH() {

        Call<List<KhachHang>> listKH = ApiUtils.getAPIService().getAllKH();

        listKH.enqueue(new Callback<List<KhachHang>>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                if (response.isSuccessful()) {
                    List<KhachHang> listkH = response.body();
                    khachHangAdapter = new KhachHangAdapter(ListKHActivity.this, ListKHActivity.this, listkH);
                    rcvKH.setAdapter(khachHangAdapter);
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
                //Intent intent = new Intent(MainActivity.this, AddKhachHangActivity.class);
                //startActivity(intent);
            }
        });
    }
}