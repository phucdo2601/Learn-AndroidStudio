package com.phucdn.learnaddtocartwithmssqlb1.model;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String cartId;
    private int itemId;
    private String itemName;
    private String image;
    private float price;
    private int quantity;
    private float totalPrice;

    public CartModel() {
    }

    public CartModel(String cartId, int itemId, String itemName, String image, float price, int quantity, float totalPrice) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
