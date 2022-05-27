package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.dal.SelectByDateInterface;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;

//TODO definir la/les requêtes de sélection par  date

public class ArticleDAOJDBCImpl implements ObjetsEnchereDAO<Article>, SelectByDateInterface<Article>{
	private final String selectByDateArticle = "select * from 'articles' where  date_fin_encheres is null;";
	private final String SELECT_UNSELL_ARTICLE = "SELECT * FROM `ARTICLES` WHERE no_acheteur is NULL";
	private final String selectAllArticles = "select * from 'articles'; ";
	private final String selectByIdArticles = "SELECT `no_article`, `nom_article`, `description`, `date_debut_encheres`, `date_fin_encheres`, `prix_initial`, `prix_vente`, `no_vendeur`, `no_categorie`, `no_acheteur` FROM `ARTICLES` WHERE `no_article` = ?; ";
	private final String insertArticle = "insert into 'articles' (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie, no_acheteur ) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String deleteArticle = "delete from 'articles' where no_article = ?;";
	public final SimpleDateFormat formatDateFR = new SimpleDateFormat("DD/MM/YY");

	@Override
	public void insert(Article a) {
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			//Todo avant insert article: insert Categorie et Utilisateur
			PreparedStatement pstmt = cnx.prepareStatement(insertArticle, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, a.getNomArticle());
			pstmt.setString(2, a.getDescription());
			pstmt.setObject(3, a.getDateDebutEnchere());
			pstmt.setObject(4, a.getDateFinEnchere());
			pstmt.setInt(5, a.getPrixInitial());
			pstmt.setInt(6, a.getPrixVente());
			pstmt.setInt(7, a.getNoVendeur());
			pstmt.setInt(8, a.getNoCategorie());
			pstmt.setInt(9, a.getNoAcheteur());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys(); rs.next();
			if (rowsInserted > 0) {
				System.out.println(rowsInserted + " Article inséré");
				a.setNoArticle(rs.getInt(1));
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public Article selectByIdFull(int id) {
		Article a = null;
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdArticles);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),  rs.getObject("date_debut_encheres", LocalDateTime.class), rs.getObject("date_fin_encheres", LocalDateTime.class), rs.getInt("prix_initial"), rs.getInt("prix_vente") , rs.getInt("no_vendeur"), rs.getInt("no_categorie"), rs.getInt("no_acheteur"));
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
	public List<Article> selectAllDiscret() {
	//Création Liste
		List<Article> articles = new ArrayList<>();
		Article a = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllArticles);
				while (rs.next()) {
					a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), rs.getObject("date_debut_encheres", LocalDateTime.class), rs.getObject("date_fin_encheres", LocalDateTime.class), rs.getInt("prix_initial"), rs.getInt("prix_vente") , rs.getInt("no_vendeur"), rs.getInt("no_categorie"), rs.getInt("no_acheteur"));
					articles.add(a);
				}
			
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		return articles;
	}
	//paramatre int ou Article?
	@Override
	public void delete(int id) {
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(deleteArticle);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println(rowsAffected+ " Article suprimmé");
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();		
			}
	}

	public List<Article> selectDateEnCours() {
		Article a = null;
		List<Article> articleEnCours = new ArrayList<Article>();
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery(selectByDateArticle);
			if (rs != null) {
				while (rs.next()) {
					a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),
							rs.getObject("date_debut_encheres", LocalDateTime.class),
							rs.getObject("date_fin_encheres", LocalDateTime.class), rs.getInt("prix_initial"),
							rs.getInt("prix_vente"), rs.getInt("no_vendeur"), rs.getInt("no_categorie"),
							rs.getInt("no_acheteur"));
					articleEnCours.add(a);
				} 
			} else {
				throw new DALException("Aucun Article Correspondant");
			}
			stmt.close();
		}catch (Exception e) {
			e.printStackTrace();		
		}
		return articleEnCours;
	}

	@Override
	public List<Article> selectUnsellArticle() {
		List<Article> articles = new ArrayList<>();
		Article a = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_UNSELL_ARTICLE);
				while (rs.next()) {
					a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),  rs.getObject("date_debut_encheres", LocalDateTime.class), rs.getObject("date_fin_encheres", LocalDateTime.class), rs.getInt("prix_initial"), rs.getInt("prix_vente") , rs.getInt("no_vendeur"), rs.getInt("no_categorie"), rs.getInt("no_acheteur"));
					
					articles.add(a);
				}
			
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		return articles;
	}

	
	@Override
	public Article selectByIdDiscret(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectAllFull() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Article verificationLogin(String a, String b) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
}
