package com.tse.app.model;

import java.util.Date;

public class UserIngredient {
	
	// Identifiant de l'utilisateur associé à l'ingrédient
	private int userId;
	
	// Identifiant de l'ingrédient
	private int ingredientId;
	
	// Quantité de l'ingrédient
	private Long amount;
	
	// Identifiant de l'unité de mesure
	private int unitId;
	
	// Date de péremption de l'ingrédient
	private Date peremptionDate;
	
	// Constructeur avec tous les paramètres obligatoires
	public UserIngredient(int userId, int ingredientId, Long amount, int unitId, Date peremptionDate) {
		super();
		this.userId = userId;
		this.ingredientId = ingredientId;
		this.amount = amount;
		this.unitId = unitId;
		this.peremptionDate = peremptionDate;
	}
	
	// Constructeur avec les paramètres obligatoires (amount, unitId, peremptionDate)
	public UserIngredient(Long amount, int unitId, Date peremptionDate) {
		super();
		this.amount = amount;
		this.unitId = unitId;
		this.peremptionDate = peremptionDate;
	}

	// Constructeur par défaut
	public UserIngredient() {
		super();
	}
	
	// Getters et setters
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
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
}
