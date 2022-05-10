package com.example.quanlynhapxuat.model;

public class ReceivedDocket {
    private int id;
    private String created_at;
    private int employee_id;
    private int status;
    private String supplier_name;

    public ReceivedDocket() {
    }

    public ReceivedDocket(int id, String created_at, int employee_id, int status, String supplier_name) {
        this.id = id;
        this.created_at = created_at;
        this.employee_id = employee_id;
        this.status = status;
        this.supplier_name = supplier_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }
}
