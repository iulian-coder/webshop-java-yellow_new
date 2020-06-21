package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.PaymentServices;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/payment/review-payment"})
public class ReviewPaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String paymentId = req.getParameter("paymentId");
        String payerId = req.getParameter("PayerID");

        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.getPaymentDetails(paymentId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

            //Write the website template
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());

            context.setVariable( "paymentId",paymentId);
            context.setVariable("payerId",payerId);
            context.setVariable("payerInfo", payerInfo);
            context.setVariable("transaction", transaction);
            context.setVariable("shippingAddress", shippingAddress);

            engine.process("/reviewpayment.html", context, resp.getWriter());

        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            req.setAttribute("errorMessage", ex.getMessage());
            req.getRequestDispatcher("/error").forward(req, resp);
        }

    }
}
