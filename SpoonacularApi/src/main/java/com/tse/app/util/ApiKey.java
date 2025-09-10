package com.tse.app.util;

/**
 * Classe représentant la clé API utilisée pour accéder à des services de Spoonacular.
 */

public class ApiKey {
	// Clé API statique
	static String apiKey;

	/**
     * Constructeur par défaut qui initialise la clé API.
     */
	public ApiKey() {
		super();
		this.apiKey = "e57d13460c6843df937374463cd462db";
	}
	
	 /**
     * Obtient la clé API.
     *
     * return La clé API utilisée pour accéder à un service externe.
     */
	public static String getApiKey() {
		return apiKey;
	}

}
