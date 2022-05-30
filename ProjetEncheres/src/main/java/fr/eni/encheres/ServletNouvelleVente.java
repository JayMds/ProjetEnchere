package fr.eni.encheres;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.servlet.http.Part;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class NouvelleVente
 */
@WebServlet("/NouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_TAMPON = 10240;
	public final String imgMax = "/ProjetEncheres/src/main/webapp/asset/img/imgArticleMax";  
	public final String imgMini = "/ProjetEncheres/src/main/webapp/asset/img/imgArticleMini";
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
		
		Article a = null;
		FichierUtils fichierUtils = new FichierUtils();
		
		if(listeCodesErreur.size()>0) {
			request.setAttribute("listeCodesErreur",listeCodesErreur);			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
			rd.forward(request, response);	
		}else {
			
			try {
				try {
					a =	artManager.addArticle(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInit , vendeur.getNoUtilisateur(), categorie);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				Part part = request.getPart("photoProduit");
				
				String fileName = fichierUtils.getNomFichier(part);
				if (fileName != null && !fileName.isEmpty()) {
					System.out.println(fileName);
					fileName = "Article"+a.getNoArticle();
					fichierUtils.ecrireFichier(part, fileName, imgMax);
				}
				
				String message = "Votre article est maintenant en vente";
				response.setCharacterEncoding("UTF-8" );				
				response.addCookie( CookieUtils.SetCookie("message", message, 10)  );				
				response.sendRedirect(request.getContextPath());
			
			} catch (DALException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
				rd.forward(request, response);	
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		
		//message de reussite
		
	}


  
}
