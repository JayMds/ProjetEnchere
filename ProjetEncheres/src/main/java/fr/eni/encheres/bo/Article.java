package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Article {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEnchere;
	private LocalDate dateFinEnchere;
	private int prixInitial;
	private int prixVente;
	private int noVendeur;
	private int noCategorie;
	private int noAcheteur;
	
	public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEnchere,
			LocalDate dateFinEnchere, int prixInitial, int prixVente, int noVendeur, int noCategorie,
			int noAcheteur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noVendeur = noVendeur;
		this.noCategorie = noCategorie;
		this.noAcheteur = noAcheteur;
	}
	
	public Article(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere,
			int prixInitial, int prixVente, int noUtilisateur, int noCategorie, int noAcheteur) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noVendeur = noUtilisateur;
		this.noCategorie = noCategorie;
		this.noAcheteur = noAcheteur;
	}

	

	public Article(String nomArticle) {
		super();
		this.nomArticle = nomArticle;
	}


	
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDateDebutEnchere() {
		return dateDebutEnchere;
	}
	public void setDateDebutEnchere(LocalDate dateDebutEnchere) {
		this.dateDebutEnchere = dateDebutEnchere;
	}
	public LocalDate getDateFinEnchere() {
		return dateFinEnchere;
	}
	public void setDateFinEnchere(LocalDate dateFinEnchere) {
		this.dateFinEnchere = dateFinEnchere;
	}
	public int getPrixInitial() {
		return prixInitial;
	}
	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public int getNoVendeur() {
		return noVendeur;
	}

	public void setNoVendeur(int noVendeur) {
		this.noVendeur = noVendeur;
	}

	public int getNoCategorie() {
		return noCategorie;
	}
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public int getNoAcheteur() {
		return noAcheteur;
	}

	public void setNoAcheteur(int noAcheteur) {
		this.noAcheteur = noAcheteur;
	}
	
	
	
}
