package com.tse.app.model;

import java.util.ArrayList;

public class Frigo {
private ArrayList<Ingredient> liste;
	
	public Frigo() {
		liste = new ArrayList<Ingredient>();	
	}
	
	public ArrayList<Ingredient> getListe()
	{
		return liste;
	}
	
	public void addIngredient(Ingredient ingredient)  
	{
		liste.add(ingredient);
	}
	
	public void removeIngredient(Ingredient ingredient)
	{
		if (liste.contains(ingredient)){
			liste.remove(ingredient);
		}
	}

}
