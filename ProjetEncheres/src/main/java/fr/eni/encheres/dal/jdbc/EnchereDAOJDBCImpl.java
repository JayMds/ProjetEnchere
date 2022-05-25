package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class EnchereDAOJDBCImpl implements ObjetsEnchereDAO<Enchere> {
	private final String insertEnchere= "insert into 'encheres' (no_utilisateur, no_article, date_enchere, montant_enchere) values(?, ?, ?, ?);";
	private final String selectByIdEnchere = "select * from 'encheres' where no_article = ?; ";
	private final String selectAllEnchere= "select * from 'encheres'; ";
	private final String deleteEnchere = "delete from 'encheres' where no_article = ?;";



	@Override
	public void insert(Enchere e) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(insertEnchere);
			pstmt.setInt(1, e.getNoUtilisateur());
			pstmt.setInt(2, e.getNoArticle());
			pstmt.setDate(3, java.sql.Date.valueOf(e.getDateEnchere()));
			pstmt.setInt(4, e.getMontant());
			
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println(rowsInserted + " Article inséré");
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

	@Override
	public Enchere selectById(int id) throws DALException {
		Enchere e = null;
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdEnchere);
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
	public List<Enchere> selectAll() throws DALException {
		List<Enchere> encheres = new ArrayList<>();
		Enchere e = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllEnchere);
				while (rs.next()) {
					e = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere").toLocalDate(), rs.getInt("montant_enchere"));
					encheres.add(e);
				}
				stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return encheres;
	}

	@Override
	public void delete(int id) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(deleteEnchere);
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

	@Override //DO NOT USE
	public List<Enchere> selectDateEnCours(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override //DNOT USE
	public List<Enchere> selectUnsellArticle() {
		// TODO Auto-generated method stub
		return null;
	}

}