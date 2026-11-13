package Ctrl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AuthentificationServlet
 */
@WebServlet("/authentifier")
public class AuthentificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthentificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // a) Récupération des informations
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // b) Vérification des identifiants
        if (email.equals("admin.fsjegj@gmail.com") && password.equals("jee2026")) {

            // c) Création de la session
            HttpSession session = request.getSession();

            // Enregistrement des informations
            session.setAttribute("nom", nom);
            session.setAttribute("prenom", prenom);
            session.setAttribute("email", email);

            // Génération de la page HTML
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

    		out.println("<!DOCTYPE html>");
    		out.println("<html lang=\"fr\">");
    		out.println("<head>");
    		out.println("<meta charset=\"UTF-8\">");
    		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
    		out.println("<title> Bienvenue </title>");
    		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">");
    		out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Bienvenue " + prenom + " " + nom + "</h2>");
            out.println("<h3>Informations personnelles :</h3>");
            out.println("<div class='container'>");
            out.println("<p>Nom : " + nom + "</p>");
            out.println("<p>Prénom : " + prenom + "</p>");
            out.println("<p>Email : " + email + "</p>");
            out.println("</div>");

            // Formulaire de déconnexion
            out.println("<form action='/TP2/logout' method='POST'>");
            out.println("<button type='submit'>Se déconnecter</button>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

        } else {

            // d) Identifiants incorrects
            response.sendRedirect("login.html");
        }

	}

}
