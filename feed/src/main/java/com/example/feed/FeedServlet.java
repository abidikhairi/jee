package com.example.feed;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * GET  /feed -> the timeline and the "new post" form
 * POST /feed -> publishes a post, then redirects to GET (Post/Redirect/Get)
 */
@WebServlet(name = "feed", urlPatterns = "/feed")
public class FeedServlet extends HttpServlet {

    static final String USER = "user";
    static final String ERROR = "error";

    private FeedStore store() {
        return (FeedStore) getServletContext().getAttribute(FeedStore.KEY);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String user = (String) session.getAttribute(USER);
        String error = (String) session.getAttribute(ERROR);
        session.removeAttribute(ERROR);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().print(Layout.page(req.getContextPath(), render(req.getContextPath(), user, error)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        String author = clean(req.getParameter("author"));
        String text = clean(req.getParameter("text"));

        if (author.isEmpty() || author.length() > 20) {
            session.setAttribute(ERROR, "Choisissez un pseudo de 1 à 20 caractères.");
        } else if (text.isEmpty()) {
            session.setAttribute(ERROR, "Le message ne peut pas être vide.");
        } else if (text.length() > FeedStore.MAX_LENGTH) {
            session.setAttribute(ERROR, "Message trop long (" + FeedStore.MAX_LENGTH + " caractères maximum).");
        } else {
            session.setAttribute(USER, author);
            store().add(author, text);
        }
        resp.sendRedirect(req.getContextPath() + "/feed");
    }

    private static String clean(String s) {
        return s == null ? "" : s.strip();
    }

    private String render(String ctx, String user, String error) {
        String field = "w-full rounded-lg border border-slate-300 px-3 py-2 "
                + "focus:border-sky-500 focus:outline-none focus:ring-2 focus:ring-sky-200";

        StringBuilder posts = new StringBuilder();
        for (Post p : store().all()) {
            boolean mine = p.author().equals(user);
            posts.append("""
                    <article class="rounded-2xl bg-white p-4 shadow-sm">
                      <div class="flex items-center gap-3">
                        <div class="flex h-10 w-10 items-center justify-center rounded-full bg-sky-100 font-bold uppercase text-sky-700">%s</div>
                        <div class="text-sm"><span class="font-semibold">@%s</span> <span class="text-slate-400">· %s</span></div>
                      </div>
                      <p class="mt-3 whitespace-pre-wrap break-words">%s</p>
                      <div class="mt-3 flex items-center gap-4 text-sm">
                        <form method="post" action="%s/like"><input type="hidden" name="id" value="%d">
                          <button class="text-slate-500 transition hover:text-pink-600">♥ %d</button></form>
                        %s
                      </div>
                    </article>
                    """.formatted(
                    Layout.escape(p.author().substring(0, 1)), Layout.escape(p.author()), Layout.ago(p.createdAt()),
                    Layout.escape(p.text()), ctx, p.id(), p.likes(),
                    mine ? "<form method=\"post\" action=\"" + ctx + "/delete\"><input type=\"hidden\" name=\"id\" value=\"" + p.id()
                            + "\"><button class=\"text-slate-400 hover:text-red-600\">Supprimer</button></form>" : ""));
        }

        String errorBlock = error == null ? ""
                : "<div class=\"mb-3 rounded-lg border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700\">"
                + Layout.escape(error) + "</div>";

        return """
                <header class="bg-sky-600 py-4 text-white shadow">
                  <div class="mx-auto flex max-w-xl items-baseline justify-between px-4">
                    <h1 class="text-2xl font-bold">Feed</h1>
                    <span class="text-sm text-sky-100">Jakarta EE 11 · Servlets · Tailwind CSS</span>
                  </div>
                </header>
                <main class="mx-auto max-w-xl space-y-4 px-4 py-6">
                  <form method="post" action="%s/feed" class="space-y-3 rounded-2xl bg-white p-4 shadow-sm">
                    %s
                    <input name="author" value="%s" maxlength="20" required placeholder="Votre pseudo" class="%s">
                    <textarea name="text" rows="3" maxlength="%d" required placeholder="Quoi de neuf ?" class="%s"></textarea>
                    <div class="flex items-center justify-between">
                      <span class="text-xs text-slate-400">%d caractères maximum</span>
                      <button class="rounded-full bg-sky-600 px-5 py-2 font-semibold text-white transition hover:bg-sky-700 active:scale-95">Publier</button>
                    </div>
                  </form>
                  %s
                </main>
                """.formatted(ctx, errorBlock, Layout.escape(user), field, FeedStore.MAX_LENGTH, field,
                FeedStore.MAX_LENGTH, posts);
    }
}
