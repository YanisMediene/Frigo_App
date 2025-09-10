package com.tse.app.model;

import java.util.Objects;

public class Ingredient {
		// Identifiant de l'ingrédient
		private int id;
		
		 // Nom de l'ingrédient
		private String name;
		
		// Chemin d'accès à l'image de l'ingrédient
		private String image;
		
		// Mesures associées à l'ingrédient
		private Measures measures;
		
		// Quantité de l'ingrédient
		private Double amount;
		
		 // Unité de mesure de la quantité de l'ingrédient
		private String unit;
		
		// Constructeur avec les paramètres obligatoires (id, name, image)
		public Ingredient(int id, String name, String image) {
			super();
			this.id = id;
			this.name = name;
			this.image = image;
		}
		
		// Constructeur avec les paramètres obligatoires (name, image)
		public Ingredient(String name, String image) {
			super();
			this.name = name;
			this.image = image;
		}
		
		// Constructeur par défaut
		public Ingredient() {
			super();
		}
		
		// Getters et Setters pour chaque propriété
		public int getIngredientId() {
			return id;
		}
		public String getUnit() {
			return unit;
		}
		public void setIngredientId(int ingredientId) {
			this.id = ingredientId;
		}
		public String getName() {
			return name;
		}
		public Measures getMeasures() {
			return measures;
		}
		public Double getAmount() {
			return amount;
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
		
		// Méthode générée automatiquement pour calculer le code de hachage basé sur les propriétés de l'objet
		@Override
		public int hashCode() {
			return Objects.hash(image, id, name);
		}
		
		// Méthode générée automatiquement pour comparer deux objets de type Ingredient
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ingredient other = (Ingredient) obj;
			return Objects.equals(image, other.image) && id == other.id
					&& Objects.equals(name, other.name);
		}
		
		// Méthode générée automatiquement pour obtenir une représentation textuelle de l'objet
		@Override
		public String toString() {
			return "Ingredient [ingredientId=" + id + ", name=" + name + ", image=" + image + "]";
		}
}
