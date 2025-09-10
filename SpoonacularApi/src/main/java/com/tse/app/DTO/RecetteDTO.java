package com.tse.app.DTO;

import java.util.Objects;

public class RecetteDTO {
	
	 // Identifiant de la recette
	private int idRecette;
	
	// Identifiant de l'utilisateur auquel la recette est associée
	private int idUser;
	
	 // Titre de la recette
	private String title;
	
	// Chemin d'accès à l'image de la recette
	private String image;
	
	// Indique si la recette est marquée comme favorite
	private boolean favorite;

	// Constructeur avec tous les paramètres
	public RecetteDTO(int idUser, int idRecette , String title, String image, boolean favorite) {
		super();
		this.idRecette = idRecette;
		this.idUser = idUser;
		this.title = title;
		this.image = image;
		this.favorite = favorite;
	}

	// Getters et Setters pour chaque propriété

	public int getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(int idRecette) {
		this.idRecette = idRecette;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	 // Méthode générée automatiquement pour calculer le code de hachage basé sur les propriétés de l'objet
	@Override
	public int hashCode() {
		return Objects.hash(favorite, idRecette, idUser, image, title);
	}

	// Méthode générée automatiquement pour comparer deux objets de type RecetteDTO
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecetteDTO other = (RecetteDTO) obj;
		return favorite == other.favorite && idRecette == other.idRecette && idUser == other.idUser
				&& Objects.equals(image, other.image) && Objects.equals(title, other.title);
	}

	// Méthode générée automatiquement pour obtenir une représentation textuelle de l'objet
	@Override
	public String toString() {
		return "RecetteDTO [idRecette=" + idRecette + ", idUser=" + idUser + ", title=" + title + ", image=" + image
				+ ", favorite=" + favorite + "]";
	}
	
	
	
	
}
