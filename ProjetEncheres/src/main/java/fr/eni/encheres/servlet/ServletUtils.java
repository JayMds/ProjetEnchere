package fr.eni.encheres.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;

public abstract class ServletUtils {

	public static void selectAndSetAttributeCategorie(HttpServletRequest request) throws DALException {
		CategorieManager catManager = new CategorieManager(); 
		List<Categorie> listeCategories = catManager.selectAllCategrorie(); 
		request.setAttribute("Categories", listeCategories);
	}

	public static void selectAndSetAttributeUnsellArticle(HttpServletRequest request) throws BusinessException, DALException {
		ArticleManager artManager = new ArticleManager();			
		List<Article> listeArticle = artManager.selectUnsellArticle();					
		request.setAttribute("listeArticle", listeArticle);
	}
	
	
	
	
	
}
