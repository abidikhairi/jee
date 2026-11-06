package com.example.helloworld;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "calc", urlPatterns = {"/calc"})
public class CalcServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String a = req.getParameter("a");
        String b = req.getParameter("b");

        int ia = Integer.parseInt(a);
        int ib = Integer.parseInt(b);

        String op = req.getParameter("op");
        int res;

        switch (op) {
            case "add":
                res = ia + ib;
                break;
            case "sub":
                res = ia - ib;
                break;
            case "div":
                res = ia / ib;
                break;
            case "mul":
                res = ia * ib;
                break;
            default:
                res = -1;
        }

        resp.getWriter().println("Resultat: " + res);
    }
}
