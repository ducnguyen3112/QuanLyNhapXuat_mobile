package com.example.quanlynhapxuat.model;

public class ReceivedDocketDetail {
    private int id;
    private int received_docket_id;
    private int product_id;
    private int quantity;
    private int price;

    public ReceivedDocketDetail() {
    }

    public ReceivedDocketDetail(int id, int received_docket_id, int product_id, int quantity, int price) {
        this.id = id;
        this.received_docket_id = received_docket_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceived_docket_id() {
        return received_docket_id;
    }

    public void setReceived_docket_id(int received_docket_id) {
        this.received_docket_id = received_docket_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
