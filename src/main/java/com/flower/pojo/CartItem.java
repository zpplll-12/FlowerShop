package com.flower.pojo;
public class CartItem {
    private Flower flower;
    private int num;
    private double subTotal;

    public CartItem(Flower flower, int num) {
        this.flower = flower;this.num = num;this.subTotal = flower.getPrice() * num;
    }

    public Flower getFlower() {return flower;}
    public void setFlower(Flower flower) {
        this.flower = flower;this.subTotal = flower.getPrice() * this.num;
    }
    public int getNum() {return num;}
    public void setNum(int num) {
        this.num = num;this.subTotal = this.flower.getPrice() * num;
    }
    public double getSubTotal() {return subTotal;}
}