package com.tse.app.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tse.app.DTO.RecetteDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.Recette;
import com.tse.app.model.User;
import com.tse.app.util.UserIdConnection;

public class RecettesTest {
	ArrayList<User> users;
	@Before
	public void init() {
		users=RecetteService.getAllUsers();
		RecetteService.removeUsers();
		LoginService.registerUser(new User(1, "adnane", "1234"));
		LoginService.registerUser(new User(2, "toto", "1234"));
		LoginService.registerUser(new User(3, "tata", "1234"));
		RecetteService.addRecette(new Recette(1, "chicken a l'americaine", "chicken a l'americaine.png"));
		RecetteService.addRecette(new Recette(2, "pates napolitaines", "pates napolitaines.png"));
		RecetteService.addRecette(new Recette(3, "couscous merguez", "couscous merguez.png"));
		RecetteService.addRecetteToUser(new RecetteDTO(1, 1, "chicken a l'americaine", "chicken a l'americaine.png", false));
		RecetteService.addRecetteToUser(new RecetteDTO(2, 1, "chicken a l'americaine", "chicken a l'americaine.png", false));
		RecetteService.addRecetteToUser(new RecetteDTO(2, 2, "pates napolitaines", "pates napolitaines.png", true));
		RecetteService.addRecetteToUser(new RecetteDTO(3, 2, "pates napolitaines", "pates napolitaines.png", true));
		RecetteService.addRecetteToUser(new RecetteDTO(3, 3, "couscous merguez", "couscous merguez.png", false));
		RecetteService.addRecetteToUser(new RecetteDTO(3, 10, "sandwich merguez", "sandwich merguez.png", true));
		RecetteService.addRecetteToUser(new RecetteDTO(3, 20, "carbonara", "carbonara.png", false));
		RecetteService.addRecetteToUser(new RecetteDTO(3, 30, "recette nulle", "recette nulle.png", false));
	}

	@After
	public void close() {
		RecetteService.removeUsers();
		RecetteService.resetAll();
		RecetteService.removeAllRecettesFromUser(UserIdConnection.getUserId());
		RecetteService.removeAllRecettes();
		RecetteService.addUsers(users);
	}

	// Test fonction : Recette getRecette(int recetteId)
	// Test 1 : Recette existante dans la base
	@Test
	public void GetExistingRecette() {
		Recette existRecette = new Recette(1, "chicken a l'americaine", "chicken a l'americaine.png");
		assertEquals(existRecette, RecetteService.getRecette(1));
	}

	// Test 2 : Recette non existante dans la base
	@Test
	public void GetNonExistingRecette() {
		Recette nonExistRecette = new Recette(4, "test fruit de mer", "test fruit de mer.png");
		assertEquals(null, RecetteService.getRecette(4));
	}

	// Test fonction : void addRecette(Recette recette)
	// Test 1 : additionner une recette non existante
	@Test
	public void addNewRecette() {
		ArrayList<Recette> recettesList = new ArrayList<>();
		recettesList = RecetteService.getAllRecettes();
		Recette recetteToAdd = new Recette(5, "tarte au pomme", "tarte au pomme.png");
		RecetteService.addRecette(recetteToAdd);
		assertEquals(recetteToAdd, RecetteService.getRecette(5));
		assertEquals(recettesList.size() + 1, RecetteService.getAllRecettes().size());

	}

	// Test 2 : additionner un ingr�dient d�j� dans la base
	@Test
	public void addExistingIngredient() {
		ArrayList<Recette> recettesList = new ArrayList<>();
		recettesList = RecetteService.getAllRecettes();
		Recette recetteAlreadyAdded = new Recette(2, "pates napolitaines", "pates napolitaines.png");
		RecetteService.addRecette(recetteAlreadyAdded);
		assertEquals(recetteAlreadyAdded, RecetteService.getRecette(2));
		assertEquals(recettesList.size(), RecetteService.getAllRecettes().size());
	}

	// Test fonction : ArrayList<RecetteDTO> getAllRecettesForUser(int userId)
	// Test : r�cup�ration de toutes les recettes pour un utilisateur
	@Test
	public void getRecettesUserCheck() {
		ArrayList<RecetteDTO> recettesTest = new ArrayList<>();
		recettesTest = RecetteService.getAllRecettesForUser(1);
		assertEquals(recettesTest.size(), 1);
	}

	// Test fonction : void addRecetteToUser(RecetteDTO newRecette)
	// Test 1 : ajouter � l'utilisateur une recette qu'il a d�j�
	@Test
	public void addExistingIngredientToUser() {
		ArrayList<RecetteDTO> recettesList = new ArrayList<>();
		recettesList = RecetteService.getAllRecettesForUser(2);
		RecetteDTO recetteAlreadyAddedToUser = new RecetteDTO(2, 2, "pates napolitaines", "pates napolitaines.png",
				true);
		RecetteService.addRecetteToUser(recetteAlreadyAddedToUser);
		assertEquals(recetteAlreadyAddedToUser, RecetteService.getRecetteUserById(2, 2));
		assertEquals(recettesList.size(), RecetteService.getAllRecettesForUser(2).size());
	}

	// Test 2 : ajouter � l'utilisateur un nouvel ingr�dient
	@Test
	public void addNewIngredientToUser() {
		ArrayList<RecetteDTO> recettesList = new ArrayList<>();
		recettesList = RecetteService.getAllRecettesForUser(2);
		RecetteDTO recetteNewToUser = new RecetteDTO(2, 3, "couscous merguez", "couscous merguez.png", false);
		RecetteService.addRecetteToUser(recetteNewToUser);
		assertEquals(recettesList.size() + 1, RecetteService.getAllRecettesForUser(2).size());
	}

	// Test fonction : ArrayList<RecetteDTO> getFavorieRecettesForUser(int userId)
	@Test
	public void getFavorieRecettesForUserCheck() {
		assertEquals(RecetteService.getFavorieRecettesForUser(2).size(), 1);
	}

	// Test 2 : on a une recette non favorite et on l'ajoute aux favorite
	@Test
	public void SwitchToFavorieTrue() {
		ArrayList<RecetteDTO> recettesFavoris = new ArrayList<>();
		recettesFavoris = RecetteService.getFavorieRecettesForUser(3);
		// on va switch couscous merguez non favoris (favoris = false)
		RecetteService.switchFavorieStateRecette(RecetteService.getRecetteUserById(3, 3));
		// check si il a �t� ajout� dans les favoris
		assertEquals(RecetteService.getFavorieRecettesForUser(3).size(), recettesFavoris.size() + 1);
		assertEquals(RecetteService.getRecetteUserById(3, 3).isFavorite(), true);
	}

	// Test 3 : on a une recette favorite et on l'enleve des favorite
	@Test
	public void SwitchToFavorieFalse() {
		ArrayList<RecetteDTO> recettesFavoris = new ArrayList<>();
		recettesFavoris = RecetteService.getFavorieRecettesForUser(3);
		// on va switch les pates napolitaines qui etaient deja dans les favoris
		// (favoris = true)
		RecetteService.switchFavorieStateRecette(RecetteService.getRecetteUserById(3, 2));
		// check si il a �t� enlev� des favoris
		assertEquals(RecetteService.getFavorieRecettesForUser(3).size(), recettesFavoris.size() - 1);
		assertEquals(RecetteService.getRecetteUserById(3, 2).isFavorite(), false);
	}

	// Test fonction : ArrayList<RecetteDTO> getRecettesUserByName(int userId,
	// String name)
	// Test 1 : le nom de la recette n'existe pas :
	@Test
	public void getRecetteUserByNonExistingName() {
		assertEquals(0, RecetteService.getRecettesUserByName(3, "paela").size());
	}

	// Test 2 : il y a des recettes qui contiennent le nom entr� en input
	@Test
	public void getRecetteUserByExistingName() {
		assertEquals(2, RecetteService.getRecettesUserByName(3, "merguez").size());
	}

	// Test fonction : RecetteDTO getRecetteUserById(int userId, int
	// ingredientId)
	// Test 1 : l'utilisateur ne contient pas cette recette (cet id)
	@Test
	public void getRecetteUserByNonExistingId() {
		// recette with id 1 => "chicken a l'americaine" not in user 3
		assertEquals(null, RecetteService.getRecetteUserById(3, 1));
	}

	// Test 2 : l'utilisateur contient cet ingr�dient (cet id)
	@Test
	public void getRecetteUserByExistingId() {
		RecetteDTO recetteInUser = new RecetteDTO(3, 3, "couscous merguez", "couscous merguez.png", false);
		assertEquals(recetteInUser, RecetteService.getRecetteUserById(3, 3));
	}

	// Test fonction : ArrayList<RecetteDTO> getRecetteUserByNameFromFavories(int
	// userId, String name)
	// Test 1 : le nom de la recette n'est pas dans les favoris
	@Test
	public void getRecetteUserByNameNotInFavories() {
		assertEquals(0, RecetteService.getRecetteUserByNameFromFavories(3, "carbonara").size());
	}

	

	// Test 2 : remove une recette existant chez un user
	@Test
	public void removeExistingIngredientForUser(){
		ArrayList<RecetteDTO> recettesTest = new ArrayList<>();
		recettesTest = RecetteService.getAllRecettesForUser(3);
		RecetteDTO recetteToRemove = new RecetteDTO(3, 30, "recette nulle", "recette nulle.png", false);
		RecetteService.removeRecetteFromUser(recetteToRemove);
		// Checker si le size change, et donc la recette en trouv�e a �t� remove !
		assertEquals(recettesTest.size()-1, RecetteService.getAllRecettesForUser(3).size());
	}

}
