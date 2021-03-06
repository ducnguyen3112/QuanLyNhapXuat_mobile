package com.example.quanlynhapxuat.service;

import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.model.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeliveryDocketService {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();

    DeliveryDocketService deliveryDocketService=new Retrofit.Builder()
            //http://192.168.0.6:8080/api//
            //https://shoesstation.herokuapp.com/api/
            .baseUrl("https://shoesstation.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DeliveryDocketService.class);
    @GET("deliveryDockets")
    Call<List<DeliveryDocket>> getAllDeliveryDocket();
    @GET("deliveryDockets/{id}/deliveryDocketDetails")
    Call<List<DeliveryDocketDetail>> getAllDeliveryDetailsInDelivery(@Path("id") int id);
    @POST("deliveryDockets")
    Call<DeliveryDocket> addDeliveryDocket(@Body DeliveryDocket deliveryDocket);
    @PUT("deliveryDockets/{id}")
    Call<DeliveryDocket> upDateDeliveryDocket(@Body DeliveryDocket deliveryDocket,@Path("id") int id);
}
