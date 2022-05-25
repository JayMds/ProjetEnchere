package fr.eni.encheres.dal;

import java.util.List;

public interface ObjetsEnchereDAO<T> {
	
	
	public void insert(T objet) throws DALException;
	public T selectById(int id) throws DALException;
	public List<T> selectAll() throws DALException;
	public void delete(int id) throws DALException;

	
}
