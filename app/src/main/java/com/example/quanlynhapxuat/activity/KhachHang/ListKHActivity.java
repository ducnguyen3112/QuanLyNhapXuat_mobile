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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKHActivity extends AppCompatActivity {

    private FloatingActionButton fabAddKh;
    private RecyclerView rcvKH;
    private KhachHangAdapter khachHangAdapter;
    List<KhachHang> list = new ArrayList<>();


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

        Call<JsonObject> call = ApiUtils.getKhachHangService().getAllKH();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonArray jsonArray = response.body().getAsJsonObject("_embedded").getAsJsonArray("customers");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject rootObject = jsonArray.get(0).getAsJsonObject();
                        list.add(new KhachHang(
                                rootObject.get("fullName").getAsString(),
                                rootObject.get("phoneNumber").getAsString(),
                                rootObject.get("address").getAsString(),
                                rootObject.get("email").getAsString(),
                                rootObject.get("avatar").getAsString()
                        ));
                    }
                    list.forEach(item ->
                            Log.e("H", item.toString()));
//                    khachHangAdapter = new KhachHangAdapter(ListKHActivity.this, ListKHActivity.this, listkH);
//                    rcvKH.setAdapter(khachHangAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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