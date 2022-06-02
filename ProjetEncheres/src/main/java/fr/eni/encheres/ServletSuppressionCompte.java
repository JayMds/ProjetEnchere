package fr.eni.encheres;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class ServletSuppressionCompte
 */
@WebServlet("/suppression-de-compte")
public class ServletSuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSuppressionCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 int no_utilisateur =Integer.parseInt(request.getParameter("id")) ;
	 
	 UtilisateurManager userManager = new UtilisateurManager(); 
	 try {
		userManager.deleteUser(no_utilisateur);
		String message = "Votre compte a bien été supprimé";
		response.setCharacterEncoding("UTF-8" );				
		response.addCookie( CookieUtils.SetCookie("message", message, 10)  );	
		response.sendRedirect(request.getContextPath()+"/logout");
		
	} catch (DALException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
