package com.tse.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.tse.app.DTO.IngredientDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.UserIngredient;
import com.tse.app.util.ConnectionDB;

public class IngredientService {
	
	//Gestion des exceptions � traiter au fur et � mesure de l'avancement du projet + impl�menter les tests qui y conviennent
	
	
	//CONSEIL : lors de l'impl�mentation au niveau du front, il vaut mieux travailler dans la plupart des cas avec un objet
	//IngredientDTO pour �viter tout conflit au niveau des diff�rentes op�rations ;).

	// TEST : Valide
	// R�cup�rer un ingr�dient � partir de son id (null si n'existe pas)
	public static Ingredient getIngredient(int ingredientId) {
		String sql = "SELECT * FROM ingredients WHERE ingredient_id = ?";
		// Verification de l'existance du meme ingredient en verifiant si l'id de
		// l'ingredient existe deja
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement ingVerifStatement = connection.prepareStatement(sql)) {
			ingVerifStatement.setInt(1, ingredientId);
			try (ResultSet resultSet = ingVerifStatement.executeQuery()) {
				if (resultSet.next()) {
					int ingrId = resultSet.getInt("ingredient_id");
					String ingredientName = resultSet.getString("name");
					String image = resultSet.getString("image");
					return new Ingredient(ingrId, ingredientName, image);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// TEST : Valide
	// Recuperer tous les ingredients
	public static ArrayList<Ingredient> getAllIngredients() {
		ArrayList<Ingredient> ingredientsList = new ArrayList<>();

		String sql = "SELECT * FROM ingredients";

		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int ingredientId = resultSet.getInt("ingredient_id");
					String ingredientName = resultSet.getString("name");
					String image = resultSet.getString("image");
					ingredientsList.add(new Ingredient(ingredientId, ingredientName, image));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ingredientsList;
	}

	// TEST : Valide
	// Ajouter un ingredient � la base (check si ingredient_id deja existant sinon
	// ajouter l'ingredient)
	public static void addIngredient(Ingredient ingredient) {
		if (getIngredient(ingredient.getIngredientId()) == null) {
			try (Connection connection = ConnectionDB.getConnection()) {
				// Ajouter l'ingr�dient � la table ingredients
				String insertIngredientSql = "INSERT INTO ingredients (ingredient_id,name,image) VALUES (?,?,?)";
				try (PreparedStatement insertIngredientStatement = connection.prepareStatement(insertIngredientSql)) {
					insertIngredientStatement.setInt(1, ingredient.getIngredientId());
					insertIngredientStatement.setString(2, ingredient.getName());
					insertIngredientStatement.setString(3, ingredient.getImage());
					insertIngredientStatement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//TEST : Valide
	// Recuperer tous les ingredients d'un utilisateur specifique
	public static ArrayList<IngredientDTO> getAllIngredientsForUser(int userId) {
		ArrayList<IngredientDTO> ingredientsList = new ArrayList<>();

		String sql = "SELECT i.ingredient_id, i.name, i.image, ui.amount, ui.unit_id, ui.peremption_date  "
				+ "FROM user_ingredients as ui " + "JOIN ingredients as i ON ui.ingredient_id = i.ingredient_id "
				+ "WHERE ui.user_id = ?";

		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int ingredientId = resultSet.getInt("ingredient_id");
					String ingredientName = resultSet.getString("name");
					String image = resultSet.getString("image");
					Double amount = resultSet.getDouble("amount");
					int unitId = resultSet.getInt("unit_id");
					Date peremptionDate = resultSet.getDate("peremption_date");
					ingredientsList.add(new IngredientDTO(userId, ingredientId, ingredientName, image, amount, unitId,
							peremptionDate));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ingredientsList;
	}

	// TEST : Valide
	// ingredients user_ingredients -- il faut envoyer ingredient comme objet
	//checker si l'ingredient est deja associ� ou pas au user avant de l'inserer
		public static void addIngredientToUser(IngredientDTO ingredientDTO) {
		addIngredient(new Ingredient(ingredientDTO.getIngredientId(),ingredientDTO.getName(),ingredientDTO.getImage()));
		// Ajouter une entr�e dans la table user_ingredients
		if (getIngredientUserById(ingredientDTO.getUserId(),ingredientDTO.getIngredientId()) == null) {
			String insertUserIngredientSql = "INSERT INTO user_ingredients (user_id, ingredient_id, amount, unit_id, peremption_date) "
					+ "VALUES (?,?,?,?,?)";
			try (Connection connection = ConnectionDB.getConnection();
					PreparedStatement insertUserIngredientStatement = connection
							.prepareStatement(insertUserIngredientSql)) {
				insertUserIngredientStatement.setInt(1, ingredientDTO.getUserId());
				insertUserIngredientStatement.setInt(2, ingredientDTO.getIngredientId());
				insertUserIngredientStatement.setDouble(3, ingredientDTO.getAmount());
				insertUserIngredientStatement.setInt(4, ingredientDTO.getUnitId());
				insertUserIngredientStatement.setDate(5, new java.sql.Date(ingredientDTO.getPeremptionDate().getTime()));
				insertUserIngredientStatement.executeUpdate();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Tu as d�j� cet ingredient dans la liste !");
		}
	}

	//TEST : Valide
	// la recuperation des ingredients d un utilisateur qui matchent avec le nom de
	// l'ingredient en input
	public static ArrayList<IngredientDTO> getIngredientsUserByName(int userId, String name) {
		ArrayList<IngredientDTO> listIngredients = new ArrayList<>();
		String checkAssociationSql = "SELECT i.ingredient_id, i.name, i.image, ui.amount, ui.unit_id, ui.peremption_date  "
				+ "FROM user_ingredients as ui " + "JOIN ingredients as i ON ui.ingredient_id = i.ingredient_id "
				+ "WHERE ui.user_id = ? and i.name like ?";

		try (Connection connection = ConnectionDB.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(checkAssociationSql)) {
				preparedStatement.setInt(1, userId);
				preparedStatement.setString(2, "%" + name + "%");
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						int ingredientId = resultSet.getInt("ingredient_id");
						String ingredientName = resultSet.getString("name");
						String image = resultSet.getString("image");
						Double amount = resultSet.getDouble("amount");
						int unitId = resultSet.getInt("unit_id");
						Date peremptionDate = resultSet.getDate("peremption_date");
						listIngredients.add(new IngredientDTO(userId, ingredientId, ingredientName, image, amount,
								unitId, peremptionDate));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listIngredients;
	}
	
	
	//TEST : En cours
	// getIngredientUser by Id 
	public static IngredientDTO getIngredientUserById(int userId, int ingredientId) {
		String checkAssociationSql = "SELECT i.ingredient_id, i.name, i.image, ui.amount, ui.unit_id, ui.peremption_date  "
				+ "FROM user_ingredients as ui " + "JOIN ingredients as i ON ui.ingredient_id = i.ingredient_id "
				+" WHERE ui.user_id = ? AND ui.ingredient_id = ?";
		try (Connection connection = ConnectionDB.getConnection()) {
			try (PreparedStatement checkAssociationStatement = connection.prepareStatement(checkAssociationSql)) {
				checkAssociationStatement.setInt(1, userId);
				checkAssociationStatement.setInt(2, ingredientId);
				try (ResultSet resultSet = checkAssociationStatement.executeQuery()) {
					if (resultSet.next()) {
						String ingredientName = resultSet.getString("name");
						String image = resultSet.getString("image");
						Double amount = resultSet.getDouble("amount");
						int unitId = resultSet.getInt("unit_id");
						Date peremptionDate = resultSet.getDate("peremption_date");
						return new IngredientDTO(userId, ingredientId, ingredientName, image, amount,
								unitId, peremptionDate);
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getIngredientUserUnitById(int userId, int ingredientId) {
		String checkAssociationSql = "SELECT units.unitName FROM user_ingredients JOIN units"+
				" ON user_ingredients.unit_id = units.unit_id WHERE user_ingredients.ingredient_id=?;";
		try (Connection connection = ConnectionDB.getConnection()) {
			try (PreparedStatement checkAssociationStatement = connection.prepareStatement(checkAssociationSql)) {
				checkAssociationStatement.setInt(1, ingredientId);
				try (ResultSet resultSet = checkAssociationStatement.executeQuery()) {
					if (resultSet.next()) {
						String unitName = resultSet.getString("unitName");
						return unitName;
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	//TEST : Valide
	// on peut update que deux valeurs c'est l'amount et peremption date
	// on a juste besoin de l'ingredientDTO avec les nouvelles valeurs (l'id de l'ingredient � modifier pour le user
	// avec l'id X est d�j� dans le DTO)
	
	//REMARQUE ! Si on fait le traitement des exceptions, traiter le cas de amount n�gatif !!!
	public static void updateIngredientInUser(IngredientDTO ingredientUpdated,int userId) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String updateAssociationSql = "UPDATE user_ingredients SET amount = ?,peremption_date = ?, unit_id = ? "
					+ " WHERE user_id = ? AND ingredient_id = ?";
			try (PreparedStatement updateAssociationStatement = connection.prepareStatement(updateAssociationSql)) {
				updateAssociationStatement.setDouble(1, ingredientUpdated.getAmount());
				updateAssociationStatement.setDate(2,new java.sql.Date(ingredientUpdated.getPeremptionDate().getTime()));
				updateAssociationStatement.setInt(3, ingredientUpdated.getUnitId());
				updateAssociationStatement.setInt(4, ingredientUpdated.getUserId());
				updateAssociationStatement.setInt(5, ingredientUpdated.getIngredientId());
				updateAssociationStatement.executeUpdate();
				System.out.println("Ingr�dient mis � jour dans le frigo de l'utilisateur.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("L'association entre l'utilisateur et l'ingr�dient n'existe pas.");
		}
	}

	
	//Enlever pour un utilisateur un ingr�dient specifique sur sa liste
	
	//Normalement lors du remove, c'est un objet DTO qui est deja dans la liste du user qui sera remove (donc pas 
	//la peine de checker si il n'y a pas de diff�rence dans l'amount, peremption ...
	public static void removeIngredientFromUser(IngredientDTO ingredient) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String removeAssociationSql = "DELETE FROM user_ingredients WHERE user_id = ? AND ingredient_id = ?";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.setInt(1, ingredient.getUserId());
				removeAssociationStatement.setInt(2, ingredient.getIngredientId());
				removeAssociationStatement.executeUpdate();
				System.out.println("Ingr�dient supprim� du frigo de l'utilisateur.");
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("L'association entre l'utilisateur et l'ingr�dient
				// n'existe pas.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("L'association entre l'utilisateur et l'ingr�dient
			// n'existe pas.");
		}
	}

	//ATTENTION : ne jamais removeAllIngredients avant removeAllIngredientsFromUser
	
	//TEST : valide
	public static void removeAllIngredients() {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String removeAssociationSql = "DELETE FROM ingredients";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.executeUpdate();
				System.out.println("Ingr�dient supprim� du frigo de l'utilisateur.");
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("L'association entre l'utilisateur et l'ingr�dient
				// n'existe pas.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("L'association entre l'utilisateur et l'ingr�dient
			// n'existe pas.");
		}
	}

	//TEST : valide
	public static void removeAllIngredientsFromUser(int userId) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String removeAssociationSql = "DELETE FROM user_ingredients WHERE user_id = ?";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.setInt(1, userId);
				removeAssociationStatement.executeUpdate();
				System.out.println("Ingr�dient supprim� du frigo de l'utilisateur.");
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("L'association entre l'utilisateur et l'ingr�dient
				// n'existe pas.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("L'association entre l'utilisateur et l'ingr�dient
			// n'existe pas.");
		}
	}
}