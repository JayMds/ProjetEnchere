package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

//todo manage du DELETE

public class ArticleManager extends  VerificationArticleManager {
	
	private ObjetsEnchereDAO<Article> articleDAO;

	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}
	
	public void addArticle(String nom, String description, LocalDateTime dateDebut, LocalDateTime dateFin, int prixInit, int vendeur, int categorie) throws DALException, BusinessException {
		BusinessException exception = new BusinessException();
		this.validerNom(nom, exception);
		this.validerDescription(description, exception);
		this.validerDateDebut(dateDebut, exception);
		this.validerDateFin(dateFin, exception);
		this.validerPrixInitial(prixInit, exception);
		this.validerVendeur(vendeur, exception);
		this.validerCategorie(categorie, prixInit, exception);
		
		if (!exception.hasErreurs()) 
		{
			Article a = new Article(nom, description, dateDebut, dateFin, prixInit, vendeur, categorie);
			articleDAO.insert(a);
		}
				
		if (exception.hasErreurs()) 
		{
			throw exception;
		}
		
	}
	
	public Article selectArticle(int id) throws DALException, BusinessException {
		BusinessException exception = new BusinessException();
		Article a = null;
		this.validerNoArticle(id, exception);
		if (!exception.hasErreurs()) {
			a = articleDAO.selectByIdFull(id);			
		}
		
		if (exception.hasErreurs()) {
			throw exception;
		}

		return a;
	}
	
	public List<Article> selectAllArticles() throws DALException{
		List<Article> articles = new ArrayList<>();
		articles = articleDAO.selectAllDiscret();
		return this.articleDAO.selectAllDiscret();
	}
	
	public List<Article> selectArticleEnVente(){
		List<Article> articles = new ArrayList<>();
		articleDAO.selectDateEnCours();	
		return articles;
	}
	
	public List<Article> selectUnsellArticle(){
		return this.articleDAO.selectUnsellArticle();
		
	}
	
	public  void deleteArticle(int id) throws DALException {
		articleDAO.delete(id);
	}
	
	public void updateArticle(Article a) {
		articleDAO.update(a, false);
	}

}
