package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.servlet.BusinessException;

public class UtilisateurDAOImpl implements ObjetsEnchereDAO<Utilisateur> {
	String insert = "INSERT INTO `UTILISATEURS`(`pseudo`, `nom`, `prenom`, `email`, `telephone`, `rue`, `code_postal`, `ville`, `mot_de_passe`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	String delete = "DELETE FROM `UTILISATEURS` WHERE no_Utilisateur = ?;";
	String selectByIdFull = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur =?";
	String selectByIdCredit = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur =?";
	String selectByIdDiscret = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville,  FROM UTILISATEURS WHERE no_utilisateur =?";
	String selectAllDiscret = "SELECT pseudo,nom, prenom, email,telephone,rue,codePostal,ville from UTILISATEURS";
	String selectAllFull = "SELECT pseudo,nom, prenom, email,telephone,rue,codePostal,ville from UTILISATEURS";
	String SELECT_BY_LOG = "SELECT `no_utilisateur`, `pseudo`, `nom`, `prenom`, `email`, `telephone`, `rue`, `code_postal`, `ville`, `mot_de_passe`, `credit`, `administrateur` FROM `UTILISATEURS` WHERE `email` = ? And `mot_de_passe`=?";
	String VERIF_PSEUDO = "SELECT PSEUDO FROM UTILISATEURS WHERE PSEUDO=?";
	String updateUtilisateur = "UPDATE `UTILISATEURS` SET `pseudo`=?,`nom`=?,`prenom`=?,`email`=?,`telephone`=?,`rue`=?,`code_postal`=?,`ville`=? WHERE no_utilisateur=?";
	String updateUtilisateurmdp = "UPDATE `UTILISATEURS` SET `mot_de_passe`=? WHERE no_utilisateur=?";
	String UPDATE_CREDIT = "UPDATE `UTILISATEURS` SET `credit`=? WHERE no_utilisateur=?;";
	@Override
	public Utilisateur insert(Utilisateur utilisateurCourant) throws DALException {
		int rowsInserted = -1;
		try (Connection cnx = ConnectionProvider.getConnection();) {

			PreparedStatement pstmt = cnx.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
			ResultSet rs = pstmt.getGeneratedKeys();
			int index = 1;
			pstmt.setString(index++, utilisateurCourant.getPseudo());
			pstmt.setString(index++, utilisateurCourant.getNom());
			pstmt.setString(index++, utilisateurCourant.getPrenom());
			pstmt.setString(index++, utilisateurCourant.getEmail());
			pstmt.setString(index++, utilisateurCourant.getTelephone());
			pstmt.setString(index++, utilisateurCourant.getRue());
			pstmt.setString(index++, utilisateurCourant.getCodePostal());
			pstmt.setString(index++, utilisateurCourant.getVille());
			pstmt.setString(index++, utilisateurCourant.getMotDePasse());

			rs = pstmt.getGeneratedKeys();

			rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new user was inserted successfully!");
			}
			pstmt.close();
		} catch (SQLException e) {
			// throw new DALException("");
			e.printStackTrace();
		}

		if (rowsInserted == -1) {
			throw new DALException("erreur lors de l'insert");
		}
		return utilisateurCourant;
	}

	@Override
	public Utilisateur selectByIdFull(int no_utilisateur) throws DALException {

		Utilisateur UtilisateurCourant = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdFull);
			pstmt.setInt(1, no_utilisateur);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_Postal");
				String ville = rs.getString("ville");
				String motDePasse = rs.getString("mot_De_Passe");
				String credit = rs.getString("credit");
				Boolean administrateur = rs.getBoolean("administrateur");

				UtilisateurCourant = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
						motDePasse, credit, administrateur);

			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);

		}
		UtilisateurCourant.setNoUtilisateur(no_utilisateur);
		return UtilisateurCourant;

	}

	@Override
	public Utilisateur selectByIdDiscret(int no_utilisateur) throws DALException {

		Utilisateur UtilisateurCourant = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdFull);
			pstmt.setInt(1, no_utilisateur);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_Postal");
				String ville = rs.getString("ville");

				UtilisateurCourant = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville);

			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);

		}

		return UtilisateurCourant;

	}

	@Override
	public List<Utilisateur> selectAllDiscret() throws DALException {

		List<Utilisateur> ListUtilisateurs = new ArrayList<Utilisateur>();

		try (Connection cnx = ConnectionProvider.getConnection();) {
			Statement stmt = cnx.createStatement();

			ResultSet rs = stmt.executeQuery(selectAllDiscret);

			while (rs.next()) {
				Utilisateur u = new Utilisateur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
				ListUtilisateurs.add(u);
				stmt.close();

			}

		} catch (SQLException e) {

			throw new DALException(e);

		}

		return ListUtilisateurs;

	}

	@Override
	public List<Utilisateur> selectAllFull() throws DALException {

		List<Utilisateur> ListUtilisateurs = new ArrayList<Utilisateur>();

		try (Connection cnx = ConnectionProvider.getConnection();) {
			Statement stmt = cnx.createStatement();

			ResultSet rs = stmt.executeQuery(selectAllFull);

			while (rs.next()) {
				Utilisateur u = new Utilisateur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getBoolean(11));
				ListUtilisateurs.add(u);
				stmt.close();

			}

		} catch (SQLException e) {

			throw new DALException(e);

		}

		return ListUtilisateurs;

	}

	@Override
	public void delete(int id) throws DALException {
		int rowsAffected = 0;

		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(delete);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println(rowsAffected + " Utilisateur suprimmé");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DALException(e);
		}
/**
		if (rowsAffected == 0) {
			
			throw new DALException("Erreur lors de la suppression");
		}
**/
	}

	public Utilisateur verificationLogin(String login, String motdepasse) throws DALException {

		// motdepasse= HashClass.sha1(motdepasse);

		try (Connection cnx = ConnectionProvider.getConnection();) {

			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_LOG);
			pstmt.setString(1, login);
			pstmt.setString(2, motdepasse);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int noUser = rs.getInt("no_utilisateur");
				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_Postal");
				String ville = rs.getString("ville");
				// String motDePasse = rs.getString("mot_De_Passe");
				String credit = rs.getString("credit");
				Boolean administrateur = rs.getBoolean("administrateur");

				return new Utilisateur(noUser, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit,
						administrateur);

			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DALException(e);

		}
	}

	public Utilisateur verificationPseudo(String pseudo) throws DALException {

		Utilisateur UtilisateurCourant = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection();) {

			PreparedStatement pstmt = cnx.prepareStatement(VERIF_PSEUDO);
			pstmt.setString(1, pseudo);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String pseudoTest = rs.getString("pseudo");

				UtilisateurCourant = new Utilisateur(pseudoTest);

			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);

		}

		return UtilisateurCourant;

	}

	@Override
	public void update(Utilisateur utilisateurCourant, boolean infosOrMdp) throws DALException {
		int index = 1;
		try (Connection cnx = ConnectionProvider.getConnection();) {

			
				System.out.println(utilisateurCourant.toString());
				PreparedStatement pstmt = cnx.prepareStatement(updateUtilisateur);
				pstmt.setString(index++, utilisateurCourant.getPseudo());
				
				pstmt.setString(index++, utilisateurCourant.getNom());
				pstmt.setString(index++, utilisateurCourant.getPrenom());
				pstmt.setString(index++, utilisateurCourant.getEmail());
				pstmt.setString(index++, utilisateurCourant.getTelephone());
				pstmt.setString(index++, utilisateurCourant.getRue());
				pstmt.setString(index++, utilisateurCourant.getCodePostal());
				pstmt.setString(index++, utilisateurCourant.getVille());
				pstmt.setInt(index++, utilisateurCourant.getNoUtilisateur());
				int rowsAffected = pstmt.executeUpdate();
				System.out.println(rowsAffected + " utilisateur modifié");
				pstmt.close();
				if (infosOrMdp) {
					PreparedStatement pstmt1 = cnx.prepareStatement(updateUtilisateurmdp);
					pstmt1.setString(1, utilisateurCourant.getMotDePasse());
					pstmt1.setInt(2, utilisateurCourant.getNoUtilisateur());
					int rowsAffected1 = pstmt1.executeUpdate();
					System.out.println(rowsAffected1 + " mdp modifié");
					pstmt1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}



	@Override
	public String VerifCreditUtilisateur(int creditUtilisateur) throws DALException {

		String credit = null;

		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectByIdCredit);
			pstmt.setInt(1, creditUtilisateur);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				credit = rs.getString("credit");

			}

			pstmt.close();

		} catch (SQLException e) {
			throw new DALException(e);

		}

		return credit;

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
	public void VerifCreditSuperieurEncheres(int montantDeniereEnchere, int creditVerifierBDD)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void VerifMontantMinimum(int test2, int montantDeniereEnchere) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public int VerifMontantEnchere(int idArticle) throws DALException {
		// TODO Auto-generated method stub
		return 0;
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
	public List<Utilisateur> selectDateEnCours() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utilisateur> selectUnsellArticle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertIntoList(Enchere newEnchere) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utilisateur nouveauSolde(Utilisateur utilisateur, int montant) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();) {
	
			
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CREDIT);
			pstmt.setInt(1, montant);			
			pstmt.setInt(2, utilisateur.getNoUtilisateur());
					
			int rowsAffected = pstmt.executeUpdate();
			System.out.println(rowsAffected + " utilisateur modifié");
			pstmt.close();
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
		
		
		
	
		return selectByIdFull(utilisateur.getNoUtilisateur());
	}

	
}
