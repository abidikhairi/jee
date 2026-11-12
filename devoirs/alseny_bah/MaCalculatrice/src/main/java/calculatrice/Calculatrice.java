package calculatrice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Servlet implementation class Calculatrice
 */
@WebServlet("/Calculatrice")
public class Calculatrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calculatrice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		//Recuperation des donnees
		int entier1 = Integer.parseInt(request.getParameter("E1"));
		int entier2 = Integer.parseInt(request.getParameter("E2"));
		String operateur = request.getParameter("operateur");
		
		int resultat = 0;
		
		switch(operateur) {
			
			case	"+": resultat= entier1 + entier2;
			break;
			
			case	"-": resultat = entier1 - entier2;
			break;
			
			case 	"*": resultat = entier1 * entier2;
			break;
			
			case 	"/":
				try {
						
						resultat = entier1 / entier2;
				}catch(ArithmeticException e) {
					e.printStackTrace();
				}
		
		}
		
		
		out.print("<!Doctype html>");
		out.print("<html lang=\"fr\">");
		out.print("<head>");
		out.print("<meta charset=\"UTF-8\">");
		out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		out.println("<title>La calculatrice pro </title>");
		out.println("<link rel=\"stylesheet\" href=\""
		        + request.getContextPath()
		        + "/css/style.css\">");
		out.println("</head>");
		out.println("<body>");
		 if (entier2 == 0 && operateur.equals("/"))
			 out.println("Division impossible");	 
		 else  
			 out.println("<p> " +entier1 +" "+operateur+" "+entier2 +" = " + resultat);
		 out.println("</body>");
		 out.println("</html>");
			 
		
		
		
	}

}
