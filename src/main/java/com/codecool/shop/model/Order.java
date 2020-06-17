package com.codecool.shop.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class Order {
    private int id;
    private Data creationData;
    private Cart cart;
    private User user;
    private int cartId;
    private int userId;
    private String status;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String billingAddress;
    private String shippingAddress;
    private float total;

    public Order(int cartId, int userId, String firstName, String lastName, String phone, String email, String billingAddress, String shippingAdress, float total) {
        this.cartId = cartId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAdress;
        this.total = total;
    }

    public int getCartId() {

        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Data getCreationData() {
        return creationData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAdress() {
        return shippingAddress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAddress = shippingAdress;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
