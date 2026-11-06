package com.example.feed;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** POST /delete?id=N : removes a post, only if the current user wrote it. */
@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String user = session == null ? null : (String) session.getAttribute(FeedServlet.USER);
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            long id = Long.parseLong(req.getParameter("id"));
            ((FeedStore) getServletContext().getAttribute(FeedStore.KEY)).delete(id, user);
        } catch (NumberFormatException ignored) {
            // bad id: nothing to delete
        }
        resp.sendRedirect(req.getContextPath() + "/feed");
    }
}
