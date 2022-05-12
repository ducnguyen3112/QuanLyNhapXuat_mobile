package com.example.quanlynhapxuat.api;

import com.example.quanlynhapxuat.service.KhachHangService;

public class ApiUtils {

    public static final String baseURL = "https://shoesstation.herokuapp.com/api/";

    public static KhachHangService getKhachHangService() {
        return RetrofitClient.getClient(baseURL).create(KhachHangService.class);
    }
}
