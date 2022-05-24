package fr.eni.encheres.dal.jdbc;

import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class ArticleDAOJDBCImpl implements ObjetsEnchereDAO<Article>{

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Article> selectDateEnCours(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}
