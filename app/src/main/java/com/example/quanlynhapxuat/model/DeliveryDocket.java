package com.example.quanlynhapxuat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryDocket {
    private int id;
    private int employeeId;
    private int customerId;
    private int status;
    private String createdAt;
    List<DeliveryDocketDetail> deliveryDocketDetails=new ArrayList<>();

    public DeliveryDocket(int id, int employeeId, int customerId, int status, String createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.status = status;
        createdAt = createdAt;
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

    public List<DeliveryDocketDetail> getDeliveryDocketDetails() {
        return deliveryDocketDetails;
    }

    public void setDeliveryDocketDetails(List<DeliveryDocketDetail> deliveryDocketDetails) {
        this.deliveryDocketDetails = deliveryDocketDetails;
    }
}
