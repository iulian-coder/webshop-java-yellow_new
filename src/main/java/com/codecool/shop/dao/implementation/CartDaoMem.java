package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.*;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public Map<Product, Integer> getCart() {
        return this.cart;
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(int id) {
        ProductDaoMem productsList = ProductDaoMem.getInstance();
        Product product = productsList.find(id);
            if(cart.containsKey(product)){
                cart.put(product, cart.get(product) + 1);
            }else{
                cart.put(product, 1);
            }
    }

//    @Override
//    public Product find(int id) {
//        return cart.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
//    }
//
//    @Override
//    public void remove(int id) {
//        cart.remove(find(id));
//    }
//
    @Override
    public Map<Product, Integer> getAll() {
        return this.cart;
    }



    public int getTotalNumberOfItems(){
        return cart.size();
    }



}
