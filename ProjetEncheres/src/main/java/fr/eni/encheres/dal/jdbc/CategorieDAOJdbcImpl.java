package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class CategorieDAOJdbcImpl implements ObjetsEnchereDAO<Categorie> {
	private final String insertArticle = "insert into 'categrorie' (no_categorie, libelle) values(?, ?);";

	@Override
	public void insert(Categorie c) throws DALException {
		
		try (Connection cnx = ConnectionProvider.getConnection();) {
			//Todo avant insert article: insert Categorie et Utilisateur
			PreparedStatement pstmt = cnx.prepareStatement(insertArticle, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, c.getLibelle());
		
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys(); rs.next();
			if (rowsInserted > 0) {
				System.out.println(rowsInserted + " Article inséré");
				c.setNoCategorie(rowsInserted);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public List<Categorie> selectDateEnCours(LocalDate date) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

}
