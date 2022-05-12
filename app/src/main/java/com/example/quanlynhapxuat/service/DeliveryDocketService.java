package com.example.quanlynhapxuat.service;

import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeliveryDocketService {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();

    EmployeeService employeeService=new Retrofit.Builder()
            //http://192.168.0.6:8080/api//
            //https://shoesstation.herokuapp.com/api/deliveryDockets
            .baseUrl("https://shoesstation.herokuapp.com/api/deliveryDockets/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EmployeeService.class);
    @GET
    Call<DeliveryDocket> registrationEmployee(@Body DeliveryDocket deliveryDocket);
}
