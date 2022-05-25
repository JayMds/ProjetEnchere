package fr.eni.encheres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String mot_de_passe = request.getParameter("motdepasse");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		
		RequestDispatcher dispatcher = null;
		Connection cnx = null;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "nanou");
			PreparedStatement pst = cnx.prepareStatement("insert into users(pseudo,nom,prenom,email,motdepasse,telephone,rue,code_postal,ville) values(?,?,?,?,?,?,?,?,?) ");
			pst.setString(1, pseudo);
			pst.setString(2, nom);
			pst.setString(3, prenom);
			pst.setString(4, email);
			pst.setString(5, mot_de_passe);
			pst.setString(6, telephone);
			pst.setString(7, rue);
			pst.setString(8, codePostal);
			pst.setString(9, ville);
			
			int rowCount = pst.executeUpdate();
			
			if (rowCount > 0) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
				request.setAttribute("status", "success");
				
			} else {
				dispatcher = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				cnx.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
	
}
