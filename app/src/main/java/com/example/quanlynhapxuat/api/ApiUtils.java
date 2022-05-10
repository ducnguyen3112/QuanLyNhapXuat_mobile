package com.example.quanlynhapxuat.api;

public class ApiUtils {

    public static final String baseURL = "https://shoesstation.herokuapp.com/api";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(baseURL).create(APIService.class);
    }
}
