package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CartDao {
    void add(int id) throws SQLException;
    void remove(int id) throws SQLException;
    void removeProduct(int id) throws SQLException;
    int get(int id) throws SQLException;
    void changeQuantity(int i, int id);
    List <Cart> getAll() throws SQLException;
    Map<Product, Integer> getAllDaoMem();

    Cart find(int id) throws SQLException;
    void addNewCart(int user_id) throws SQLException;
    Cart findByUserId(int userId) throws SQLException;
    void removeCart(int id) throws SQLException;


}
