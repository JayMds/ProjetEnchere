package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;
import fr.eni.encheres.servlet.BusinessException;

public class EnchereManager extends VerificationEnchereManager {

	private ObjetsEnchereDAO<Enchere> enchereDAO;

	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}

	public void addEnchere(int idUser, int idArticle, LocalDateTime dateEnchere, int montant)
			throws DALException, BusinessException {
		BusinessException exception = new BusinessException();
		this.validerNoUtilisateur(montant, exception);
		this.validerNoArticle(idArticle, exception);
		//this.validerDateEnchere(dateEnchere, exception);
		this.validerMonttantEnchere(montant, exception);
		if (!exception.hasErreurs()) {
			Enchere e = new Enchere(idUser, idArticle, dateEnchere, montant);
			enchereDAO.insert(e);
		}
		if (exception.hasErreurs()) {
			throw exception;
		}
	}

	public void VerifCreditSuperieurEncheres(int montantDeniereEnchere, int creditVerifierBDD)
			throws BusinessException {
		enchereDAO.VerifCreditSuperieurEncheres(montantDeniereEnchere, creditVerifierBDD);
	}

	public Enchere selectEnchere(int id) throws DALException {
		return this.enchereDAO.selectByIdFull(id);
	}

	public List<Enchere> selectAllEnchere() throws DALException {
		return this.enchereDAO.selectAllDiscret();
	}

	public void deleteEnchere(int id) throws DALException {
		this.enchereDAO.delete(id);
	}

	public void updateEnchere(Enchere e) throws DALException {
		
		this.enchereDAO.update(e, false);
	}

	public Enchere addEnchere(int idArticle, int montant) throws DALException {
		Enchere e = new Enchere(idArticle, montant);
		return this.enchereDAO.insert(e);

	}

	public int VerifMontantDerniereEncheres(int idArticle) throws DALException {
		return enchereDAO.VerifMontantEnchere(idArticle);

	}

	public void VerifMontantMini(int test2, int montantDeniereEnchere) throws BusinessException {
		enchereDAO.VerifMontantMinimum(test2, montantDeniereEnchere);
	}
	

	public Utilisateur validationOperationEncherir(int idArticle, Utilisateur encherisseur, int offre) throws DALException, BusinessException {
		
		//MANAGER INIT
		BusinessException businessException = new BusinessException();
		UtilisateurManager userManager = new UtilisateurManager();
		ArticleManager artManager = new ArticleManager();
		
		
		Article article = artManager.selectArticle(idArticle); 
		//System.out.println("no encherisseur:"+encherisseur.getNoUtilisateur());
		String creditVerifierBDD = userManager.VerifCreditUtilisateur(encherisseur.getNoUtilisateur()); //recupération des crédit utlisateur
		int creditUtilisateur = Integer.parseInt(creditVerifierBDD);
		
		
		//recupération desinfos de la dernière enchère
		int montantDeniereEnchere = VerifMontantDerniereEncheres(idArticle);
		Enchere derniereEnchere = selectEnchere(idArticle); 
		Utilisateur dernierEncherisseur = userManager.selectionnerToutesInformationsDunUtilisateurAvecSonID(derniereEnchere.getNoUtilisateur());
		dernierEncherisseur.setNoUtilisateur(derniereEnchere.getNoUtilisateur());
		
		//verification de l'offre
		verificationDateEnchere(businessException, article);		
		verificationMontantEnchereEtCreditUtilisateur(businessException, creditUtilisateur, montantDeniereEnchere);
		verificationOffreMinimum(offre, businessException, montantDeniereEnchere);
		
		Utilisateur encherisseurUpdated = null;
		 
		if(!businessException.hasErreurs()) {
				//System.out.println(encherisseur.getNoUtilisateur());
				Enchere newEnchere = new Enchere(encherisseur.getNoUtilisateur(), idArticle,LocalDateTime.now(), offre);
				
				//on debite l'encherisseur et on recupère son nouveau solde				 
				 encherisseurUpdated = userManager.debiter(encherisseur , offre); 
				
				//on recrédite l'ancien encherisseur	
				// System.out.println(dernierEncherisseur.getNoUtilisateur());
				userManager.crediter(dernierEncherisseur, derniereEnchere.getMontant() ); 
				
				
				//mise a jour de l'enchère
				//System.out.println(newEnchere.toString());
				updateEnchere(newEnchere);
				
				addListEnchere(newEnchere);
				
				
		}else {
			throw businessException; 
		}
	
		return encherisseurUpdated; 
		
	}

	private void addListEnchere(Enchere newEnchere) throws BusinessException {
		BusinessException exception = new BusinessException();
		this.validerNoUtilisateur(newEnchere.getNoUtilisateur(), exception);
		this.validerNoArticle(newEnchere.getNoArticle(), exception);
		this.validerDateEnchere(newEnchere.getDateEnchere(), exception);
		this.validerMonttantEnchere(newEnchere.getMontant(), exception);
		if (!exception.hasErreurs()) {
			
			enchereDAO.insertIntoList(newEnchere);
		}
		if (exception.hasErreurs()) {
			throw exception;
		}
		
	}

	private void verificationOffreMinimum(int offre, BusinessException businessException, int montantDeniereEnchere)
			throws BusinessException {
		if(offre < montantDeniereEnchere) {
			businessException.ajouterErreur(CodesResultatBLL.ENCHERE_INSUFISSANTE);
			throw businessException; 
		}
	}

	private void verificationMontantEnchereEtCreditUtilisateur(BusinessException businessException,
			int creditUtilisateur, int montantDeniereEnchere) throws BusinessException {
		if(creditUtilisateur < montantDeniereEnchere ) {
			businessException.ajouterErreur(CodesResultatBLL.SOLDE_INSUFISSANT);
			throw businessException; }
	}

	private void verificationDateEnchere(BusinessException businessException, Article article)
			throws BusinessException {
		LocalDateTime DateduJour = LocalDateTime.now(); 		
		
		if(DateduJour.compareTo(article.getDateFinEnchere())>0){		
			businessException.ajouterErreur(CodesResultatBLL.DATE_ENCHERE_DEPASSE);
			throw businessException; 
		
	}
	

}}
