package com.flower.pojo;
public class Flower {
    private int flowerId;
    private String flowerName;
    private double price;
    private String category;
    private String description;
    private String imgUrl;
    private int stock;

    public Flower() {}
    public Flower(String flowerName, double price, String category, String description) {
        this.flowerName = flowerName;this.price = price;this.category = category;this.description = description;
    }

    public int getFlowerId() {return flowerId;}
    public void setFlowerId(int flowerId) {this.flowerId = flowerId;}
    public String getFlowerName() {return flowerName;}
    public void setFlowerName(String flowerName) {this.flowerName = flowerName;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getImgUrl() {return imgUrl;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
}