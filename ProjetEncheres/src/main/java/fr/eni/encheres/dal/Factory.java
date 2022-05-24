package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

public class Factory {
	
	
	public static class factory{
		public static ObjetsEnchereDAO<Utilisateur> getUtilisateurDAO() {
			return new UtilisateurDAOImpl();
			test
		}

	}


}
