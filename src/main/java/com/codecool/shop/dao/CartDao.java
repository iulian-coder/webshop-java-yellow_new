package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;
import java.util.Map;

public interface CartDao {
    void add(int id);
//    Product find(int id);
    void remove(int id);
    void changeQuantity(int i, int id);
//
    Map<Product, Integer> getAll();
}
