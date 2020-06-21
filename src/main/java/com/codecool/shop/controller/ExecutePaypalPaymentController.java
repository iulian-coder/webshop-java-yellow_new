package com.codecool.shop.controller;

import com.codecool.shop.model.PaymentServices;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/payment/execute_payment"})
public class ExecutePaypalPaymentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentId = req.getParameter("paymentId");
        String payerId = req.getParameter("PayerID");

        try {
            PaymentServices paymentServices = new PaymentServices();
            paymentServices.executePayment(paymentId,payerId);

            //TODO: make a thank you page
//              :clear the cart
            resp.sendRedirect("/");

        }catch (PayPalRESTException ex){
            ex.printStackTrace();
            req.setAttribute("errorMessage", ex.getMessage());
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
