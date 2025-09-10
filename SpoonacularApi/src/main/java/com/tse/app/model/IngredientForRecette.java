package com.tse.app.model;

public class IngredientForRecette {
	
	// Identifiant de l'ingrédient associé à la recette
	private int idIngredient;
	
	// Quantité de l'ingrédient dans la recette
	private Double amount;
	
	 // Unité de mesure de la quantité de l'ingrédient
	private String unit;
	
	// Nom de l'ingrédient
	private String name;
	
	// Constructeur avec les paramètres obligatoires (idIngredient, name, amount, unit)
	public IngredientForRecette(int idIngredient,String name, Double amount, String unit) {
		super();
		this.idIngredient = idIngredient;
		this.amount = amount;
		this.unit = unit;
		this.name = name;
	}
	
	// Méthode générée automatiquement pour obtenir une représentation textuelle de l'objet
	@Override
	public String toString() {
		return "IngredientForRecette [idIngredient=" + idIngredient + ", amount=" + amount + ", unit=" + unit
				+ ", name=" + name + "]";
	}
	
	// Getters et Setters pour chaque propriété
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getIdIngredient() {
		return idIngredient;
	}

	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}


