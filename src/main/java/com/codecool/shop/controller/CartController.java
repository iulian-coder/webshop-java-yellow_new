package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDao = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Map<Product, Integer> cartMap = cartDao.getAll();

        int numberOfProducts = 0;
        double sum = 0;
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            sum += entry.getKey().getPriceDouble() * entry.getValue();
            numberOfProducts += entry.getValue();
        }

        context.setVariable("cartMap", cartMap);
        context.setVariable("totalPrice", sum);
        context.setVariable("totalNumberOfItems", numberOfProducts);
        engine.process("product/cart_page.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao carDao = CartDaoMem.getInstance();


        if (req.getParameter("addToCart") != null) {
            String id = req.getParameter("addToCart");
            carDao.add(Integer.parseInt(id));
        }
        if (req.getParameter("itemId")!=null && req.getParameter("changeQuantity")!=null) {
            String itemIdToChangeQuantity = req.getParameter("itemId");
            String newQuantity = req.getParameter("changeQuantity");
            carDao.changeQuantity(Integer.parseInt(itemIdToChangeQuantity), Integer.parseInt(newQuantity));
        }
        resp.sendRedirect("/");
    }

}
