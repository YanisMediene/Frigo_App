package com.tse.app.model;

import java.util.Objects;

public class Recette {
	
	// Identifiant de la recette
	private int recetteId;
	
	// Titre de la recette
	private String title;
	
	// Chemin d'accès à l'image de la recette
	private String image;
	
	
	// Constructeur avec les paramètres obligatoires (recetteId, title, image)
	public Recette(int recetteId, String title, String image) {
		super();
		this.recetteId = recetteId;
		this.title = title;
		this.image = image;
	}

	// Constructeur avec les paramètres obligatoires (title, image)
	public Recette(String title, String image) {
		super();
		this.title = title;
		this.image = image;
	}

	// Constructeur par défaut
	public Recette() {
		super();
	}

	
	// Getters et Setters pour chaque propriété
	public int getRecetteId() {
		return recetteId;
	}


	public void setRecetteId(int recetteId) {
		this.recetteId = recetteId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	// Méthode générée automatiquement pour calculer le code de hachage basé sur les propriétés de l'objet
	@Override
	public int hashCode() {
		return Objects.hash(image, recetteId, title);
	}

	// Méthode générée automatiquement pour comparer deux objets de type Recette
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recette other = (Recette) obj;
		return Objects.equals(image, other.image) && recetteId == other.recetteId && Objects.equals(title, other.title);
	}

	
	
	
}
