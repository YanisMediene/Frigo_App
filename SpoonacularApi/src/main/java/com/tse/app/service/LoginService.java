package com.tse.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

import com.tse.app.model.User;
import com.tse.app.util.ConnectionDB;
import com.tse.app.util.UserIdConnection;

public class LoginService {
	
    public static boolean authenticate(User user) {
        try (Connection connection = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                try (ResultSet resultSet = statement.executeQuery()) {
                	while (resultSet.next()) {                    
                        UserIdConnection.setUserId(resultSet.getInt("user_id"));
                        return true;
                    }
					//ConnectionDB.closeConnection();
                	//System.out.println("Nom d'utilisateur : " + resultSet.getString("username"));
                    return false; // Si une ligne est trouvée, l'authentification est réussie
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de connexion ou d'exécution de la requête
            return false;
        }
    }
    
    public static boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, intolerances) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            // Paramètres de la requête préparée
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, "000000000000");
            // Exécutez la requête
            int rowsAffected = preparedStatement.executeUpdate();

            // Si une ligne est affectée, l'enregistrement a réussi
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean checkUsername(String username) {
    	boolean alreadyExist = false;
        try (Connection connection = ConnectionDB.getConnection()) {
            String sql = "SELECT username FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                	while (resultSet.next() && alreadyExist==false) {                    
                        String result=resultSet.getString("username");
                        if (username.equals(result)) 
                        	alreadyExist=true;
                    }
                	return alreadyExist;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de connexion ou d'exécution de la requête
            return false;
        }
    }
}
