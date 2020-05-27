package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends BaseModel{
    private List<Product> cart;

    public Cart(String name, List<Product> cart) {
        super(name);
        this.cart = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart=" + cart +
                '}';
    }
}
