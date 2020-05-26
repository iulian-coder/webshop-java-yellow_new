package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends BaseModel{
    private List<Product> cart;

    public Cart(String name, List<Product> cart) {
        super(name);
        this.cart = new ArrayList<>();
    }

    public List<Product> getCart() {
        return this.cart;
    }

    public void addToCart(Product product){
        cart.add(product);
    }

    public void removeFromCart(Product product){
        cart.remove(product);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart=" + cart +
                '}';
    }
}
