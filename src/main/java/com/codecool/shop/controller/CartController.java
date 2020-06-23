package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.*;
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
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //It's a LIST
        CartDao cartDao = null;
        List<Cart> templist = new ArrayList<>();
        float sum = 0;
        int numberOfProducts = 0;

        try {
            cartDao = CartDaoJDBC.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //get session if it exists
        HttpSession session = req.getSession(false);

        String sessionUsername = null;

        if (session != null) {
            sessionUsername = (String) session.getAttribute("username");
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("username", sessionUsername);


        try {
            templist = cartDao.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (req.getSession().getAttribute("username") != null) {
            sum = ((CartDaoJDBC) cartDao).productsTotalPrice(templist);
            numberOfProducts = ((CartDaoJDBC) cartDao).totalNumberOfProductsInCart(templist);
        }else{

            CartDaoMem cart = (CartDaoMem) req.getSession().getAttribute("cartMem");
            if (cart != null) {
                sum = cart.productsTotalPrice(cart);
                numberOfProducts = cart.totalNumberOfProductsInCart(cart);
            }

        }




        context.setVariable("cartList", templist);
        context.setVariable("totalPrice", sum);
        context.setVariable("totalNumberOfItems", numberOfProducts);
        engine.process("product/cart_page.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("username") != null) {

            UserDao user = null;
            CartDao cartDao = null;
            try {
                user = UserDaoJDBC.getInstance();
                cartDao = CartDaoJDBC.getInstance();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (req.getParameter("addToCart") != null) {
                    int id = Integer.parseInt(req.getParameter("addToCart"));
                    int userId = user.getUserByUsername((String) req.getSession().getAttribute("username")).getId();
                    int cartId = cartDao.addNewCart(userId);
                    cartDao.add(id, cartId);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            CartDao cart = (CartDao) req.getSession().getAttribute("cartMem");
            if (cart == null) {
                cart = new CartDaoMem();
                req.getSession().setAttribute("cartMem", cart);
            }
            try {
                if (req.getParameter("addToCart") != null) {
                    int id = Integer.parseInt(req.getParameter("addToCart"));
                    cart.add(id, 0);
                    Map<Product, Integer> cartMap = null;
                    cartMap = cart.getAllDaoMem();
                    System.out.println("Aici o sa inceapa for");
                    for (Map.Entry<Product, Integer> entry : cartMap.entrySet())
                         {
                             System.out.println(entry.getKey().getName()+": "+entry.getValue());

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }





//        try {
//            if (req.getParameter("itemId") != null && req.getParameter("changeQuantity") != null) {
//                int itemIdToChangeQuantity = Integer.parseInt(req.getParameter("itemId"));
//                int newQuantity = Integer.parseInt(req.getParameter("changeQuantity"));
//
//

//
//
//                if (newQuantity == 0) {
//                    carDao.removeProduct(itemIdToChangeQuantity);
//                }
//                if (newQuantity > 0) {
//                    if (newQuantity >= carDao.get(itemIdToChangeQuantity)) {
//                        int quantityDifference = newQuantity - carDao.get(itemIdToChangeQuantity);
//
//                        for (int i = 1; i <= quantityDifference; i++) {
//                            carDao.add(itemIdToChangeQuantity);
//                        }
//                    }else{
//                        carDao.removeProduct(itemIdToChangeQuantity);
//                        int quantityDifference = carDao.get(itemIdToChangeQuantity)-newQuantity;
//                        for (int i = 1; i <= quantityDifference; i++) {
//                            carDao.add(itemIdToChangeQuantity);
//                        }
//                    }
//
//                }
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        resp.sendRedirect("/");
   }

}
