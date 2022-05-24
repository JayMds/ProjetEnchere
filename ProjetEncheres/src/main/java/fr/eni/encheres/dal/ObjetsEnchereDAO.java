package fr.eni.encheres.dal;

import java.time.LocalDate;
import java.util.List;

public interface ObjetsEnchereDAO<T> {
	
	public void insert();
	public T selectById(int id);
	public List<T> selectAll();
	public void delete(int id);
	public List<T> selectDateEnCours(LocalDate date);

	
}
