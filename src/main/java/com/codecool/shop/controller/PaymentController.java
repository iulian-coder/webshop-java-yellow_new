package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get session if it exists
        HttpSession session = req.getSession(false);

        String sessionUsername = null;

        if(session!=null) {
            sessionUsername = (String)session.getAttribute("username");
        }

        CartDao cartDao = null;
        try {
            cartDao = CartDaoJDBC.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("username", sessionUsername);

        List<Cart> templist = new ArrayList<>();
        try {
            templist = cartDao.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        float sum = 0;
        int numberOfProducts = 0;
        for (int i = 0; i < templist.size(); i++) {
            sum += templist.get(i).getTotal();
            numberOfProducts += templist.get(i).getQuantity();
        }
        context.setVariable("cartList", templist);
        context.setVariable("totalPrice", sum);
        context.setVariable("totalNumberOfItems", numberOfProducts);

        engine.process("paymentPage.html", context, resp.getWriter());

    }
}
