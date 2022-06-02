package fr.eni.encheres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.io.ListenCallback;

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
 * Servlet implementation class ServletRechercheUser
 */
@WebServlet("/ServletRechercheUser")
public class ServletRechercheUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRechercheUser() {
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
		try {
			ArticleManager artManager = new ArticleManager();
			UtilisateurManager userManager = new UtilisateurManager(); 
			EnchereManager enchereManager = new EnchereManager(); 
			CategorieManager categorieManager = new CategorieManager();
			
			List<Article> listeArticle = new ArrayList<Article>();
			List<Categorie> listeCategorie = new ArrayList<Categorie>();
			
			String recherche = request.getParameter("recherche");
			String cat = request.getParameter("choixCategorie");
		//si un choix de categorie est fait:
			int choixCategorie = 0;
			if (!cat.equalsIgnoreCase("all")) {
				choixCategorie = Integer.valueOf(request.getParameter("choixCategorie"));
				listeArticle = artManager.selectRechercheUser(recherche, choixCategorie);	
			} else {
				listeArticle = artManager.selectAllRechercheUser(recherche);
			}
			
			
			
			listeCategorie = categorieManager.selectAllCategrorie();
			
				for(Article article : listeArticle) {
					Utilisateur user = userManager.selectionnerInformationDiscret(article.getNoVendeur()); 
					Enchere enchere = enchereManager.selectEnchere(article.getNoArticle()); 
					//System.out.println(user.getPseudo());
					article.setNomVendeur(user.getPseudo()); 
					article.setEnchere(enchere); 
			
			}
				request.setAttribute("listeArticle", listeArticle); 
				request.setAttribute("Categories", listeCategorie);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
				rd.forward(request, response);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
