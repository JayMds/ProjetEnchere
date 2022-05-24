package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOImpl implements ObjetsEnchereDAO<Object> {
	
	@Override
	public void insert(Object objet) throws DALException {
		
		
		
	}



	@Override
	public Utilisateur selectById(int no_utilisateur) throws DALException {

		List<Utilisateur> Utilisateurs = new ArrayList<Utilisateur>();
		Utilisateur UtilisateurCourant = new Utilisateur();
		Connection cnx = null;
		Statement stmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			stmt = cnx.createStatement();

			String sql = "SELECT * FROM Utilisateurs WHERE noUtilisateur =?";

			PreparedStatement statement = cnx.prepareStatement(sql);
			statement.setInt(1, no_utilisateur);

			ResultSet rs = statement.executeQuery();

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

		} catch (SQLException e) {
			throw new DALException(e);

		}

		finally {
			close(cnx);
			close(stmt);

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


