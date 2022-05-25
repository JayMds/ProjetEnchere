package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

//todo manage du DELETE

public class CategorieManager {
	
	private ObjetsEnchereDAO<Categorie> categorieDAO;

	public CategorieManager(ObjetsEnchereDAO<Categorie> categorieDAO) {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}
	
	public void addArticle(String nom) throws DALException {
		Categorie c = new Categorie(nom);
		categorieDAO.insert(c);
	}
	
	public Categorie selectArticle(int id) throws DALException {
		Categorie c = categorieDAO.selectById(id);
		return c;
	}
	
	public List<Categorie> selectAllArticles() throws DALException{
		List<Categorie> categories = new ArrayList<>();
		categories = categorieDAO.selectAll();
		return categories;
		
	}

}
