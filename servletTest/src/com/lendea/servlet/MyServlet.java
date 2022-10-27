package com.lendea.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lendea
 * @date 2022/9/22 13:43
 */
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Myservlet 在处理get()请求...");
        final PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        writer.println("<strong>My Servlet!!!</strong><br>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Myservlet 在处理Post请求...");
        final PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        writer.println("<strong>My Servlet!!!</strong><br>");
    }
}
