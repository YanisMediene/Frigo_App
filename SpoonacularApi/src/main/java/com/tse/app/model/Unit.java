package com.tse.app.model;

public class Unit {
	
	// Identifiant de l'unité
	private int unitId;
	
	// Nom de l'unité
	private String unitName;
	
	
	// Constructeur avec les paramètres obligatoires (unitId, unitName)
	public Unit(int unitId, String unitName) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
	}
	
	// Constructeur avec les paramètres obligatoires (unitName)
	public Unit(String unitName) {
		super();
		this.unitName = unitName;
	}
	
	// Constructeur par défaut
	public Unit() {
		super();
	}
	
	
	// Getters et Setters pour chaque propriété
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
	
}
