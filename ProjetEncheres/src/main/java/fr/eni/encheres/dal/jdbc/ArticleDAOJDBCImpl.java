package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;


public class ArticleDAOJDBCImpl implements ObjetsEnchereDAO<Article>{
	public final SimpleDateFormat formatDateFR = new SimpleDateFormat("DD/MM/YY");
	private final String selectAllArticles = "select * from Articles; ";
	private final String selectByIdArticles = "select * from Articles where no_article = ?; ";
	private final String insertArticle = "insert into articles (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie, no_acheteur ) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

	@Override
	public void insert(Article a) {
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			//Todo avant insert article: insert Categorie et Utilisateur
			PreparedStatement pstmt = cnx.prepareStatement(insertArticle);
			pstmt.setString(1, insertArticle);
			pstmt.setString(1, insertArticle);
			pstmt.setInt(1, 0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public Article selectById(int id) {
		Article a = null;
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdArticles);
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
		//Si rs.next renvoie un resultat creer un nouvel Article
			if (rs.next()) 
			{
				a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), (rs.getDate("date_debut_encheres")).toLocalDate(), (rs.getDate("date_fin_encheres")).toLocalDate(), rs.getInt("prix_initial"), rs.getInt("prix_vente") , rs.getInt("no_vendeur"), rs.getInt("no_categorie"), rs.getInt("no_acheteur"));
			}else{
				
				throw new DALException("Aucun Article ne correspont à l'id "+ id);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();		
			}
		

		return a;
	}

	@Override
	public List<Article> selectAll() {
	//Création Liste
		List<Article> articles = new ArrayList<>();
		Article a = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllArticles);
				while (rs.next()) {
					a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), (rs.getDate("date_debut_encheres")).toLocalDate(), (rs.getDate("date_fin_encheres")).toLocalDate(), rs.getInt("prix_initial"), rs.getInt("prix_vente") , rs.getInt("no_vendeur"), rs.getInt("no_categorie"), rs.getInt("no_acheteur"));
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
