package com.example.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A minimal Jakarta Servlet (Jakarta Servlet 6.1, part of Jakarta EE 11).
 *
 * <p>The @WebServlet annotation maps this class to the URL {@code /hello} so no
 * {@code web.xml} entry is required. The application server instantiates the
 * servlet and calls {@link #doGet} for every HTTP GET request.</p>
 */
@WebServlet(name = "hello", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("Name not found");
            return;
        }
        response.setContentType("text/html");
        response.getWriter().println("Hello, " + name);
    }
}
