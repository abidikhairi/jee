package com.example.feed;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/** Runs once when the web application starts: creates the shared store. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FeedStore store = new FeedStore();
        store.add("jakarta", "Bienvenue sur le fil ! Écrivez votre premier message ci-dessus.");
        store.add("servlet", "Une servlet = une classe Java qui répond à des requêtes HTTP.");
        sce.getServletContext().setAttribute(FeedStore.KEY, store);
    }
}
