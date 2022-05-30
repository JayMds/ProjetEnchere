package fr.eni.encheres;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class ServletArticle
 */

public class ServletArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author aCreativDesign - AlexG
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager artManager = new ArticleManager(); 
		CategorieManager catManager = new CategorieManager(); 
		EnchereManager encheresManager = new EnchereManager();
		UtilisateurManager userManager = new UtilisateurManager();
		RetraitManager retraitManager = new RetraitManager(); 
		
		
		
		int idArticle = Integer.parseInt(request.getParameter("idArticle") ); 
		try {
			Article article = artManager.selectArticle(idArticle);
			
			
			//recupération de la categorie
			Categorie categorie = catManager.selectCategrorie(article.getNoCategorie());
			article.setCatagorie(categorie); 
			
			
			//récupération du meilleur encherisseur
			Enchere enchere = encheresManager.selectEnchere(idArticle);
			Utilisateur meilleureEncherisseur =  userManager.selectionnerInformationDiscret(enchere.getNoUtilisateur()); 
			enchere.setEncherisseur(meilleureEncherisseur);
			article.setEnchere(enchere); 
			
			//recupération du retrait
			
			
			//Récupération du vendeur
			Utilisateur Vendeur = userManager.selectionnerInformationDiscret(article.getNoVendeur()); 
			article.setVendeur(Vendeur);
			
			request.setAttribute("article", article);			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article.jsp"); 
			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user = (Utilisateur) request.getSession(false).getAttribute("connectedUser");
		

		System.out.println(user.toString());
	}

}
