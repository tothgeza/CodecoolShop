package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/admin")
public class AdminController extends HttpServlet{
    ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //prepare thymeleaf
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        //prepare the regex pattern and the directory to read the files
        Pattern regexPattern = Pattern.compile("(?<=\\{)[^}]*(?=\\})");
        File dir = new File("AdminLog/");

        //for each matched pattern put it in a list, then ath the end another list to separate each user
        for (File file : dir.listFiles()) {
            ArrayList<String> allMatches = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                Matcher m = regexPattern.matcher(st);
                while (m.find()) {
                    allMatches.add(m.group());
                }
            }
            listOLists.add(allMatches);
        }
        context.setVariable("logs", listOLists);
        engine.process("admin/admin.html", context, response.getWriter());
    }

}
