package com.tse.app.model;

public class User {
	
	// Identifiant de l'utilisateur
	private int userId;
	
	// Nom d'utilisateur
	private String username;
	
	// Mot de passe de l'utilisateur
	private String password;
	
	// Intolérances alimentaires de l'utilisateur
	private String intolerances;
	
	// Constructeur par défaut
	public User() {
	}
	
	// Constructeur avec les paramètres obligatoires (username, password)
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	// Constructeur avec les paramètres obligatoires (userId, username, password)
	public User(int userId,String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.userId=userId;
	}
	
	// Constructeur avec les paramètres obligatoires (userId, username, password, intolerances)
	public User(int userId,String username, String password, String intolerances) {
		super();
		this.username = username;
		this.password = password;
		this.userId=userId;
		this.intolerances=intolerances;
	}
	
	// Getters et setters
	
	public String getUsername() {
		return username;
	}
	public String getIntolerances() {
		return intolerances;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
