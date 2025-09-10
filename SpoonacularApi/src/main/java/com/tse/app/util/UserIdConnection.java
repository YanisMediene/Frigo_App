package com.tse.app.util;

/**
 * Classe utilitaire pour stocker l'ID de l'utilisateur connecté.
 */

public class UserIdConnection {
	private static int userId;

	/**
     * Obtient l'ID de l'utilisateur connecté.
     *
     * return L'ID de l'utilisateur connecté.
     */
	
	public static int getUserId() {
		return userId;
	}

	/**
     * Définit l'ID de l'utilisateur connecté.
     *
     * userId Nouvel ID de l'utilisateur connecté.
     */
	
	public static void setUserId(int userId) {
		UserIdConnection.userId = userId;
	}
	
}
