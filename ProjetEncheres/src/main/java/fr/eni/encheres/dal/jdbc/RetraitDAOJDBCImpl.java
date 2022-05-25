package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class RetraitDAOJDBCImpl implements ObjetsEnchereDAO<Retrait> {
	private final String insertRetrait= "insert into 'retraits' (no_article, rue, code_postal, ville) values(?, ?, ?, ?);";
	private final String selectByIdRetrait= "select * from 'retraits' where no_article = ?; ";
	private final String selectAllRetrait= "select * from 'retraits'; ";
	private final String deleteRetrait = "delete from 'retraits' where no_article = ?;";
	
	@Override
	public void insert(Retrait r) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(insertRetrait);
			pstmt.setInt(1, r.getNoArticle());
			pstmt.setString(2, r.getRue());
			pstmt.setString(3, r.getCodePostal());
			pstmt.setString(4, r.getVille());
			
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println(rowsInserted + " Retrait inséré");
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		
	}
	@Override
	public Retrait selectById(int id) throws DALException {
		Enchere e = null;
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdRetrait);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) 
			{
				e = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere").toLocalDate(), rs.getInt("montant_enchere"));
			}
			else{
				
				throw new DALException("Aucun Article ne correspond à l'id "+ id);
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();		
			}
		return e;
	}
	@Override
	public List<Retrait> selectAll() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override 
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}
	
	@Override //DO NOT USE
	public List<Retrait> selectDateEnCours(LocalDate date) {
		return null;
	}
	@Override //DO NOT USE
	public List<Retrait> selectUnsellArticle() {
		return null;
	}
	

}
