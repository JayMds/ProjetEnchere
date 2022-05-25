package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

//todo manage du DELETE

public class ArticleManager {
	
	private ObjetsEnchereDAO<Article> articleDAO;

	public ArticleManager(ObjetsEnchereDAO<Article> articleDAO) {
		this.articleDAO = DAOFactory.getArticleDAO();
	}
	
	public void addArticle(String nom, String description, LocalDate dateDebut, LocalDate dateFin, int prixInit, int prixFin, int vendeur, int categorie, int acheteur) throws DALException {
		Article a = new Article(nom, description, dateDebut, dateFin, prixInit, prixFin, vendeur, categorie, acheteur);
				articleDAO.insert(a);
	}
	
	public Article selectArticle(int id) throws DALException {
		Article a = articleDAO.selectById(id);
		return a;
	}
	
	public List<Article> selectAllArticles() throws DALException{
		List<Article> articles = new ArrayList<>();
		articles = articleDAO.selectAll();
		return articles;
		
	}
	
	public List<Article> selectArticleEnVente(LocalDate date){
		List<Article> articles = new ArrayList<>();
		articleDAO.selectDateEnCours(date);
		return articles;
		
		
	}
	
	public void verifArticle() {
		
		
	}

}
