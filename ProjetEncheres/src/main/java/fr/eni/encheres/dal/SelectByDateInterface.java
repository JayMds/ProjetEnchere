package fr.eni.encheres.dal;

import java.time.LocalDate;
import java.util.List;


public interface SelectByDateInterface<T> {

	public List<T> selectDateEnCours(LocalDate date);
	public List<T> selectUnsellArticle();

}
