package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class UtilisateurManager {

	private static int TAILLE_MINI = 3;
	private static int TAILLE_MAXI = 30;

	private static String REGEX_CODE_POSTAL = "/^(([0-8][0-9])|(9[0-5]))[0-9]{3}$/";
	private static String REGEX_TELEPHONE = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
	private static String REGEX_VILLE = "[^0-9]";
	private static String REGEX_MOT_DE_PASSE = "^(?=.*[0-9])(?=.*[az])(?=.*[AZ])(?=.*[@#$%^&-+=() ])(?=\\\\S+$).{8, 20}$";

	private ObjetsEnchereDAO<Utilisateur> utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO(); 
	}

	public Utilisateur ajouterutilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse) throws BusinessException, DALException {

		BusinessException businessException = new BusinessException();
		this.validerPseudo(pseudo, businessException);
		this.validerNom(nom, businessException);
		this.validerPrenom(prenom, businessException);
		this.validerEmail(email, businessException);
		this.validerTelephone(telephone, businessException);
		this.validerRue(rue, businessException);
		this.validercodePostal(codePostal, businessException);
		this.validerVille(ville, businessException);
		this.validermotDePasse(motDePasse, businessException);

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

	private void validerPseudo(String pseudo, BusinessException businessException) {

		if (pseudo.isBlank() ^ pseudo.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (pseudo.length() < TAILLE_MINI ^ pseudo.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	private void validerNom(String nom, BusinessException businessException) {

		if (nom.isBlank() ^ nom.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (nom.length() < TAILLE_MINI ^ nom.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	private void validerPrenom(String prenom, BusinessException businessException) {

		if (prenom.isBlank() ^ prenom.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (prenom.length() < TAILLE_MINI ^ prenom.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	private void validerEmail(String email, BusinessException businessException) {

		if (email.isBlank() ^ email.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!email.contains("@")) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}
	}

	private void validerTelephone(String telephone, BusinessException businessException) {

		if (telephone.isBlank() ^ telephone.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!telephone.matches(REGEX_TELEPHONE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	private void validerRue(String rue, BusinessException businessException) {

		if (rue.isBlank() ^ rue.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validercodePostal(String codePostal, BusinessException businessException) {

		if (codePostal.isBlank() ^ codePostal.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!codePostal.matches(REGEX_CODE_POSTAL)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	private void validerVille(String ville, BusinessException businessException) {

		if (ville.isBlank() ^ ville.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);

		}
		if (!ville.matches(REGEX_VILLE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	private void validermotDePasse(String motdepasse, BusinessException businessException) {

		if (motdepasse.isBlank() ^ motdepasse.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
		
		if (!motdepasse.matches(REGEX_MOT_DE_PASSE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_MDP);
		}
		
		
		
	}

	public List<Utilisateur> selectionnerTousLesUtilisateurs() throws BusinessException, DALException {
		return this.utilisateurDAO.selectAll();
	}

	public Utilisateur selectionnerUnUtilisateurAvecSonID(int id) throws BusinessException, DALException {
		return this.utilisateurDAO.selectById(id);
	}
}