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
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserDao userDao = null;
        OrderDao orderDao = null;
        User userTemp = null;
        CartDao cartDao = null;

        try {
            userDao = UserDaoJDBC.getInstance();
            orderDao = OrderDaoJDBC.getInstance();
            cartDao = CartDaoJDBC.getInstance();
        } catch (SQLException e){
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        String sessionUsername = (String)session.getAttribute("username");

//        TODO refactoring
        String firstName = "None";
        String lastName = "None";
        String username = sessionUsername;
        String email = "None";
        String phone = req.getParameter("phone");
        String password = "None";
        String billingAddress = req.getParameter("address");
        String shippingAddress = req.getParameter("address2");

        try {
            userTemp = userDao.getUserbyUsername(sessionUsername);
            float totalPrice = cartDao.find(1).getTotal();

            Order order = new Order(1, userTemp.getId(),"pending",totalPrice);
            orderDao.add(order);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User user =new User(username, password, firstName, lastName, email, phone, billingAddress, shippingAddress);



        try {
            userDao.update(user);
        } catch (SQLException e){
            e.printStackTrace();
        }

        resp.sendRedirect("/cart/payment");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext() );

        HttpSession session = req.getSession();
        String sessionUsername = (String)session.getAttribute("username");

        //Check if the user is login
        if (sessionUsername == null){
            resp.sendRedirect("/login");
        }else {
            engine.process("/checkoutPage.html", context, resp.getWriter());
        }
    }
}
