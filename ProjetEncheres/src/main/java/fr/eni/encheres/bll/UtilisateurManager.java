package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class UtilisateurManager {

	private ObjetsEnchereDAO<Utilisateur> utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public Utilisateur ajouterutilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse) throws BusinessException, DALException {

		BusinessException businessException = new BusinessException();
		this.validerPseudo(pseudo);
		;
		this.validerNom(nom);
		this.validerPrenom(prenom);
		this.validerEmail(email);
		this.validerTelephone(telephone);
		this.validerRue(rue);
		this.validercodePostal(codePostal);
		this.validerVille(ville);
		this.validermotDePasse(motDePasse);

		Utilisateur utilisateur = null;

		if (!businessException.hasErreurs()) {
			utilisateur = new Utilisateur();

			utilisateur.setPseudo(pseudo);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setTelephone(telephone);
			utilisateur.setRue(rue);
			utilisateur.setCodePostal(codePostal);
			utilisateur.setVille(ville);
			utilisateur.setMotDePasse(motDePasse);

			try {
				this.utilisateurDAO.insert(utilisateur);
			} catch (DALException e) {
				throw new DALException(e);
			}

		} else {
			throw businessException;

		}
		return utilisateur;
	}

	private void validerPseudo(String pseudo) {

	}

	private void validerNom(String nom) {

	}

	private void validerPrenom(String prenom) {

	}

	private void validerEmail(String email) {

	}

	private void validerTelephone(String telephone) {

	}

	private void validerRue(String rue) {

	}

	private void validercodePostal(String codePostal) {

	}

	private void validerVille(String ville) {

	}

	private void validermotDePasse(String motdepasse) {

	}

	public List<Utilisateur> selectionnerTousLesUtilisateurs() throws BusinessException, DALException {
		return this.utilisateurDAO.selectAll();
	}

	public Utilisateur selectionnerUnUtilisateurAvecSonID(int id) throws BusinessException, DALException {
		return this.utilisateurDAO.selectById(id);
	}

}
