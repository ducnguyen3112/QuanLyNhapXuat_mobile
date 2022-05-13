package com.example.quanlynhapxuat.service;

import com.example.quanlynhapxuat.model.ReceivedDocket;
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReceivedDocketService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();

    ReceivedDocketService RECEIVED_DOCKET_SERVICE = new Retrofit.Builder()
            .baseUrl("https://shoesstation.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ReceivedDocketService.class);

    @GET("receivedDockets/")
    Call<ArrayList<ReceivedDocket>> getReceivedDocketList();

    @GET("receivedDocketDetails/")
    Call<ArrayList<ReceivedDocketDetail>> getReceivedDocketDetailList();

    @POST("receivedDockets/")
    Call<ReceivedDocket> postReceivedDocket(@Body ReceivedDocket receivedDocket);
}
