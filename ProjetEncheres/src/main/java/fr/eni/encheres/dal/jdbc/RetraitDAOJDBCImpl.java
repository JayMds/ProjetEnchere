package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.servlet.BusinessException;

public class RetraitDAOJDBCImpl implements ObjetsEnchereDAO<Retrait> {
	private final String insertRetrait= "INSERT INTO `RETRAITS` (`no_article`, `rue`, `code_postal`, `ville`) VALUES(?, ?, ?, ?);";
	private final String selectByIdRetrait= "SELECT * FROM `RETRAITS` WHERE `no_article` = ?; ";
	private final String selectAllRetrait= "SELECT * FROM `RETRAITS`; ";
	private final String deleteRetrait = "DELETE FROM `RETRAITS` WHERE `no_article` = ?;";
	private final String updateEnchere = "UPDATE `RETRAITS` SET `no_utilisateur`=?, `date_enchere`=?, `montant_enchere`=? WHERE `no_article`=?";

	@Override
	public Retrait insert(Retrait r) throws DALException {
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
		return null;		
		
	}
	 
	@Override
	public Retrait selectByIdFull(int id) throws DALException {
		Retrait e = null;
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdRetrait);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();;
			
			if (rs.next()) 
			{
				e = new Retrait(rs.getInt("no_article"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
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
	public List<Retrait> selectAllFull() throws DALException {
		List<Retrait> retraits= new ArrayList<>();
		Retrait e = null;
		
			try(Connection cnx = ConnectionProvider.getConnection();){
				Statement stmt = cnx.createStatement();
				ResultSet rs = stmt.executeQuery(selectAllRetrait);
				while (rs.next()) {
					e = new Retrait(rs.getInt("no_utilisateur"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
					retraits.add(e);
				}
				stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return retraits;
	}
	
	@Override 
	public void delete(int id) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(deleteRetrait);
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
	public void update(Retrait r, boolean fullOrNot) {
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(updateEnchere);
			pstmt.setInt(1, r.getNoArticle());
			pstmt.setString(2, r.getRue());
			pstmt.setString(3, r.getCodePostal());
			pstmt.setString(4, r.getVille());
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected> 0) {
				System.out.println(rowsAffected+ " Article inséré");
			}
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	@Override //DO NOT USE
	public List<Retrait> selectDateEnCours() {
		return null;
	}
	@Override //DO NOT USE
	public List<Retrait> selectUnsellArticle() {
		return null;
	}
	@Override
	public Retrait selectByIdDiscret(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retrait> selectAllDiscret() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Retrait verificationLogin(String a, String b) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Retrait verificationPseudo(String login) throws BusinessException, DALException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Article> selectAchatEnCour(int no_utilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectAchatTermines(int no_utilisateur) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public int VerifMontantEnchere(int idArticle) throws DALException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void VerifCreditSuperieurEncheres(int montantDeniereEnchere, int creditVerifierBDD)
			throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	public List<Article> selectVenteUtilisateurEncour(int noUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectVenteUtilisateurNonDebute(int noUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectVenteUtilisateurTermine(int noUtilisateur) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String VerifCreditUtilisateur(int creditUtilisateur) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void VerifMontantMinimum(int test2, int montantDeniereEnchere) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Article> selectRechercheUser(String recherche, int noCategorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectAllRechercheUser(String recherche) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertIntoList(Enchere newEnchere) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utilisateur nouveauSolde(Utilisateur encherisseur, int montant) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
