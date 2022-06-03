package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;


/**
 * Servlet implementation class ServletInscription
 */

public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = "";
		String nom = "";
		String prenom = "";
		String email = "";
		String motDePasse = "";
		String confirmation = "";
		String telephone = "";;
		String rue = "";
		String codePostal = "";
		String ville = "";
		Utilisateur newUser = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, ville, false);
		System.out.println(newUser.toString());
		setUtilisateurAndForward(request, response, newUser);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String motDePasse = request.getParameter("motDePasse");
		String confirmation = request.getParameter("confirmation");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		
	
		Utilisateur newUser = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, ville, false); 
		UtilisateurManager userManager = new UtilisateurManager(); 
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		
		
		/**
		 * @author AlexG
		 */
		if(!motDePasse.equals(confirmation)) {			
			listeCodesErreur.add(CodeResultatServlet.CONFIRMATION_MDP);					
		}
		
		//traitement
		if(listeCodesErreur.size()>0) {
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			setUtilisateurAndForward(request, response, newUser);		
		}else {
			
			try {
				
				userManager.ajouterutilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse);
				//création du cookie pour l'affichage du message
				//TODO création methode et fichier proprietes 
				String message = "Votre inscription est réussie, bienvenue parmis nous.";
				response.setCharacterEncoding("UTF-8" );				
				response.addCookie( CookieUtils.SetCookie("message", message, 10)  );				
				response.sendRedirect(request.getContextPath());
				
				
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				setUtilisateurAndForward(request, response, newUser);	
				e.printStackTrace();
				
			} catch (DALException e) {
				setUtilisateurAndForward(request, response, newUser);	
				e.printStackTrace();
			} 
		}
		
		
		
}

	private void setUtilisateurAndForward(HttpServletRequest request, HttpServletResponse response, Utilisateur newUser)
			throws ServletException, IOException {
		request.setAttribute("NewUser", newUser); 
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
		rd.forward(request, response);
	}
	
}
