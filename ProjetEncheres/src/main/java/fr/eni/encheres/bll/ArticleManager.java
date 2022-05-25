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

	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}
	
	public void addArticle(String nom, String description, LocalDate dateDebut, LocalDate dateFin, int prixInit, int prixFin, int vendeur, int categorie, int acheteur) throws DALException {
		Article a = new Article(nom, description, dateDebut, dateFin, prixInit, prixFin, vendeur, categorie, acheteur);
				articleDAO.insert(a);
	}
	
	public Article selectArticle(int id) throws DALException {
		Article a = articleDAO.selectAllById(id);
		return a;
	}
	
	public List<Article> selectAllArticles() throws DALException{
		List<Article> articles = new ArrayList<>();
		articles = articleDAO.selectAllDiscret();
		return articles;
		
	}
	
	public List<Article> selectArticleEnVente(LocalDate date){
		List<Article> articles = new ArrayList<>();
		articleDAO.selectDateEnCours(date);	
		
		return articles;
	}
	
	public List<Article> selectUnsellArticle(){
		List<Article> articles = new ArrayList<>();
		//articleDAO.selectDateEnCours(date);
		
		articles = articleDAO.selectUnsellArticle();
		
		return articles;
		
	}
	
	public  void deleteArticle(int id) throws DALException {
		articleDAO.delete(id);
	}
	
	public void verifArticle() {
		
		
	}

}
