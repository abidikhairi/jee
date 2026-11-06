package com.example.feed;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** POST /like?id=N : adds a like, then goes back to the feed. */
@WebServlet(name = "like", urlPatterns = "/like")
public class LikeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            ((FeedStore) getServletContext().getAttribute(FeedStore.KEY)).like(id);
        } catch (NumberFormatException ignored) {
            // bad id: nothing to like
        }
        resp.sendRedirect(req.getContextPath() + "/feed");
    }
}
