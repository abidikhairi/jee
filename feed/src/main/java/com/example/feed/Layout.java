package com.example.feed;

import java.time.Duration;
import java.time.Instant;

/** Shared HTML shell and small formatting helpers. */
final class Layout {

    private Layout() {
    }

    static String page(String contextPath, String body) {
        return """
                <!doctype html>
                <html lang="fr">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Feed</title>
                  <link rel="stylesheet" href="%s/css/styles.css">
                </head>
                <body class="min-h-screen bg-slate-100 font-sans text-slate-800">
                %s
                </body>
                </html>
                """.formatted(contextPath, body);
    }

    /** Escape user-controlled text before writing it into HTML (prevents XSS). */
    static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    static String ago(Instant t) {
        long s = Duration.between(t, Instant.now()).getSeconds();
        if (s < 60) return "à l'instant";
        if (s < 3600) return "il y a " + (s / 60) + " min";
        if (s < 86400) return "il y a " + (s / 3600) + " h";
        return "il y a " + (s / 86400) + " j";
    }
}
