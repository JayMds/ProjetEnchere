package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class UtilisateurDAOImpl implements ObjetsEnchereDAO<Utilisateur> {
	String insert = "INSERT INTO `UTILISATEURS`(`pseudo`, `nom`, `prenom`, `email`, `telephone`, `rue`, `code_postal`, `ville`, `mot_de_passe`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	String delete = "DELETE from UTILISATEURS where no_Utilisateur = ?;";
	String selectByIdFull = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur =?";
	String selectByIdDiscret = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville,  FROM UTILISATEURS WHERE no_utilisateur =?";
	String selectAllDiscret = "SELECT pseudo,nom, prenom, email,telephone,rue,codePostal,ville from UTILISATEURS";
	String selectAllFull = "SELECT pseudo,nom, prenom, email,telephone,rue,codePostal,ville from UTILISATEURS";
	

	@Override

	public void insert(Utilisateur utilisateurCourant) throws DALException {
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
			//throw new DALException("");
			e.printStackTrace();
		}

		if (rowsInserted == -1) {
			throw new DALException("erreur lors de l'insert");
		}
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
				String motDePasse = rs.getString("motDePasse");
				String credit = rs.getString("credit");
				Boolean administrateur = rs.getBoolean("administrateur");

				UtilisateurCourant = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
						motDePasse, credit, administrateur);

			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);

		}

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

			ResultSet rs = stmt.executeQuery(selectAllDiscret);

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
				System.out.println(rowsAffected + " Article suprimmé");
			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);
		}

		if (rowsAffected == 0) {
			throw new DALException("Erreur lors de la suppression");
		}

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
	

}
