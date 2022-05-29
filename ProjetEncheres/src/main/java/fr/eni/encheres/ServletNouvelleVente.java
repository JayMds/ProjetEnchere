package fr.eni.encheres;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class NouvelleVente
 */
@WebServlet("/NouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		CategorieManager catManager = new CategorieManager(); 
		List<Categorie> listeCategories;
		try {
			listeCategories = catManager.selectAllCategrorie();
			request.setAttribute("Categories", listeCategories); 
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
			rd.forward(request, response);
			
			
			
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 			
	
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		int categorie = Integer.parseInt(request.getParameter("categorie"));  
		int prixInit = Integer.parseInt(request.getParameter("prix"));
		//convertion de l'input date en localdate time
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		String dateDebut = request.getParameter("debutEncheres");
		String dateFin = request.getParameter("finEncheres");
		//System.out.println("heure sans replace :"+ dateDebut);
		dateDebut = dateDebut.replace("T", " ");
		dateFin = dateFin.replace("T", " ");
		//System.out.println("heure avec  replace :"+ dateDebut );
		
		
		
		LocalDateTime dateDebutEncheres = LocalDateTime.parse(dateDebut, formatter);
		LocalDateTime dateFinEncheres = LocalDateTime.parse(dateFin, formatter);
		
			
	
		
		String rueRetrait = request.getParameter("rue");
		String CPReatrait = request.getParameter("codePostal");
		String villeRetrait = request.getParameter("ville");
		
		ArticleManager artManager = new ArticleManager(); 
		RetraitManager retraitManager = new RetraitManager(); 
		
				
		
		Utilisateur vendeur = (Utilisateur) request.getSession(false).getAttribute("connectedUser");
	
		
		if(listeCodesErreur.size()>0) {
			request.setAttribute("listeCodesErreur",listeCodesErreur);			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
			rd.forward(request, response);	
		}else {
			
			try {
				artManager.addArticle(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInit , vendeur.getNoUtilisateur(), categorie);
		
				String message = "Votre article est maintenant en vente";
				response.setCharacterEncoding("UTF-8" );				
				response.addCookie( CookieUtils.SetCookie("message", message, 10)  );				
				response.sendRedirect(request.getContextPath());
			
			} catch (DALException e) {
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
				rd.forward(request, response);	
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		
		//message de reussite
		
	}

}
