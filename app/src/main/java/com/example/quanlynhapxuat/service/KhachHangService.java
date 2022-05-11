package com.example.quanlynhapxuat.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KhachHangService {
    @GET("customers")
    Call<JsonObject> getAllKH();
}
