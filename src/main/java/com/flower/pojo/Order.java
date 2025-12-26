package com.flower.pojo;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Date orderTime;
    private double totalPrice;
    private String orderStatus;
    private String receiver;
    private String phone;
    private String address;
    private List<OrderItem> itemList;

    public Order() {}

    public int getOrderId() {return orderId;}
    public void setOrderId(int orderId) {this.orderId = orderId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public Date getOrderTime() {return orderTime;}
    public void setOrderTime(Date orderTime) {this.orderTime = orderTime;}
    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public String getOrderStatus() {return orderStatus;}
    public void setOrderStatus(String orderStatus) {this.orderStatus = orderStatus;}
    public String getReceiver() {return receiver;}
    public void setReceiver(String receiver) {this.receiver = receiver;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public List<OrderItem> getItemList() {return itemList;}
    public void setItemList(List<OrderItem> itemList) {this.itemList = itemList;}
}