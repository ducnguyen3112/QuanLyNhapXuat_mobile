package com.example.quanlynhapxuat.api;

import com.example.quanlynhapxuat.service.KhachHangService;
import com.example.quanlynhapxuat.service.ProductService;
import com.example.quanlynhapxuat.service.ReceivedDocketService;
import com.example.quanlynhapxuat.service.UploadService;

public class ApiUtils {

    public static final String baseURL = "https://shoesstation.herokuapp.com/api/";

    public static KhachHangService getKhachHangService() {
        return RetrofitClient.getClient(baseURL).create(KhachHangService.class);
    }

    public static ReceivedDocketService getReceivedDocketService() {
        return RetrofitClient.getClient(baseURL).create(ReceivedDocketService.class);
    }


    public static ProductService getProductService() {
        return RetrofitClient.getClient(baseURL).create(ProductService.class);
    }

    public static UploadService getUploadService() {
        return RetrofitClient.getClient(baseURL).create(UploadService.class);
    }

}

