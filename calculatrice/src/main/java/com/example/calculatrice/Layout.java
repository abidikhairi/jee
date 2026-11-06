package com.example.calculatrice;

/** Small helper that wraps page content in the shared HTML shell (Tailwind stylesheet included). */
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
                  <title>Calculatrice</title>
                  <link rel="stylesheet" href="%s/css/styles.css">
                </head>
                <body class="min-h-screen bg-gradient-to-br from-indigo-900 via-indigo-700 to-pink-600 flex items-center justify-center p-4 font-sans">
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
}
