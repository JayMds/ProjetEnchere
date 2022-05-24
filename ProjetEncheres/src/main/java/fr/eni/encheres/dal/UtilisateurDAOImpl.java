package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOImpl implements ObjetsEnchereDAO<Utilisateur> {

	String selectById = "SELECT * FROM Utilisateurs WHERE noUtilisateur =?";
	String insert = "INSERT INTO Utilisateurs (pseudo,nom, prenom, email,telephone,rue,codePostal,ville motDePasse,administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public void insert(Utilisateur utilisateurCourant) throws DALException {
		int rowsInserted = -1;
		try (Connection cnx = ConnectionProvider.getConnection();) {

			PreparedStatement pstmt = cnx.prepareStatement(insert);
			int index = 0;
			pstmt.setString(index++, utilisateurCourant.getPseudo());
			pstmt.setString(index++, utilisateurCourant.getNom());
			pstmt.setString(index++, utilisateurCourant.getPrenom());
			pstmt.setString(index++, utilisateurCourant.getEmail());
			pstmt.setString(index++, utilisateurCourant.getTelephone());
			pstmt.setString(index++, utilisateurCourant.getRue());
			pstmt.setString(index++, utilisateurCourant.getCodePostal());
			pstmt.setString(index++, utilisateurCourant.getVille());
			pstmt.setString(index++, utilisateurCourant.getMotDePasse());

			rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new user was inserted successfully!");
			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);
		}

		if (rowsInserted == -1) {
			throw new DALException("erreur lors de l'insert");
		}

	}

	@Override
	public Utilisateur selectById(int no_utilisateur) throws DALException {

		List<Utilisateur> Utilisateurs = new ArrayList<Utilisateur>();
		Utilisateur UtilisateurCourant = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(selectById);
			pstmt.setInt(1, no_utilisateur);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("codePostal");
				String ville = rs.getString("ville");

				UtilisateurCourant = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville);
				Utilisateurs.add(UtilisateurCourant);

			}
			pstmt.close();
		} catch (SQLException e) {
			throw new DALException(e);

		}

		return UtilisateurCourant;

	}

	@Override
	public List selectAll() {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public List selectDateEnCours(LocalDate date) {
		return null;
	}

	public static void close(AutoCloseable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
