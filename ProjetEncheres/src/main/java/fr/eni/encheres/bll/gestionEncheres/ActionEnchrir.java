package fr.eni.encheres.bll.gestionEncheres;

import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public class ActionEnchrir {
	
	UtilisateurManager userManager = new UtilisateurManager(); 
	
		HttpSession session = request.getSession(true);
	 

		Utilisateur user = userManager.selectionnerInformationDiscret();
		request.setAttribute("user", user);
	
	
	
	
	
	
	
	
	Utilisateur u = new Utilisateur(, null, null, null, null, null, null, null, null, null, null, false)
	
	
	
	Enchere e = new Enchere();
	
	

}
