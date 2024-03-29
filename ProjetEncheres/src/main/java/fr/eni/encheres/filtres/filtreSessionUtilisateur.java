package fr.eni.encheres.filtres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.servlet.CookieUtils;

/**
 * Servlet Filter implementation class filtreSessionUtilisateur
 */
@WebFilter(urlPatterns = "/*",
			dispatcherTypes = {
								DispatcherType.REQUEST, 
								DispatcherType.INCLUDE, 
								DispatcherType.FORWARD,
								DispatcherType.ERROR
							   }
			
          )
public class filtreSessionUtilisateur extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public filtreSessionUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		Utilisateur user = (Utilisateur) httpRequest.getSession().getAttribute("connectedUser");
		
	 // System.out.println(httpRequest.getServletPath());
		if(user==null) {
		
			//System.out.println("user null");
			if (httpRequest.getRequestURI().endsWith(".png")) {
			    chain.doFilter(request, response);
			}
			if (httpRequest.getRequestURI().endsWith(".jpg")) {
			    chain.doFilter(request, response);
			}
			
			if(checkPage("servletarticle", httpRequest) || 
					checkPage("vente", httpRequest ) || 
					checkPage("encherir", httpRequest )  ||
					checkPage("encheres", httpRequest ) ||
					checkPage("utilisateur", httpRequest )||
					checkPage("suppression", httpRequest )	||
					checkPage("servletModificationEnchere", httpRequest )
					
					) {
				response.setCharacterEncoding("UTF-8" );	
				System.out.println("blocage");
				response.setCharacterEncoding("UTF-8" );
				String message="Vous devez être connecté pour accéder à cette page"; 
				httpResponse.addCookie( CookieUtils.SetCookie("message", message, 10)  );
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/connexion"); 
			
			
			}else {
				
				
				
				chain.doFilter(request, response);
			}
			
			
		}else {
			//System.out.println("utilisateur connecté");
			chain.doFilter(request, response);
		}
		
		
		
	}

	private boolean checkPage(String page, HttpServletRequest httpRequest) {
		return httpRequest.getServletPath().toLowerCase().contains(page);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
