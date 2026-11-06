package com.example.calculatrice;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** POST /history : clears the calculation history, then goes back to the calculator. */
@WebServlet(name = "history", urlPatterns = "/history")
public class HistoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(CalculatriceServlet.HISTORY);
        }
        resp.sendRedirect(req.getContextPath() + "/calc");
    }
}
