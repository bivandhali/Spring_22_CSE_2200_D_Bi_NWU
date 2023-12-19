package com.example.grocergrid.models;

public class OrderAdminModel {

    String  email;
    String phone;

    String name;


    String productName;
    String productPrice;
    String TotalQuantity;
    String Time;
    String Date;
    int TotalPrice;

    int totalStock;
    String documentId;


    public OrderAdminModel() {
    }

    public OrderAdminModel(String productName, String productPrice, String totalQuantity, String time, String date, int totalPrice, int totalStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        TotalQuantity = totalQuantity;
        Time = time;
        Date = date;
        TotalPrice = totalPrice;
        this.totalStock = totalStock;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }
}