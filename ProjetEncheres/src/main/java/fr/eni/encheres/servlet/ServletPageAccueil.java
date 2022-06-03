package fr.eni.encheres.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class ServletPageAccueil
 */

public class ServletPageAccueil extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/**
		 * @author AlexG
		 */
		
		try {
			//Récupération des articles en cour de vente et des informations du vendeur
			ServletUtils.selectAndSetAttributeUnsellArticle(request);
			
			//récupération de l'ensemble des catégorie sur la bdd			
			
			ServletUtils.selectAndSetAttributeCategorie(request);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
			rd.forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager artManager = new ArticleManager();
				
		String recherche = request.getParameter("recherche");
		int choixCategorie = Integer.valueOf("choixCategorie");
		
		List<Article> listeArticle = artManager.selectRechercheUser(recherche, choixCategorie);	
		
		request.setAttribute("listeArticle", listeArticle); 
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
		rd.forward(request, response);
		
	}

}