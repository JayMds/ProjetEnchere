package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;




public class UtilisateurManager {
	
	
	private static int TAILLE_MINI_PSEUDO=3;
	
	
	
	a su
	
	private ObjetsEnchereDAO<Utilisateur> utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public Utilisateur ajouterutilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse) throws BusinessException, DALException {

		BusinessException businessException = new BusinessException();
		this.validerPseudo(pseudo,businessException);
		this.validerNom(nom,businessException);
		this.validerPrenom(prenom,businessException);
		this.validerEmail(email,businessException);
		this.validerTelephone(telephone,businessException);
		this.validerRue(rue,businessException);
		this.validercodePostal(codePostal,businessException);
		this.validerVille(ville,businessException);
		this.validermotDePasse(motDePasse,businessException);

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

	private void validerPseudo(String pseudo,BusinessException businessException)  {
		

				
				
				
				if(pseudo.isBlank()^pseudo.isEmpty() ) {
					businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
				}
			

				if(pseudo.length()<TAILLE_MINI_PSEUDO)  {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_PSEUDO_TAILLE_MINI);
				}
		
				
				
		
				
				
				
				
				

	}
		
		
	
	

	private void validerNom(String nom,BusinessException businessException) {
		
		if(nom.isBlank()^nom.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}


	}

	private void validerPrenom(String prenom,BusinessException businessException) {
		
		if(prenom.isBlank()^prenom.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validerEmail(String email,BusinessException businessException) {

		if(email.isBlank()^email.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validerTelephone(String telephone,BusinessException businessException) {

		if(telephone.isBlank()^telephone.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validerRue(String rue,BusinessException businessException) {

		if(rue.isBlank()^rue.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validercodePostal(String codePostal,BusinessException businessException) {

		if(codePostal.isBlank()^codePostal.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validerVille(String ville,BusinessException businessException) {

		if(ville.isBlank()^ville.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	private void validermotDePasse(String motdepasse,BusinessException businessException) {

		if(motdepasse.isBlank()^motdepasse.isEmpty() ) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VIDE_OU_BLANC);
		}
	}

	public List<Utilisateur> selectionnerTousLesUtilisateurs() throws BusinessException, DALException {
		return this.utilisateurDAO.selectAll();
	}

	public Utilisateur selectionnerUnUtilisateurAvecSonID(int id) throws BusinessException, DALException {
		return this.utilisateurDAO.selectById(id);
	}

}
