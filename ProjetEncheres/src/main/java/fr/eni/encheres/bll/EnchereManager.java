package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.ObjetsEnchereDAO;

public class EnchereManager {

	private ObjetsEnchereDAO<Enchere> enchereDAO;

	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	
	public void addEnchere(int idUser, int idArticle, LocalDateTime dateEnchere, int montant) throws DALException {
		Enchere e = new Enchere(idUser, idArticle, dateEnchere, montant);
		enchereDAO.insert(e);
	}
	
	public Enchere selectEnchere(int id) throws DALException {
		Enchere e = enchereDAO.selectByIdFull(id);
		return e;
	}
	
	public List<Enchere> selectAllEnchere() throws DALException{
		List<Enchere> encheres = new ArrayList<>();
		encheres = enchereDAO.selectAllDiscret();
		return encheres;
	}
	
	public void deleteEnchere() {
		
	}
}
