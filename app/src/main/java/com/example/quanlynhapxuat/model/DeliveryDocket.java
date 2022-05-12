package com.example.quanlynhapxuat.model;

import java.util.Date;

public class DeliveryDocket {
    private int id;
    private Date CreatedAt;
    private int status;

    public DeliveryDocket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
