package fr.eni.encheres.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAO implements ObjetsEnchereDAO<Object> {

	@Override
	public void insert() {
		
	}

	@Override
	public  Utilisateur selectById(int id) {
		return null;
	}

	@Override
	public List selectAll() {
		return null;
	}

	@Override
	public void delete(int id) {
		
	}

	@Override
	public List selectDateEnCours(LocalDate date) {
		return null;
	}

}
