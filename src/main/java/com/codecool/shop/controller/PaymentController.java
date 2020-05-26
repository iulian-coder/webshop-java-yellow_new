package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        un status de order confirmet pe get
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("paymentPage.html", context, resp.getWriter());

    }
}
