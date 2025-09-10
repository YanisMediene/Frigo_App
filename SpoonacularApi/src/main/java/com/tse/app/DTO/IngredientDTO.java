package com.tse.app.DTO;

import java.util.Date;
import java.util.Objects;

public class IngredientDTO {
	
	// Identifiant de l'utilisateur auquel l'ingrédient est associé
	private int userId;
	
	// Identifiant de l'ingrédient
	private int ingredientId;
	
	// Nom de l'ingrédient
	private String name;
	
	// Chemin d'accès à l'image de l'ingrédient
	private String image;
	
	// Quantité de l'ingrédient
	private Double amount;
	
	// Identifiant de l'unité de mesure de la quantité
	private int unitId;
	
	// Date de péremption de l'ingrédient
	private Date peremptionDate;
	
	// Constructeur avec tous les paramètres
	public IngredientDTO(int userId, int ingredientId, String name, String image, Double amount, int unitId,
			Date peremptionDate) {
		super();
		this.userId = userId;
		this.ingredientId = ingredientId;
		this.name = name;
		this.image = image;
		this.amount = amount;
		this.unitId = unitId;
		this.peremptionDate = peremptionDate;
	}
	
	// Constructeur par défaut
	public IngredientDTO() {
	}
	
	// Getters et Setters pour chaque propriété

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(double d) {
		this.amount = d;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public Date getPeremptionDate() {
		return peremptionDate;
	}

	public void setPeremptionDate(Date peremptionDate) {
		this.peremptionDate = peremptionDate;
	}
//

	// Méthode générée automatiquement pour calculer le code de hachage basé sur les propriétés de l'objet
	@Override
	public int hashCode() {
		return Objects.hash(amount, image, ingredientId, name, peremptionDate, unitId, userId);
	}

	 // Méthode générée automatiquement pour comparer deux objets de type IngredientDTO
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientDTO other = (IngredientDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(image, other.image)
				&& ingredientId == other.ingredientId && Objects.equals(name, other.name)
				&& Objects.equals(peremptionDate, other.peremptionDate) && unitId == other.unitId
				&& userId == other.userId;
	}

	
	// Méthode générée automatiquement pour obtenir une représentation textuelle de l'objet
	@Override
	public String toString() {
		return "IngredientDTO [userId=" + userId + ", ingredientId=" + ingredientId + ", name=" + name + ", image="
				+ image + ", amount=" + amount + ", unitId=" + unitId + ", peremptionDate=" + peremptionDate + "]";
	}
	
	
}
