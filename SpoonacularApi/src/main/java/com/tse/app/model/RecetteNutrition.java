package com.tse.app.model;

import java.util.List;

public class RecetteNutrition {
	
	// Informations nutritionnelles sur les calories
	private String calories;
	
	// Informations nutritionnelles sur les glucides (carbs)
    private String carbs;
    
    // Informations nutritionnelles sur les lipides (fat)
    private String fat;
    
    // Informations nutritionnelles sur les protéines
    private String protein;
    
    // Getters et setters
    
	public String getCalories() {
		return calories;
	}
	public String getCarbs() {
		return carbs;
	}
	public String getFat() {
		return fat;
	}
	public String getProteins() {
		return protein;
	}
}
