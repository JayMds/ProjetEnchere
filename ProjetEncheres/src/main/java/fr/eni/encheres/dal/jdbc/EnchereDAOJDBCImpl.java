package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class EnchereDAOJDBCImpl implements ObjetsEnchereDAO<Enchere> {
	private final String insertEnchere= "INSERT into 'ENCHERES' (no_utilisateur, no_article, date_enchere, montant_enchere) values(?, ?, ?, ?);";
	private final String selectByIdEnchere = "SELECT `no_utilisateur`, `no_article`, `date_enchere`, `montant_enchere` FROM `ENCHERES` WHERE`no_article` =?; ";
	private final String selectAllEnchere= "SELECT * from 'ENCHERES'; ";
	private final String deleteEnchere = "DELETE from 'ENCHERES' WHERE no_article = ?;";
	private final String updateEnchere = "UPDATE 'ENCHERES' SET 'no_utilsateur'=?, 'date_enchere'=?, 'montant_enchere'=? WHERE 'no_article'=?";
	@Override
	public Enchere insert(Enchere e) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(insertEnchere);
			pstmt.setInt(1, e.getNoUtilisateur());
			pstmt.setInt(2, e.getNoArticle());
			pstmt.setObject(3, e.getDateEnchere());
			pstmt.setInt(4, e.getMontant());
			
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println(rowsInserted + " Article inséré");
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;		
	}

	@Override
	public Enchere selectByIdFull(int id) throws DALException {
		Enchere e = null;
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdEnchere);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				e = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getObject("date_enchere", LocalDateTime.class), rs.getInt("montant_enchere"));
				
			
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
	public List<Enchere> selectAllDiscret() throws DALException {
		List<Enchere> encheres = new ArrayList<>();
		Enchere e = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllEnchere);
				while (rs.next()) {
					e = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getObject("date_enchere", LocalDateTime.class), rs.getInt("montant_enchere"));
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
	
	@Override
	public void update(Enchere e, boolean fullOrNot) {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(updateEnchere);
			pstmt.setInt(1, e.getNoUtilisateur());
			pstmt.setObject(2, e.getDateEnchere());
			pstmt.setInt(3, e.getMontant());
			pstmt.setInt(4, e.getNoArticle());
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected> 0) {
				System.out.println(rowsAffected+ " Article inséré");
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

	/*
	 * DO NOT USE
	 * 
	 */
	@Override 
	public List<Enchere> selectDateEnCours() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Enchere> selectUnsellArticle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Enchere selectByIdDiscret(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Enchere> selectAllFull() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enchere verificationLogin(String a, String b) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enchere verificationPseudo(String login) throws BusinessException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

}
