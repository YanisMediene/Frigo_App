package com.tse.app.model;
import java.util.List;

public class RecipeDetails {
	
	// Identifiant de la recette
    private int id;
    
    // Titre de la recette
    private String title;
    
    // Nombre de portions de la recette
    private int servings;
    
    // Temps de préparation de la recette en minutes
    private int readyInMinutes;
    
    // Liste des ingrédients détaillés de la recette
    private List<Ingredient> extendedIngredients;
    
    // Instructions de préparation de la recette
    private String instructions;
    
    // Nombre d'ingrédients utilisés dans la recette
    private int usedIngredientCount;
    
    // Nombre d'ingrédients manquants dans la recette
    private int missedIngredientCount;
    
    // Score de santé associé à la recette
    private int healthScore;
    
    // Getters et setters
	public int getId() {
		return id;
	}
	public int getHealthScore() {
		return healthScore;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	
	public int getReadyInMinutes() {
		return readyInMinutes;
	}
	public void setReadyInMinutes(int readyInMinutes) {
		this.readyInMinutes = readyInMinutes;
	}
	
	// Méthode pour convertir le temps de préparation en heures et minutes
	public String prepTime(int readyInMinutes) {
		int hours = readyInMinutes / 60;
		int minutes = readyInMinutes - hours*60;
		String phrase = "";
		if (hours != 0)
			phrase += hours + " hours";
		if (minutes != 0)
			if (hours != 0)
				phrase += " and ";
			phrase += minutes + " minutes";
		return phrase;
	}
	
	// Suite et fin getters et setters
	
	public List<Ingredient> getExtendedIngredients() {
		return extendedIngredients;
	}
	public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
		this.extendedIngredients = extendedIngredients;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public int getUsedIngredientCount() {
		return usedIngredientCount;
	}
	public void setUsedIngredientCount(int usedIngredientCount) {
		this.usedIngredientCount = usedIngredientCount;
	}
	public int getMissedIngredientCount() {
		return missedIngredientCount;
	}
	public void setMissedIngredientCount(int missedIngredientCount) {
		this.missedIngredientCount = missedIngredientCount;
	}

}