package fr.eni.encheres.dal;

import java.time.LocalDate;
import java.util.List;

public interface ObjetsEnchereDAO<T> {
	
	public void insert() throws DALException;
	public T selectById(int id) throws DALException;
	public List<T> selectAll() throws DALException;
	public void delete(int id) throws DALException;
	public List<T> selectDateEnCours(LocalDate date) throws DALException;
	
	

	
}
