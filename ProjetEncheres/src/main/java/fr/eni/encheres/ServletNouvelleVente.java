package fr.eni.encheres;
import java.io.File;
import java.io.IOException;
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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

/**
 * Servlet implementation class NouvelleVente
 */
@WebServlet("/NouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
//Dossier d'enregistrment adapter a son emplacement (phase dev)
	public final String imgArticlesPath = "D:\\projets-web\\Projets Java\\ProjetEnchere\\ProjetEncheres\\src\\main\\webapp\\asset\\img\\ImgArticles";
 	 @Override
 	
 	 public void init() throws ServletException {
 		// TODO Auto-generated method stub
 		super.init();
 	}
	
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
	//Conversion request ---> Multipart TODO RESTE A REGLER LIMTE TAILLE FICHIER ET FORMAT OU CONVERSION EN JPG (PAS TROP COMPLIQUé) reorga exception
		List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
		FileItem imgArticle = null;
		String fileName = null;
	//Inits Outils et variables
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		List<Integer> listeCodesErreur=new ArrayList<>();
		Article a = null;
		String nomArticle = null;
		String description = null;
		int categorie = 0;  
		int prixInit = 0 ;
		String dateDebut = null;
		String dateFin = null;
		String rueRetrait = null;
		String CPReatrait = null;
		String villeRetrait = null;
	//Itération item de Multipart    
        for(FileItem item : multiparts){
    //Si item = champ de formulaire    	
        	if (item.isFormField()) 
        	{
    //Test item pour determiner contenu et mettre dans la bonne variable   		
        	switch (item.getFieldName()) 
	        	{
				case "nomArticle": nomArticle = item.getString(); break;
				case "description": description = item.getString(); break;
				case "categorie": categorie = Integer.parseInt(item.getString());break;
				case "prix": prixInit = Integer.parseInt(item.getString()); break;
				case "debutEncheres": dateDebut = item.getString(); break;
				case "finEncheres": dateFin = item.getString(); break;
				case "rue": rueRetrait = item.getString(); break;
				case "codePostal": CPReatrait = item.getString(); break;
				case "ville": villeRetrait = item.getString(); break;
				default: System.out.println("problème survenu");
				}
			}
    //Si item = fichier
            if(!item.isFormField()){
            	imgArticle = item;
            }
        }
    //convertion de l'input  string date en localdate time
		dateDebut = dateDebut.replace("T", " ");
		dateFin = dateFin.replace("T", " ");
		LocalDateTime dateDebutEncheres = LocalDateTime.parse(dateDebut, formatter);
		LocalDateTime dateFinEncheres = LocalDateTime.parse(dateFin, formatter);
	//Création des Managers
		ArticleManager artManager = new ArticleManager(); 
		RetraitManager retraitManager = new RetraitManager();
		EnchereManager enchereManager = new EnchereManager();
	//Récuperation User Courant
		Utilisateur vendeur = (Utilisateur) request.getSession(false).getAttribute("connectedUser");
	//Si des erreurs sont générée:
		if(listeCodesErreur.size()>0) 
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
			rd.forward(request, response);
	//Sinon
		}else {
			try {//création + insert d'un nouvel article
				a =	artManager.addArticle(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInit , vendeur.getNoUtilisateur(), categorie);
				//création d'une enchère vierge + insert
				Enchere e = enchereManager.addEnchere(a.getNoArticle(), a.getPrixInitial());
				a.setEnchere(e);
				//création d'un point de retrait + insert
				//Retrait r = retraitManager.addRetrait(prixInit, rueRetrait, CPReatrait, villeRetrait);
				//a.setRetrait(r);
				//Création fichier image "ArticleX.ext"
		        fileName = new File(imgArticle.getName()).getName();			 //recuperation nom.ext
	            String [] split = fileName.split("\\.");						 //séparation
	            System.out.println(split[0]);System.out.println(split[1]);		 //test
	           // fileName = "Article"+a.getNoAcheteur()+split[split.length - 1];
	            fileName= "Article".concat(String.valueOf(a.getNoArticle())).concat(".").concat(split[split.length - 1]);
	            System.out.println(fileName);
	            imgArticle.write( new File(imgArticlesPath + File.separator + fileName)); 
	            String message = "Votre article est maintenant en vente";
				response.setCharacterEncoding("UTF-8" );				
				response.addCookie( CookieUtils.SetCookie("message", message, 10)  );				
				response.sendRedirect(request.getContextPath());
				
			} catch (DALException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
				rd.forward(request, response);	
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//message de reussite
	}


  
}
