package com.example.quanlynhapxuat.model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private int id;
    private String image;
    private String name, address, phone, email;
    private boolean gender;

    public KhachHang() {
    }

    public KhachHang(int id, String name, boolean gender, String address, String phone, String email) {
        this.id = id;
        this.image = image;
        this.gender = gender;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public KhachHang(int id, String image, boolean gender, String name, String address, String phone, String email) {
        this.id = id;
        this.image = image;
        this.gender = gender;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
