package fr.eni.encheres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@Override
		public void init() throws ServletException {
			UtilisateurManager userManager = new UtilisateurManager(); 
		}
	
	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on verifie si une session existe deja
		
		if(request.getSession(false) != null){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
			rd.forward(request, response);
		    
		}
		else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
			rd.forward(request, response);
		}
		
		
		
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String mot_de_passe = request.getParameter("motdepasse");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "nanou");
			PreparedStatement pst = cnx.prepareStatement("select * from users where email = ? and motdepasse = ?");
			pst.setString(1, email);
			pst.setString(2, mot_de_passe);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("nom", rs.getString("nom"));
				dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
				
			}else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
				
			}
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
