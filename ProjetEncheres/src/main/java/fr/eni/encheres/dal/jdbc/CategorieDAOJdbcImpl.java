package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class CategorieDAOJdbcImpl implements ObjetsEnchereDAO<Categorie> {

	@Override
	public void insert(Categorie c) throws DALException {
		
		/*try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdArticles);
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();(Connection cnx = ConnectionProvider.getConnection();) {
				//Todo avant insert article: insert Categorie et Utilisateur
				PreparedStatement pstmt = cnx.prepareStatement(insertArticle);
				pstmt.setString(1, insertArticle);
				pstmt.setString(1, insertArticle);
				pstmt.setInt(1, 0);
		//Si rs.next renvoie un resultat creer un nouvel Article
			
		} catch (Exception e) {
			// TODO: handle exception
		}*/
				
	}

	@Override
	public Categorie selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categorie> selectAll() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Categorie> selectDateEnCours(LocalDate date) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

}
