package com.tse.app.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tse.app.DTO.IngredientDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.User;
import com.tse.app.util.UserIdConnection;


public class IngredientsTest {
	
	@Before
    public void init() throws ParseException {
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		LoginService.registerUser(new User(1,"adnane","1234"));
		LoginService.registerUser(new User(2,"toto","1234"));
		IngredientService.addIngredient(new Ingredient(1, "chicken", "chicken.png"));
		IngredientService.addIngredient(new Ingredient(2, "egg", "egg.png"));
		IngredientService.addIngredient(new Ingredient(3, "milk", "milk.png"));
		IngredientService.addIngredient(new Ingredient(6, "chicken paprika", "chicken paprika.png"));
		IngredientService.addIngredientToUser(new IngredientDTO(1, 1, "chicken", "chicken.png", 3.0, 1, utilDate));
		IngredientService.addIngredientToUser(new IngredientDTO(2, 1, "chicken", "chicken.png", 3.0, 1, utilDate));
		IngredientService.addIngredientToUser(new IngredientDTO(2, 6, "chicken paprika", "chicken paprika.png", 3.0, 1, utilDate));
    }

	
	@After 
	public void close() {
		IngredientService.removeAllIngredientsFromUser(1);
		IngredientService.removeAllIngredientsFromUser(2);
		IngredientService.removeAllIngredientsFromUser(3);
		IngredientService.removeAllIngredients();
	}
	
	
	//Test fonction : Ingredient getIngredient(int ingredientId)
	//Test 1 : Ingredient existant dans la base
	@Test
	public void GetExistingIngredient() {
		Ingredient existIngred = new Ingredient(1, "chicken", "chicken.png");
		assertEquals(existIngred,IngredientService.getIngredient(1));
	}
	
	
	//Test 2 : Ingredient non existant dans la base
	@Test
	public void GetNonExistingIngredient() {
		Ingredient nonExistIngred = new Ingredient(4, "test", "test.png");
		assertEquals(null,IngredientService.getIngredient(4));
	}
	

	
	//Test fonction : void addIngredient(Ingredient ingredient)
	//Test 1 : additionner un ingr�dient non existant
	@Test
	public void addNewIngredient() {
		ArrayList<Ingredient> ingredientsList = new ArrayList<>();
		ingredientsList = IngredientService.getAllIngredients();
		Ingredient ingredToAdd = new Ingredient(5, "toto", "toto.png");
		IngredientService.addIngredient(ingredToAdd);
		assertEquals(ingredToAdd,IngredientService.getIngredient(5));
		assertEquals(ingredientsList.size()+1,IngredientService.getAllIngredients().size());
		
	}
	
	//Test 2 : additionner un ingr�dient d�j� dans la base
	@Test
	public void addExistingIngredient() {
		ArrayList<Ingredient> ingredientsList = new ArrayList<>();
		ingredientsList = IngredientService.getAllIngredients();
		Ingredient ingredAlreadyAdded = new Ingredient(2, "egg", "egg.png");
		IngredientService.addIngredient(ingredAlreadyAdded);
		assertEquals(ingredAlreadyAdded,IngredientService.getIngredient(2));
		assertEquals(ingredientsList.size(),IngredientService.getAllIngredients().size());	
	}
	
	
	//Test fonction : ArrayList<IngredientDTO> getAllIngredientsForUser(int userId)
	//Test : r�cup�ration de tous les ingr�dients pour un utilisateur
	@Test
	public void getIngredientsUserCheck() {
		ArrayList<IngredientDTO> ingredientsTest = new ArrayList<>();
		ingredientsTest = IngredientService.getAllIngredientsForUser(1);
		assertEquals(ingredientsTest.size(),1);
	}
	
	
	
	//Test fonction : addIngredientToUser(IngredientDTO ingredientDTO)
	//Test 1 : ajouter � l'utilisateur un ingredient qu'il a d�j�
	@Test
	public void addExistingIngredientToUser() throws ParseException {
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		ArrayList<IngredientDTO> ingredientsList = new ArrayList<>();
		ingredientsList = IngredientService.getAllIngredientsForUser(2);
		IngredientDTO ingredAlreadyAddedToUser = new IngredientDTO(2, 1, "chicken", "chicken.png", 3.0, 1, utilDate);
		IngredientService.addIngredientToUser(ingredAlreadyAddedToUser);
		assertEquals(ingredAlreadyAddedToUser,IngredientService.getIngredientUserById(2, 1));
		assertEquals(ingredientsList.size(),IngredientService.getAllIngredientsForUser(2).size());	
	}
	
	

	//Test 2 : ajouter � l'utilisateur un nouvel ingr�dient
	@Test
	public void addNewIngredientToUser() throws ParseException {
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-09");
		ArrayList<IngredientDTO> ingredientsList = new ArrayList<>();
		ingredientsList = IngredientService.getAllIngredientsForUser(2);
		IngredientDTO ingredNewToUser = new IngredientDTO(2, 3, "milk", "milk.png", 3.0, 2, utilDate);
		IngredientService.addIngredientToUser(ingredNewToUser);
		assertEquals(ingredNewToUser,IngredientService.getIngredientUserById(2, 3));
		assertEquals(ingredientsList.size()+1,IngredientService.getAllIngredientsForUser(2).size());	
	}
	
	
	
	//Test fonction : ArrayList<IngredientDTO> getIngredientsUserByName(int userId, String name)
	//Test 1 : le nom de l'ingredient n'existe pas :
	@Test 
	public void getIngredientUserByNonExistingName(){
		assertEquals(0,IngredientService.getIngredientsUserByName(2, "egg").size());
	}
	
	
	//Test 2 : il y a des ingredients qui contiennent le nom de l'ingredient en question :
		@Test 
		public void getIngredientUserByExistingName(){
			assertEquals(2,IngredientService.getIngredientsUserByName(2, "chicken").size());
		}
	
	//Test fonction : IngredientDTO getIngredientUserById(int userId, int ingredientId)
	//Test 1 : l'utilisateur ne contient pas cet ingr�dient (cet id)
	@Test
	public void getIngredientUserByNonExistingId() throws ParseException {
		//ingredient with id 2 => "egg" not in user 2
		assertEquals(null,IngredientService.getIngredientUserById(2, 2));
	}
	
	//Test 2 : l'utilisateur contient cet ingr�dient (cet id)
	@Test
	public void getIngredientUserByExistingId() throws ParseException{
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		IngredientDTO ingredInUser = new IngredientDTO(2, 1, "chicken", "chicken.png", 3.0, 1, utilDate);
		assertEquals(ingredInUser,IngredientService.getIngredientUserById(2, 1));
	}
	
	
	//Test fonction : void updateIngredientInUser(IngredientDTO ingredientUpdated)
	//Test 1 : l'utilisateur update un ingredient qui n'existe pas 
	@Test
	public void updateNonExistingIngredientForUser() throws ParseException{
		Date utilDateModified = new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-02");
		Double amountModified = 10.0;
		IngredientDTO ingredNonExisting = new IngredientDTO(2, 10, "carbonara", "carbonara.png", amountModified, 1, utilDateModified);
		//Checker si l'ingredient n'existe toujours pas 
		assertEquals(null,IngredientService.getIngredientUserById(2, 10));
	}
	
	
	
	//Test 2 : l'utilisateur update un ingredient existant
	@Test
	public void updateExistingIngredientForUser() throws ParseException{
		//Chicken with modified informations here
		Date utilDateModified = new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-02");
		Double amountModified = 10.0;
		IngredientDTO ingredUpdated = new IngredientDTO(2, 1, "chicken", "chicken.png", amountModified, 1, utilDateModified);
		IngredientService.updateIngredientInUser(ingredUpdated,2);
		assertEquals(ingredUpdated,IngredientService.getIngredientUserById(2, 1));
	}
	
	
	//Test fonction : void removeIngredientFromUser(IngredientDTO ingredient)
	//Test 1 : remove un ingredient qui n'existe pas chez un user
	@Test
	public void removeNonExistingIngredientForUser() throws ParseException{
		ArrayList<IngredientDTO> ingredientsTest = new ArrayList<>();
		ingredientsTest = IngredientService.getAllIngredientsForUser(2);
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-02");
		IngredientDTO ingredNonExisting = new IngredientDTO(2, 10, "carbonara", "carbonara.png", 5.0, 1, utilDate);
		IngredientService.removeIngredientFromUser(ingredNonExisting);
		//Checker si le size ne change pas, et donc rien n'a �t� remove !
		assertEquals(ingredientsTest.size(),IngredientService.getAllIngredientsForUser(2).size());
	}
	
	
	
	//Test 2 : remove un ingredient existant chez un user
	@Test
	public void removeExistingIngredientForUser() throws ParseException{
		ArrayList<IngredientDTO> ingredientsTest = new ArrayList<>();
		ingredientsTest = IngredientService.getAllIngredientsForUser(2);
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		IngredientDTO ingredToRemoveExisting = new IngredientDTO(2, 6, "chicken paprika", "chicken paprika.png", 3.0, 1, utilDate);
		IngredientService.removeIngredientFromUser(ingredToRemoveExisting);
		//Checker si le size ne change pas, et donc rien n'a �t� remove !
		assertEquals(ingredientsTest.size()-1,IngredientService.getAllIngredientsForUser(2).size());
	}

}
