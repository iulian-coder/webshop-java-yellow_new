package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.codecool.shop.model.ProductCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplier = SupplierDaoMem.getInstance();

        ProductDao productDataStore = null;
        ProductCategoryDao productCategoryDataStore = null;
        SupplierDao supplier = null;

        try {
            productDataStore = ProductDaoJDBC.getInstance();
            productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
            supplier = SupplierDaoJDBC.getInstance();
        }catch(SQLException e){e.getStackTrace();}

        CartDao cartDao = CartDaoMem.getInstance();


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        Map<Product, Integer> cartMap = cartDao.getAll();

        int numberOfProducts = 0;
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            numberOfProducts += entry.getValue();
        }


        WebContext context = new WebContext(req, resp, req.getServletContext());


        int categoryId=2;
        int supplierId=3;

        if (req.getParameter("category")!=null) {
            categoryId=Integer.parseInt(req.getParameter("category"));
            supplierId=0;
        }

        if (req.getParameter("supplier")!=null) {
            supplierId=Integer.parseInt(req.getParameter("supplier"));

        }
        context.setVariable("totalNumberOfItems", numberOfProducts);
        displayProducts(context, engine, resp, productCategoryDataStore,productDataStore, categoryId, supplierId, supplier);

    }


    private void displayProducts(WebContext context, TemplateEngine engine, HttpServletResponse resp, ProductCategoryDao productCategoryDataStore, ProductDao productDataStore, int categoryId, int supplierId, SupplierDao supplier) throws IOException {
        context.setVariable("category", productCategoryDataStore.find(categoryId));
        if (supplierId!=0) {
            try {
                context.setVariable("supplier", supplier.find(supplierId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        if (supplierId!=0){
            try {
                context.setVariable("products", productDataStore.getBy(supplier.find(supplierId)));
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
        engine.process("product/index.html", context, resp.getWriter());
    }

}
