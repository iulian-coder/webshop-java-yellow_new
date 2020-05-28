package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(urlPatterns = "/cart/payment/confirmation")
public class ConfirmationController extends HttpServlet {

    CartDao cartDao = CartDaoMem.getInstance();
    private String statusPayment = "no";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ccName = req.getParameter("cc-name");
        System.out.println(ccName);
        if (ccName.equals("b")){
            statusPayment ="confirmed";
        } else {
            statusPayment = "no";
        }
        resp.sendRedirect("/cart/payment/confirmation");
    }

    private void writeJson(){
        JSONObject orderDetails = new JSONObject();

        //Add data to json file
        orderDetails.putAll(cartDao.getAll());
        orderDetails.put("statusPayment", statusPayment);

        //Write JSON file
        try (FileWriter file = new FileWriter("orderDetails.json")) {

            file.write(orderDetails.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendEmail(){
//        TODO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Map<Product, Integer> cartMap = cartDao.getAll();

        int numberOfProducts = 0;
        double sum = 0;

        for(Map.Entry<Product, Integer> entry : cartMap.entrySet()){
            sum += entry.getKey().getPriceDouble() * entry.getValue();
            numberOfProducts += entry.getValue();
        }

        context.setVariable("cartMap", cartMap);
        context.setVariable("totalPrice", sum);
        context.setVariable("totalNumberOfItems", numberOfProducts);

        writeJson();
        context.setVariable("paymentMessage", statusPayment);

        engine.process("confirmationPage.html", context, resp.getWriter());

    }

}
