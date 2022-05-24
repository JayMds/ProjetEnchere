package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.dal.ConnectionProvider;


public class ArticleDAOJDBCImpl implements ObjetsEnchereDAO<Article>{
	
	private final String selectAllArticles = "select * from Articles; ";

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
	//Création Liste
		List<Article> articles = new ArrayList<>();
		Article a = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();)
			{
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllArticles);
				while (rs.next()) {
					a = new Article();
					articles.add(a);
				}
			
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		return articles;
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
