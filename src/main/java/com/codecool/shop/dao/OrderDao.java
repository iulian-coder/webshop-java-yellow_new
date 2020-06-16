package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void add(Order order) throws SQLException;
//    Order find(int id) throws SQLException;
//    void remove(int id) throws SQLException;
//
//    List<Order> getAll() throws SQLException;
}
