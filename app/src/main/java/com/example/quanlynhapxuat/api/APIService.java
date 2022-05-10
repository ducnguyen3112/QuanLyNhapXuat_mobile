package com.example.quanlynhapxuat.api;

import com.example.quanlynhapxuat.model.KhachHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface APIService {
    @GET("/posts")
    @FormUrlEncoded
    Call<List<KhachHang>> getAllKH();
}
