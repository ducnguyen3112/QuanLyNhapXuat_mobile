package com.example.quanlynhapxuat.model;

public class DeliveryDocketDetail {

    private int id;
    private int quantity;
    private int price;
    private int deliveryDocketId;
    private int productId;

    public DeliveryDocketDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDeliveryDocketId() {
        return deliveryDocketId;
    }

    public void setDeliveryDocketId(int deliveryDocketId) {
        this.deliveryDocketId = deliveryDocketId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
