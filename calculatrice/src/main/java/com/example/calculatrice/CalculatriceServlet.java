package com.example.calculatrice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * GET  /calc  -> shows the calculator form (and the history stored in the session)
 * POST /calc  -> computes a op b, stores the line in the session history, then
 *                redirects to GET (Post/Redirect/Get, so a refresh never re-submits).
 */
@WebServlet(name = "calculatrice", urlPatterns = "/calc")
public class CalculatriceServlet extends HttpServlet {

    static final String HISTORY = "history";
    static final String ERROR = "error";
    private static final int MAX_HISTORY = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<String> history = history(session);

        // one-shot "flash" message left by doPost
        String error = (String) session.getAttribute(ERROR);
        session.removeAttribute(ERROR);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().print(Layout.page(req.getContextPath(), render(history, error)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        try {
            BigDecimal a = new BigDecimal(req.getParameter("a").trim().replace(',', '.'));
            BigDecimal b = new BigDecimal(req.getParameter("b").trim().replace(',', '.'));
            Operation op = Operation.fromCode(req.getParameter("op"));

            BigDecimal result = op.apply(a, b).stripTrailingZeros();
            String line = a.toPlainString() + " " + op.symbol + " " + b.toPlainString()
                    + " = " + result.toPlainString();

            List<String> history = history(session);
            history.add(0, line);
            while (history.size() > MAX_HISTORY) {
                history.remove(history.size() - 1);
            }
        } catch (NumberFormatException | NullPointerException e) {
            session.setAttribute(ERROR, "Veuillez saisir deux nombres valides.");
        } catch (IllegalArgumentException | ArithmeticException e) {
            session.setAttribute(ERROR, e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/calc");
    }

    @SuppressWarnings("unchecked")
    private static List<String> history(HttpSession session) {
        List<String> history = (List<String>) session.getAttribute(HISTORY);
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute(HISTORY, history);
        }
        return history;
    }

    private static String render(List<String> history, String error) {
        StringBuilder options = new StringBuilder();
        for (Operation o : Operation.values()) {
            options.append("<option value=\"").append(o.code).append("\">")
                    .append(o.symbol).append("  ").append(o.label).append("</option>");
        }

        StringBuilder rows = new StringBuilder();
        for (String line : history) {
            rows.append("<li class=\"rounded-lg bg-slate-100 px-3 py-2 font-mono text-sm text-slate-700\">")
                    .append(Layout.escape(line)).append("</li>");
        }
        String historyBlock = history.isEmpty()
                ? "<p class=\"text-sm text-slate-400\">Aucun calcul pour l'instant.</p>"
                : "<ul class=\"space-y-2\">" + rows + "</ul>"
                + "<form method=\"post\" action=\"history\" class=\"mt-3\">"
                + "<button class=\"text-sm font-medium text-pink-600 hover:underline\">Effacer l'historique</button></form>";

        String errorBlock = error == null ? ""
                : "<div class=\"mb-4 rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700\">"
                + Layout.escape(error) + "</div>";

        String input = "w-full rounded-lg border border-slate-300 px-4 py-3 text-lg "
                + "focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-200";

        return """
                <main class="w-full max-w-md rounded-2xl bg-white p-8 shadow-2xl">
                  <h1 class="mb-1 text-3xl font-bold text-indigo-700">Calculatrice</h1>
                  <p class="mb-6 text-sm text-slate-500">Jakarta EE 11 · Servlets · Tailwind CSS</p>
                  %s
                  <form method="post" action="calc" class="space-y-4">
                    <input name="a" type="text" inputmode="decimal" required placeholder="Premier nombre" class="%s">
                    <select name="op" class="%s">%s</select>
                    <input name="b" type="text" inputmode="decimal" required placeholder="Second nombre" class="%s">
                    <button type="submit" class="w-full rounded-lg bg-indigo-600 py-3 text-lg font-semibold text-white transition hover:bg-indigo-700 active:scale-95">Calculer</button>
                  </form>
                  <section class="mt-8">
                    <h2 class="mb-3 text-sm font-semibold uppercase tracking-wide text-slate-500">Historique</h2>
                    %s
                  </section>
                </main>
                """.formatted(errorBlock, input, input, options, input, historyBlock);
    }
}
