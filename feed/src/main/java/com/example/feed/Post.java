package com.example.feed;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/** A single message of the feed. Shared between request threads, so mutable state is atomic. */
public final class Post {

    private final long id;
    private final String author;
    private final String text;
    private final Instant createdAt;
    private final AtomicInteger likes = new AtomicInteger();

    public Post(long id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.createdAt = Instant.now();
    }

    public long id() { return id; }
    public String author() { return author; }
    public String text() { return text; }
    public Instant createdAt() { return createdAt; }
    public int likes() { return likes.get(); }
    public int like() { return likes.incrementAndGet(); }
}
