package fr.eni.encheres.dal;

import java.util.List;

public interface ObjetsEnchereDAO<T> {
	
	public void insert();
	public T selectById();
	public List<T> selectAll();
	public void delete();

}
