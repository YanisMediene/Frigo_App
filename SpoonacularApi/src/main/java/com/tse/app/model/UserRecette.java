package com.tse.app.model;


public class UserRecette {
	
	// Identifiant de l'utilisateur associé à la recette
	private int userId;
	
	// Identifiant de la recette
	private int recetteId;
	
	// Indique si la recette est marquée comme favorite par l'utilisateur
	private boolean favorite;

	// Constructeur par défaut
	public UserRecette() {
	}

	// Constructeur avec les paramètres obligatoires (userId, recetteId, favorite)
	public UserRecette(int userId, int recetteId, boolean favorite) {
		super();
		this.userId = userId;
		this.recetteId = recetteId;
		this.favorite = favorite;
	}

	// Getters et setters
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRecetteId() {
		return recetteId;
	}

	public void setRecetteId(int recetteId) {
		this.recetteId = recetteId;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

}
