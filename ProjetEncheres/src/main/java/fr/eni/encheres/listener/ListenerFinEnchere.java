package fr.eni.encheres.listener;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.servlet.BusinessException;

/**
 * Application Lifecycle Listener implementation class ListenerFinEnchere
 *
 */
@WebListener
public class ListenerFinEnchere implements ServletRequestListener {
	private static LocalDateTime dateDuJour =  LocalDateTime.now(); 
    /**
     * Default constructor. 
     */
    public ListenerFinEnchere() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0)  { 
        //
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0)  { 
    	
    	
        ArticleManager artManager = new ArticleManager(); 
        List<Article> listeArticle = null;
		try {
			listeArticle = artManager.selectUnsellArticle();
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DALException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
        
        for(Article article : listeArticle) {
			try {
				
				artManager.VerificationEtModificationFinEnchere(article, dateDuJour);
				
				//TODO crediter vendeur
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
}
