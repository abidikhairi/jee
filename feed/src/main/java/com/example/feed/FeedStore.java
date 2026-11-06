package com.example.feed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory storage, created once at startup and shared through the ServletContext.
 * (Tomcat has no JPA: a real application would put a database behind this class.)
 */
public final class FeedStore {

    static final String KEY = "feedStore";
    static final int MAX_LENGTH = 280;

    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicLong ids = new AtomicLong();

    public Post add(String author, String text) {
        Post p = new Post(ids.incrementAndGet(), author, text);
        posts.add(0, p); // newest first
        return p;
    }

    public List<Post> all() {
        return new ArrayList<>(posts);
    }

    public void like(long id) {
        for (Post p : posts) {
            if (p.id() == id) {
                p.like();
                return;
            }
        }
    }

    /** Deletes the post only when {@code user} is its author. */
    public boolean delete(long id, String user) {
        return posts.removeIf(p -> p.id() == id && p.author().equals(user));
    }
}
