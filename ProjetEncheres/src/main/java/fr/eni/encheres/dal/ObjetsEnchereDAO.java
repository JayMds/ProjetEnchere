package fr.eni.encheres.dal;

import java.util.List;

public interface ObjetsEnchereDAO<T> extends SelectByDateInterface<T> {
	
	
	public void insert(T objet) throws DALException;
	public T selectByIdFull(int id) throws DALException;
	public T selectByIdDiscret(int id) throws DALException;
	public List<T> selectAllFull() throws DALException;
	public List<T> selectAllDiscret() throws DALException;
	public void delete(int id) throws DALException;
	}
	
	


	

