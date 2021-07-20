package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.config.Util;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;
import com.codecool.shop.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = {"/success"})
public class SuccessController  extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String from = "cc.fourhorseman";
        String pass = "Qawsed01";

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDao shoppingCartDao = ShoppingCartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, shoppingCartDao);

        HttpSession userSession = request.getSession();
        String userId = userSession.getId();

        User user = productService.getUserById(userId);

        String userEmail = user.getEmail();
       // String[] to = { userEmail }; // list of recipient email addresses
        String[] to = { userEmail,"sallcsa.csaba9@gmail.com",
//                "anett.fejes@codecool.com",
//                "imre.lindi@codecool.com",
//                "turi.krisztina1999@gmail.com",
//                "class.msc.2020",
//                "ferenczi.gergo.87@gmail.com",
//                "horvathbalazs9104@gmail.com",
//                "ricsi.keri@gmail.com",
//                "pelyheroland22@gmail.com",
//                "vedres.david.adam@gmail.com",
//                "kriszdemarco@gmail.com",
                "toth.geza.0425@gmail.com",
//                "spiczmuller.richard@gmail.com",
//                "halaszpeter9814@gmail.com",
//                "rauszka94@gmail.com",
//                "jozsefbabcsan1@gmail.com",
                "sallcsa.csaba8@gmail.com",
//                "kalydybarnabas99@gmail.com",
//                "papaimate166@gmail.com",
//                "kbalazs93@gmail.com",
//                "poool990224@gmail.com",
//                "d.aldan12.ad@gmail.com",
//                "fazekasdav@gmail.com",
//                "attilaaa1313@gmail.com",
                "ptcadam@gmail.com"};
//                "beatrix.szabo.sc@gmail.com",
//                "siposm17@gmail.com" };
        String subject = "Success Order!";

        List<Order> orders = productService.getOrderByUserId(userId);
        Order order = orders.get(0);
        ShoppingCart shoppingCart = order.getShoppingCart();


        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

//            String filepath = System.getProperty("user.dir") + "/src/main/webapp/templates/emailtemplate/emailtemplate.html";

//            StringWriter writer = new StringWriter();
//            IOUtils.copy(new FileInputStream(filepath), writer);
            Util util = new Util();
            String email = util.generateMailContent(shoppingCart, user, engine);

            msg.setContent(email, "text/html");
            msg.setSubject(subject); // HERE WE CAN SEE THE MSG
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }

        engine.process("payment/success.html", context, response.getWriter());


    }
}