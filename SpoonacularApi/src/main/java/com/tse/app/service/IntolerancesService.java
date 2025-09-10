package com.tse.app.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tse.app.model.Ingredient;
import com.tse.app.util.ConnectionDB;
import com.tse.app.view.UserSettingsFrame;

public class IntolerancesService {
	
	/**
     * Met à jour les intolérances d'un utilisateur dans la base de données.
     *
     * intolerances Les intolérances de l'utilisateur sous forme de chaîne de caractères.
     * userId L'identifiant de l'utilisateur.
     * return true si la mise à jour a réussi, false sinon.
     */
	
	public static boolean setIntolerances(String intolerances, int userId) {
		try (Connection connection = ConnectionDB.getConnection()) {
			String insertIntolerancesSql = "UPDATE users SET intolerances = ? WHERE user_id = ?";
			try (PreparedStatement insertIntolerancesStatement = connection.prepareStatement(insertIntolerancesSql)) {
				insertIntolerancesStatement.setString(1, intolerances);
				insertIntolerancesStatement.setInt(2, userId);
				insertIntolerancesStatement.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * Récupère les intolérances d'un utilisateur depuis la base de données.
     *
     * userId L'identifiant de l'utilisateur.
     * return Une chaîne de caractères représentant les intolérances de l'utilisateur.
     */
	
	public static String getIntolerances(int user_id) {
		String intolerances="000000000000";
		String getIntolerancesSql = "SELECT intolerances FROM users WHERE user_id = ?";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(getIntolerancesSql)) {
			preparedStatement.setInt(1, user_id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while (resultSet.next()) {
					intolerances = resultSet.getString("intolerances");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return intolerances;
	}
}
