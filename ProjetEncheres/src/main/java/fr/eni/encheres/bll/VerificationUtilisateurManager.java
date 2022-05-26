package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;

public class VerificationUtilisateurManager  {

	private static int TAILLE_MINI = 3;
	private static int TAILLE_MAXI = 30;

	private static String REGEX_CODE_POSTAL = "/^(([0-8][0-9])|(9[0-5]))[0-9]{3}$/";
	private static String REGEX_TELEPHONE = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
	private static String REGEX_VILLE = "[^0-9]";
	private static String REGEX_MOT_DE_PASSE = "^(?=.*[0-9])(?=.*[az])(?=.*[AZ])(?=.*[@#$%^&-+=() ])(?=\\\\S+$).{8, 20}$";

	

	protected void validerPseudo(String pseudo, BusinessException businessException) {

		if (pseudo.isBlank() ^ pseudo.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (pseudo.length() < TAILLE_MINI ^ pseudo.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	protected void validerNom(String nom, BusinessException businessException) {

		if (nom.isBlank() ^ nom.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (nom.length() < TAILLE_MINI ^ nom.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	protected void validerPrenom(String prenom, BusinessException businessException) {

		if (prenom.isBlank() ^ prenom.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (prenom.length() < TAILLE_MINI ^ prenom.length() > TAILLE_MAXI) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}

	}

	protected void validerEmail(String email, BusinessException businessException) {

		if (email.isBlank() ^ email.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!email.contains("@")) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);
		}
	}

	protected void validerTelephone(String telephone, BusinessException businessException) {

		if (telephone.isBlank() ^ telephone.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!telephone.matches(REGEX_TELEPHONE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	protected void validerRue(String rue, BusinessException businessException) {

		if (rue.isBlank() ^ rue.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	protected void validercodePostal(String codePostal, BusinessException businessException) {

		if (codePostal.isBlank() ^ codePostal.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}

		if (!codePostal.matches(REGEX_CODE_POSTAL)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	protected void validerVille(String ville, BusinessException businessException) {

		if (ville.isBlank() ^ ville.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);

		}
		if (!ville.matches(REGEX_VILLE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ERREUR_FORMAT);

		}

	}

	protected void validermotDePasse(String motdepasse, BusinessException businessException) {

		if (motdepasse.isBlank() ^ motdepasse.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
		
		if (!motdepasse.matches(REGEX_MOT_DE_PASSE)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_MDP);
		}
		
		
		
		
		
	}



}
