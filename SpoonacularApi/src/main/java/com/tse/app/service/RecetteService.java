package com.tse.app.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tse.app.DTO.IngredientDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.IngredientForRecette;
import com.tse.app.model.Recette;
import com.tse.app.model.RecipeDetails;
import com.tse.app.model.User;
import com.tse.app.util.ConnectionDB;
import com.tse.app.view.RecInfoCard;
import com.google.gson.Gson;
public class RecetteService {

	// Gestion des exceptions � traiter au fur et � mesure de l'avancement du projet
	// + impl�menter les tests qui y conviennent

	// CONSEIL : lors de l'impl�mentation au niveau du front, il vaut mieux
	// travailler dans la plupart des cas avec un objet
	// IngredientDTO pour �viter tout conflit au niveau des diff�rentes op�rations
	// ;).

	// TEST : Valide
	// R�cup�rer un ingr�dient � partir de son id (null si n'existe pas)
	public static Recette getRecette(int recetteId) {
		String sql = "SELECT * FROM recettes WHERE recette_id = ?";
		// Verificationn de l'existance de la recette en verifiant si l'id de
		// la recette existe deja
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement recVerifStatement = connection.prepareStatement(sql)) {
			recVerifStatement.setInt(1, recetteId);
			try (ResultSet resultSet = recVerifStatement.executeQuery()) {
				if (resultSet.next()) {
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					return new Recette(recetteId, title, image);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return null;
	}

	// TEST : Valide
	// Recuperer toutes les recettes
	public static ArrayList<Recette> getAllRecettes() {
		ArrayList<Recette> recettesList = new ArrayList<>();

		String sql = "SELECT * FROM recettes";

		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int recetteId = resultSet.getInt("recette_id");
					String recetteTitle = resultSet.getString("title");
					String image = resultSet.getString("image");
					recettesList.add(new Recette(recetteId, recetteTitle, image));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recettesList;
	}

	// TEST : Valide
	// Ajouter une recette � la base (check si recette_id deja existant sinon
	// ajouter l'ingredient)
	public static void addRecette(Recette recette) {
		if (getRecette(recette.getRecetteId()) == null) {
			try (Connection connection = ConnectionDB.getConnection()) {
				// Ajouter la recette à la table recettes
				String insertRecetteSql = "INSERT INTO recettes (recette_id,image,title) VALUES (?,?,?)";
				try (PreparedStatement insertRecetteStatement = connection.prepareStatement(insertRecetteSql)) {
					insertRecetteStatement.setInt(1, recette.getRecetteId());
					insertRecetteStatement.setString(2, recette.getImage());
					insertRecetteStatement.setString(3, recette.getTitle());
					insertRecetteStatement.executeUpdate();
					System.out.println("la recette :" + recette.getTitle() + " a bien ajouter a la base de donnees");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("la recette :" + recette.getTitle() + " est deja dans la base de donnees");

	}

	// TEST : Valide
	// la recuperation de la liste des recettes ajout�s par l'utilisateur
	// (myListRecette)
	public static ArrayList<RecetteDTO> getAllRecettesForUser(int userId) {
		ArrayList<RecetteDTO> recettesList = new ArrayList<>();
		String sql = "SELECT r.recette_id, r.image, r.title, ur.favorite FROM user_recettes as ur "
				+ "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ?";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idRecette = resultSet.getInt("recette_id");
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					boolean favorite = resultSet.getBoolean("favorite");
					recettesList.add(new RecetteDTO(userId, idRecette, title, image, favorite));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recettesList;
	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		String sql = "SELECT * from users";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idUser = resultSet.getInt("user_id");
					String username = resultSet.getString("username");
					String password = resultSet.getString("password");
					String intolerances = resultSet.getString("intolerances");
					users.add(new User(idUser, username, password, intolerances));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static void removeUsers() {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise à jour de l'association dans la table user_recetttes
			String removeAssociationSql = "TRUNCATE TABLE users";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	public static void addUsers(ArrayList<User> users) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Ajouter la recette à la table recettes
			String insertRecetteSql = "INSERT INTO users (user_id,username,password,intolerances) VALUES (?,?,?,?)";
			for(int i=0;i<users.size();i++) {
				try (PreparedStatement insertRecetteStatement = connection.prepareStatement(insertRecetteSql)) {
					insertRecetteStatement.setInt(1, users.get(i).getUserId());
					insertRecetteStatement.setString(2, users.get(i).getUsername());
					insertRecetteStatement.setString(3, users.get(i).getPassword());
					insertRecetteStatement.setString(4, users.get(i).getIntolerances());
					insertRecetteStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	public static void addFavRecette(int userId,RecetteDTO recette) {
		String sql = "SELECT recette_id FROM user_fav_recettes WHERE user_id=? AND recette_id=?";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, recette.getIdRecette());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
		            System.out.println("Recette deja dans les favoris");
		        } else {
		        	try (Connection connection2 = ConnectionDB.getConnection()) {
		    			String insertRecetteSql = "INSERT INTO user_fav_recettes (user_id,recette_id) VALUES (?,?)";
		    			try (PreparedStatement insertRecetteStatement = connection2.prepareStatement(insertRecetteSql)) {
		    				insertRecetteStatement.setInt(1, userId);
		    				insertRecetteStatement.setInt(2, recette.getIdRecette());
		    				insertRecetteStatement.executeUpdate();
		    			}
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
		        }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	} 

	
	public static void removeFavRecette(int userId,RecetteDTO recette) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise à jour de l'association dans la table user_recetttes
			String removeAssociationSql = "DELETE FROM user_fav_recettes WHERE user_id = ? AND recette_id = ?";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.setInt(1, userId);
				removeAssociationStatement.setInt(2, recette.getIdRecette());
				removeAssociationStatement.executeUpdate();
				System.out.println("La recette est bien supprimee des favoris.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	// TEST : Valide
	// Cette m�thode traduit l'addition d'un element dans Recette User
	// On verifie l'existance de la recette pour l'utilisateur avant de l'ajouter
	public static void addRecetteToUser(RecetteDTO newRecette) {
		// Ajouter la recette à la table recettes
		addRecette(new Recette(newRecette.getIdRecette(), newRecette.getTitle(), newRecette.getImage()));
		if (getRecetteUserById(newRecette.getIdUser(), newRecette.getIdRecette()) == null) {
			try (Connection connection = ConnectionDB.getConnection()) {
				// Ajouter une entrée dans la table user_recettes
				String insertUserRecetteSql = "INSERT INTO user_recettes (user_id, recette_id, favorite, inProgress) VALUES (?, ?, ?,?)";
				try (PreparedStatement insertUserRecetteStatement = connection.prepareStatement(insertUserRecetteSql)) {
					insertUserRecetteStatement.setInt(1, newRecette.getIdUser());
					System.out.println(newRecette.getIdUser());
					insertUserRecetteStatement.setInt(2, newRecette.getIdRecette());
					System.out.println(newRecette.getIdRecette());
					insertUserRecetteStatement.setBoolean(3, newRecette.isFavorite());
					insertUserRecetteStatement.setBoolean(4, false);
					insertUserRecetteStatement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("deja existant !");

	}

	//TEST : Valide
	// pour ajouter ou supprimer une recette de la liste des favories
	// On g�re deux cas : 
	//si on ajoute un ingredient au favoris alors qu'il n'est pas dans les favoris : dans ce cas on l'ajoute automatiquement avec favori=true
	//si on switch un ingredient vers favoris ou non
	
	//ATTENTION ! L'�tat de newRecette ici est l'�tat avant le switch de favoris au true/false
	public static void switchFavorieStateRecette(RecetteDTO newRecette) {
		try (Connection connection = ConnectionDB.getConnection()) {
			String updateAssociationSql = "UPDATE user_recettes SET favorite = ? WHERE user_id = ? AND recette_id = ?";
			try (PreparedStatement updateAssociationStatement = connection.prepareStatement(updateAssociationSql)) {
				updateAssociationStatement.setBoolean(1, !(newRecette.isFavorite()));
				updateAssociationStatement.setInt(2, newRecette.getIdUser());
				updateAssociationStatement.setInt(3, newRecette.getIdRecette());
				updateAssociationStatement.executeUpdate();
				if(newRecette.isFavorite()==true) {
					removeFavRecette(newRecette.getIdUser(),newRecette);
				}
				else {
					addFavRecette(newRecette.getIdUser(),newRecette);
				}
			}
		} catch (Exception e) {
			
		}
	}
	public static void switchToInProgressStateRecette(RecetteDTO newRecette) {
		if (RecetteService.getRecetteUserById(newRecette.getIdUser(),newRecette.getIdRecette())==null){
			addRecetteToUser(newRecette);
		}
		try (Connection connection = ConnectionDB.getConnection()) {
			String updateAssociationSql = "UPDATE user_recettes SET inProgress = ? WHERE user_id = ? AND recette_id = ?";
			try (PreparedStatement updateAssociationStatement = connection.prepareStatement(updateAssociationSql)) {
				updateAssociationStatement.setBoolean(1, true);
				updateAssociationStatement.setInt(2, newRecette.getIdUser());
				updateAssociationStatement.setInt(3, newRecette.getIdRecette());
				updateAssociationStatement.executeUpdate();
			}
		} catch (Exception e) {
			
		}
	}
	public static void switchBackInProgressStateRecette(RecetteDTO newRecette) {
		if (RecetteService.getRecetteUserById(newRecette.getIdUser(),newRecette.getIdRecette())==null){
			addRecetteToUser(newRecette);
		}
		try (Connection connection = ConnectionDB.getConnection()) {
			String updateAssociationSql = "UPDATE user_recettes SET inProgress = ? WHERE user_id = ? AND recette_id = ?";
			try (PreparedStatement updateAssociationStatement = connection.prepareStatement(updateAssociationSql)) {
				updateAssociationStatement.setBoolean(1, false);
				updateAssociationStatement.setInt(2, newRecette.getIdUser());
				updateAssociationStatement.setInt(3, newRecette.getIdRecette());
				updateAssociationStatement.executeUpdate();
			}
		} catch (Exception e) {
			
		}
	}
	public static ArrayList<RecetteDTO> getInProgessRecettesForUser(int userId) {
		ArrayList<RecetteDTO> recettesList = new ArrayList<>();
		String sql = "SELECT r.recette_id, r.image, r.title FROM user_recettes as ur "
		        + "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ? and ur.inProgress = TRUE";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idRecette = resultSet.getInt("recette_id");
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					;
					recettesList.add(new RecetteDTO(userId, idRecette, title, image, true));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recettesList;
	}
	
	// TEST : Valide
	// la recuperation de la liste des favories
	public static ArrayList<RecetteDTO> getFavorieRecettesForUser(int userId) {
		ArrayList<RecetteDTO> recettesList = new ArrayList<>();
		String sql = "SELECT r.recette_id, r.image, r.title FROM user_recettes as ur "
		        + "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ? and ur.favorite = TRUE";
		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idRecette = resultSet.getInt("recette_id");
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					recettesList.add(new RecetteDTO(userId, idRecette, title, image, true));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recettesList;
	}

	//TEST : Valide
	// la fonction pour faire la recherche d une recetteUser
	public static ArrayList<RecetteDTO> getRecettesUserByName(int userId, String name) {
		ArrayList<RecetteDTO> listRecettes = new ArrayList<>();
		String sql = "SELECT r.recette_id, r.image, r.title, ur.favorite FROM user_recettes as ur "
		        + "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ? and r.title LIKE ?";


		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, "%" + name + "%");
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idRecette = resultSet.getInt("recette_id");
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					boolean favorite = resultSet.getBoolean("favorite");
					listRecettes.add(new RecetteDTO(userId, idRecette, title, image, favorite));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listRecettes;
	}

	//TEST : Valide
	// la fonction pour faire la recherche d une recetteUser
	public static RecetteDTO getRecetteUserById(int userId, int recetteId) {
		String sql = "SELECT r.recette_id, r.image, r.title, ur.favorite FROM user_recettes as ur "
				+ "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ? and ur.recette_id = ?";

		try (Connection connection = ConnectionDB.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, recetteId);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String title = resultSet.getString("title");
						String image = resultSet.getString("image");
						boolean favorite = resultSet.getBoolean("favorite");
						return new RecetteDTO(userId, recetteId, title, image, favorite);
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
	// la fonction pour faire la recherche d une recette apartaire de la liste des
	// favories
	public static ArrayList<RecetteDTO> getRecetteUserByNameFromFavories(int userId, String name) {
		ArrayList<RecetteDTO> listRecettes = new ArrayList<>();
		String sql = "SELECT r.recette_id, r.image, r.title, ur.favorite FROM user_recettes as ur "
		        + "JOIN recettes as r ON ur.recette_id = r.recette_id WHERE ur.user_id = ? and ur.favorite = TRUE and r.title LIKE ?";


		try (Connection connection = ConnectionDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, "%" + name + "%");
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int idRecette = resultSet.getInt("recette_id");
					String title = resultSet.getString("title");
					String image = resultSet.getString("image");
					listRecettes.add(new RecetteDTO(userId, idRecette, title, image, true));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listRecettes;
	}

	//TEST : En cours
	// remove from myList propre � l'utilisateur
	public static void removeRecetteFromUser(RecetteDTO recette) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise à jour de l'association dans la table user_recetttes
			String removeAssociationSql = "DELETE FROM user_recettes WHERE user_id = ? AND recette_id = ?";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.setInt(1, recette.getIdUser());
				removeAssociationStatement.setInt(2, recette.getIdRecette());
				removeAssociationStatement.executeUpdate();
				System.out.println("La recette est bien supprimee.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ATTENTION : ne jamais removeAllRecettes avant removeAllRecettesFromUser (ces
	// deux fonctions permettent de vider les tables
	// recettes et user_recettes.

	// TEST : valide
	public static void removeAllRecettes() {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String removeAssociationSql = "DELETE FROM recettes";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.executeUpdate();
				System.out.println("Suppression des recettes ...");
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

	// TEST : valide
	public static void removeAllRecettesFromUser(int userId) {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise � jour de l'association dans la table user_ingredients
			String removeAssociationSql = "DELETE FROM user_recettes WHERE user_id = ?";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.setInt(1, userId);
				removeAssociationStatement.executeUpdate();
				System.out.println("Suppression des recettes dans la liste de l'utilisateur ...");
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
	
	public static List<IngredientForRecette> getListIngredientRecette(RecetteDTO recette){
		List<IngredientForRecette> ingredientsRecette = new ArrayList<>();
		String recipeDetails = RecInfoCard.getRecipeDetails(recette.getIdRecette());
		Gson gson = new Gson();
		RecipeDetails recipe = gson.fromJson(recipeDetails, RecipeDetails.class);
		List<Ingredient>  liste=recipe.getExtendedIngredients();
		for (Ingredient ingredient : liste) {
			int id=ingredient.getIngredientId();
			Double amount=ingredient.getAmount();
			String unit = ingredient.getUnit();
			String name = ingredient.getName();
			IngredientForRecette ing = new IngredientForRecette(id,name,amount,unit);
			ingredientsRecette.add(ing);
        }
		return ingredientsRecette;
	}
	
	//on doit la mettre dans ingredient Service
	public static void updateIngredientAmount(int userId, int ingredientId,Double amountToChange) {
		IngredientDTO ingredientUser= IngredientService.getIngredientUserById(userId,ingredientId);	
		if(amountToChange<=0.0) {
			IngredientService.removeIngredientFromUser(ingredientUser);
		}
		else {
			ingredientUser.setAmount(amountToChange);
			IngredientService.updateIngredientInUser(ingredientUser,userId);
		}
	}
	
	public static void endRecette(RecetteDTO recette) {
		List<IngredientForRecette> ingredientsRecette = getListIngredientRecette(recette);
		
		for (IngredientForRecette ingredient : ingredientsRecette) {
			//amout dans ce cas c est la quantite que on doit supprimer 
			updateIngredientAmount( recette.getIdUser(),  ingredient.getIdIngredient(), ingredient.getAmount());
		}
	}
	public static void resetAll() {
		try (Connection connection = ConnectionDB.getConnection()) {
			// Mise à jour de l'association dans la table user_recetttes
			String removeAssociationSql = "TRUNCATE TABLE user_recettes";
			try (PreparedStatement removeAssociationStatement = connection.prepareStatement(removeAssociationSql)) {
				removeAssociationStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String removeAssociationSql2 = "TRUNCATE TABLE user_fav_recettes";
			try (PreparedStatement removeAssociationStatement2 = connection.prepareStatement(removeAssociationSql2)) {
				removeAssociationStatement2.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
}
