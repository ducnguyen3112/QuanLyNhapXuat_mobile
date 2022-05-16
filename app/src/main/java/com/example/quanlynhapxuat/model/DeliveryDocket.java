package com.example.quanlynhapxuat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryDocket implements Serializable {
    private int id;
    private int employeeId;
    private int customerId;
    private int status;
    private String createdAt;
    @SerializedName("deliveryDocketDetails")
    List<DeliveryDocketDetail> deliveryDocketDetails;

    public DeliveryDocket(int id, int employeeId, int customerId, int status, String createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.status = status;
    }

    public DeliveryDocket() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotal() {
        float sum = 0;
        for (DeliveryDocketDetail item : deliveryDocketDetails) {
            sum += item.getPrice();
        }
        return sum;
    }

    public List<DeliveryDocketDetail> getDeliveryDocketDetails() {
        return deliveryDocketDetails;
    }

    public void setDeliveryDocketDetails(List<DeliveryDocketDetail> deliveryDocketDetails) {
        this.deliveryDocketDetails = deliveryDocketDetails;
    }
}