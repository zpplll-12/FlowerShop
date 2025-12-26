package com.flower.pojo;
public class OrderItem {
    private int itemId;
    private int orderId;
    private int flowerId;
    private int num;
    private double price;
    private Flower flower;

    public OrderItem() {}

    public int getItemId() {return itemId;}
    public void setItemId(int itemId) {this.itemId = itemId;}
    public int getOrderId() {return orderId;}
    public void setOrderId(int orderId) {this.orderId = orderId;}
    public int getFlowerId() {return flowerId;}
    public void setFlowerId(int flowerId) {this.flowerId = flowerId;}
    public int getNum() {return num;}
    public void setNum(int num) {this.num = num;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public Flower getFlower() {return flower;}
    public void setFlower(Flower flower) {this.flower = flower;}
}