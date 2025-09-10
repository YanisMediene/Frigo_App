package com.tse.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe utilitaire pour établir et gérer la connexion à la base de données.
 */
public class ConnectionDB {
	// URL JDBC pour la connexion à la base de données
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/spoonacular";
	// Nom d'utilisateur de la base de données
    private static final String USERNAME = "root";
    // Mot de passe de la base de données
    private static final String PASSWORD = "testeur123";
    
    // Instance de connexion
    private static Connection connection;

    /**
     * Obtient une connexion à la base de données.
     *
     * return Une connexion à la base de données.
     * throws SQLException En cas d'échec de la connexion.
     */

    public static Connection getConnection() throws SQLException {
    	// Vérifie si la connexion existe ou si elle est fermée, puis la crée si nécessaire
        if(connection == null || connection.isClosed())
        	connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    	return connection;
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public static void closeConnection() {
        try {
        	 // Vérifie si la connexion est ouverte, puis la ferme si nécessaire
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
