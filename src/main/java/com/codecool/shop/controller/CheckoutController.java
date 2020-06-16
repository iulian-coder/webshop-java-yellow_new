package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.WebConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext() );

        engine.process("/checkoutPage.html", context,resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDao = null;
        Order order = null;
        CartDao cartDao = null;
        UserDao userDao=null;
        try {
            orderDao = OrderDaoJDBC.getInstance();
            cartDao = CartDaoJDBC.getInstance();
            userDao = UserDaoJDBC.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(req.getParameter("firstName") != null &&
                req.getParameter("lastName") != null &&
                req.getParameter("phone") != null &&
                req.getParameter("email")!= null &&
                req.getParameter("address") != null &&
                req.getParameter("address2") !=null) {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String billingAddress = req.getParameter("address");
            String shippingAddress = req.getParameter("address2");
            float totalPrice = Float.parseFloat(null);
            try {
                order = new Order(cartDao.find(1).getId(), userDao.find(1).getId(), firstName, lastName, phone, email, billingAddress, shippingAddress, totalPrice);
            }catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(order.getFirstName());
        }

        try {
            orderDao.add(order);
            System.out.println("New order added");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/cart/payment");

    }
}
