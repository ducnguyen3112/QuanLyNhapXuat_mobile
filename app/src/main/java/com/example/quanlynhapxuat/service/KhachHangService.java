package com.example.quanlynhapxuat.service;

import com.example.quanlynhapxuat.model.KhachHang;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KhachHangService {
    @GET("customers")
    Call<List<KhachHang>> getAllKH();

    @POST("customers")
    Call<KhachHang> createKH(@Body KhachHang kh);
}
