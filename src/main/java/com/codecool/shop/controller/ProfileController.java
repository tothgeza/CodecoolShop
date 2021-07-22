package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.BillingAddress;
import com.codecool.shop.model.ShippingAddress;
import com.codecool.shop.model.User;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
    ProductDao productDataStore = ProductDaoMem.getInstance();
    ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    ShoppingCartDao shoppingCartDao = ShoppingCartDaoMem.getInstance();
    ProductService productService = new ProductService(productDataStore, productCategoryDataStore, shoppingCartDao);


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("edit/edit.html", context, resp.getWriter());
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bilCountry = req.getParameter("bilCountry");
        String bilCity = req.getParameter("bilCity");
        int bilZip = Integer.parseInt(req.getParameter("bilZip"));
        String bilStreet = req.getParameter("bilStreet");
        int bilHouse = Integer.parseInt(req.getParameter("bilHouse"));
        String shipCountry = req.getParameter("shipCountry");
        String shipCity = req.getParameter("shipCity");
        int shipZip = Integer.parseInt(req.getParameter("shipZip"));
        String shipStreet = req.getParameter("shipStreet");
        int shipHouse = Integer.parseInt(req.getParameter("shipHouse"));
        BillingAddress newBilling = new BillingAddress(bilCountry, bilCity, bilZip, bilStreet, bilHouse);
        ShippingAddress newShipping = new ShippingAddress(shipCountry, shipCity, shipZip, shipStreet, shipHouse);
        HttpSession session = req.getSession();
        String userId;

        if (session.getAttribute("userId") != null){
            userId = (String) session.getAttribute("userId");
            User user = productService.getRegisteredUserById(userId);
            user.setBillingAddress(newBilling);
            System.out.println(user.getBillingAddress().toString());
            user.setShippingAddress(newShipping);
            System.out.println(user.getShippingAddress().toString());
        }
        resp.sendRedirect("/");
    }
}
