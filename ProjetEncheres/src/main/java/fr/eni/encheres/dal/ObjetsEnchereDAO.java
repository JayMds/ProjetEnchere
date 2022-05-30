package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

public interface ObjetsEnchereDAO<T> extends SelectByDateInterface<T> {
	
	
	public T insert(T objet) throws DALException;
	
	
	
	public T selectByIdFull(int id) throws DALException;
	public T selectByIdDiscret(int id) throws DALException;
	public List<T> selectAllFull() throws DALException;
	public List<T> selectAllDiscret() throws DALException;
	public void delete(int id) throws DALException;
	public T verificationLogin(String a, String b) throws DALException;
	public T verificationPseudo(String login) throws BusinessException, DALException;
	public void update(T type, boolean fullOrNot);
}
	
	


	

