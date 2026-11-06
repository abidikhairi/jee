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

    private static final String[] AVATAR_COLORS = {
            "bg-sky-600", "bg-emerald-600", "bg-violet-600", "bg-pink-600", "bg-amber-600", "bg-rose-600", "bg-teal-600"
    };

    private static String avatar(String name, String size) {
        String color = AVATAR_COLORS[Math.floorMod(name.hashCode(), AVATAR_COLORS.length)];
        return "<div class=\"flex shrink-0 items-center justify-center rounded-full font-bold uppercase text-white "
                + size + " " + color + "\">" + Layout.escape(name.substring(0, 1)) + "</div>";
    }

    private static String navItem(String path, String label, boolean active) {
        return "<a href=\"#\" class=\"flex w-fit items-center gap-5 rounded-full p-3 text-xl transition hover:bg-zinc-900 "
                + (active ? "font-bold" : "") + "\">" + Icons.svg(path, "h-7 w-7", false)
                + "<span class=\"hidden xl:inline\">" + label + "</span></a>";
    }

    private String render(String ctx, String user, String error) {
        var all = store().all();

        StringBuilder posts = new StringBuilder();
        for (Post p : all) {
            boolean mine = p.author().equals(user);
            boolean liked = p.likes() > 0;
            String handle = Layout.escape(p.author());
            posts.append("""
                    <article class="flex gap-3 border-b border-zinc-800 px-4 py-3 transition hover:bg-zinc-950">
                      %s
                      <div class="min-w-0 flex-1">
                        <div class="flex items-center gap-1 text-[15px]">
                          <span class="truncate font-bold">%s</span>
                          <span class="truncate text-zinc-500">@%s · %s</span>
                        </div>
                        <p class="whitespace-pre-wrap break-words text-[15px] leading-snug">%s</p>
                        <div class="mt-2 flex items-center gap-8 text-[13px] text-zinc-500">
                          <form method="post" action="%s/like">
                            <input type="hidden" name="id" value="%d">
                            <button class="group flex items-center gap-1.5 transition %s" title="J'aime">
                              <span class="rounded-full p-1.5 group-hover:bg-pink-600/10">%s</span>%s
                            </button>
                          </form>
                          %s
                        </div>
                      </div>
                    </article>
                    """.formatted(
                    avatar(p.author(), "h-10 w-10"), handle, handle, Layout.ago(p.createdAt()),
                    Layout.escape(p.text()), ctx, p.id(),
                    liked ? "text-pink-600" : "hover:text-pink-600",
                    Icons.svg(Icons.HEART, "h-[18px] w-[18px]", liked),
                    liked ? "<span>" + p.likes() + "</span>" : "",
                    mine ? "<form method=\"post\" action=\"" + ctx + "/delete\"><input type=\"hidden\" name=\"id\" value=\""
                            + p.id() + "\"><button class=\"group flex items-center transition hover:text-red-500\" title=\"Supprimer\">"
                            + "<span class=\"rounded-full p-1.5 group-hover:bg-red-500/10\">"
                            + Icons.svg(Icons.TRASH, "h-[18px] w-[18px]", false) + "</span></button></form>" : ""));
        }
        if (all.isEmpty()) {
            posts.append("<p class=\"p-10 text-center text-zinc-500\">Rien ici pour l'instant. Publiez le premier message !</p>");
        }

        String errorBlock = error == null ? ""
                : "<div class=\"mx-4 mt-3 rounded-lg border border-red-900 bg-red-950 px-3 py-2 text-sm text-red-300\">"
                + Layout.escape(error) + "</div>";

        String me = user == null ? "?" : user;
        String nav = navItem(Icons.HOME, "Accueil", true) + navItem(Icons.SEARCH, "Explorer", false)
                + navItem(Icons.BELL, "Notifications", false) + navItem(Icons.MAIL, "Messages", false)
                + navItem(Icons.USER, "Profil", false);

        return """
                <div class="mx-auto flex max-w-[1100px] justify-center">

                  <aside class="sticky top-0 hidden h-screen w-[72px] flex-col px-2 py-2 sm:flex xl:w-[260px]">
                    <a href="%1$s/feed" class="mb-1 flex h-12 w-12 items-center justify-center rounded-full text-3xl font-black text-white transition hover:bg-zinc-900" title="Feed">F</a>
                    <nav class="space-y-1">%2$s</nav>
                    <a href="#composer" class="mt-4 flex h-12 w-12 items-center justify-center self-start rounded-full bg-[#1d9bf0] text-lg font-bold text-white transition hover:bg-[#1a8cd8] xl:w-full">
                      <span class="xl:hidden">+</span><span class="hidden xl:inline">Publier</span>
                    </a>
                  </aside>

                  <main class="min-h-screen w-full max-w-[600px] border-x border-zinc-800">
                    <header class="sticky top-0 z-10 border-b border-zinc-800 bg-black/80 backdrop-blur">
                      <h1 class="px-4 py-3 text-xl font-bold">Accueil</h1>
                    </header>

                    <form id="composer" method="post" action="%1$s/feed" class="border-b border-zinc-800 px-4 py-3">
                      %3$s
                      <div class="flex gap-3">
                        %4$s
                        <div class="min-w-0 flex-1">
                          <input name="author" value="%5$s" maxlength="20" required placeholder="Votre pseudo"
                                 class="mb-1 w-full border-b border-zinc-800 bg-transparent pb-2 text-[15px] text-[#1d9bf0] placeholder-zinc-600 focus:outline-none">
                          <textarea id="text" name="text" rows="2" maxlength="%6$d" required placeholder="Quoi de neuf ?!"
                                    class="w-full resize-none bg-transparent py-2 text-xl placeholder-zinc-600 focus:outline-none"></textarea>
                          <div class="flex items-center justify-between border-t border-zinc-800 pt-3">
                            <span id="count" class="text-sm text-zinc-500">0 / %6$d</span>
                            <button class="rounded-full bg-[#1d9bf0] px-5 py-1.5 font-bold text-white transition hover:bg-[#1a8cd8] active:scale-95">Publier</button>
                          </div>
                        </div>
                      </div>
                    </form>

                    %7$s
                  </main>

                  <aside class="sticky top-0 hidden h-screen w-[350px] space-y-4 py-2 pl-7 lg:block">
                    <div class="flex items-center gap-3 rounded-full bg-zinc-900 px-4 py-3 text-zinc-500">
                      %8$s<span>Rechercher</span>
                    </div>
                    <section class="rounded-2xl bg-zinc-950 p-4 ring-1 ring-zinc-800">
                      <h2 class="mb-3 text-xl font-bold">À propos</h2>
                      <p class="text-[15px] text-zinc-400">Un petit réseau social construit avec <b class="text-zinc-200">Jakarta EE 11</b>, des <b class="text-zinc-200">Servlets</b> et <b class="text-zinc-200">Tailwind CSS</b>.</p>
                      <p class="mt-3 text-sm text-zinc-500">%9$d message(s) publié(s)</p>
                    </section>
                    <section class="rounded-2xl bg-zinc-950 p-4 ring-1 ring-zinc-800 text-[15px]">
                      <h2 class="mb-2 text-xl font-bold">Tendances</h2>
                      <p class="py-1"><span class="text-sm text-zinc-500">Java</span><br><b>#JakartaEE</b></p>
                      <p class="py-1"><span class="text-sm text-zinc-500">Web</span><br><b>#Servlets</b></p>
                      <p class="py-1"><span class="text-sm text-zinc-500">CSS</span><br><b>#TailwindCSS</b></p>
                    </section>
                  </aside>
                </div>
                <script>
                  const t = document.getElementById('text'), c = document.getElementById('count');
                  t.addEventListener('input', () => c.textContent = t.value.length + ' / ' + t.maxLength);
                </script>
                """.formatted(ctx, nav, errorBlock, avatar(me, "h-10 w-10"), Layout.escape(user), FeedStore.MAX_LENGTH,
                posts, Icons.svg(Icons.SEARCH, "h-5 w-5", false), all.size());
    }
}
