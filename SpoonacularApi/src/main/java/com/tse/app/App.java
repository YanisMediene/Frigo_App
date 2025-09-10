package com.tse.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tse.app.util.ApiKey;
import com.tse.app.view.CreateUserAccount;
import com.tse.app.view.FavoritePanel;
import com.tse.app.view.IngPanel;
import com.tse.app.view.Ing_RecPanel;
import com.tse.app.view.RecPanel;
import com.tse.app.view.ShoppingListPanel;
import com.tse.app.view.UserLogin;

public class App{
	// Déclaration des panneaux en tant que variables statiques
	 private static UserLogin  loginPanel;
	 private static CreateUserAccount createAccountPanel;
	 private static Ing_RecPanel ing_recPanel;
	 private static IngPanel ingPanel;
	 private static RecPanel recPanel;
	 private static ShoppingListPanel listPanel;
	 private static FavoritePanel favPanel;

	public static void main(String[] args) {
		// Initialisation de la clé API
		ApiKey apikey = new ApiKey();
		 // Utilisation de SwingUtilities.invokeLater pour garantir une exécution sûre des opérations GUI
		SwingUtilities.invokeLater(() -> {
			// Création d'une nouvelle JFrame
			JFrame frame = new JFrame("Frigo App");
            frame.setResizable(false);
            // Création d'un conteneur de cartes avec un CardLayout
            JPanel cardContainer = new JPanel(new CardLayout());
            
            // Ajout des panneaux au conteneur de cartes avec des noms associés
            loginPanel = new UserLogin((CardLayout) cardContainer.getLayout(), cardContainer);
            createAccountPanel = new CreateUserAccount((CardLayout) cardContainer.getLayout(), cardContainer);
            favPanel = new FavoritePanel();
            ing_recPanel = new Ing_RecPanel();
            ingPanel = new IngPanel();
            recPanel = new RecPanel();
            listPanel= new ShoppingListPanel();
            
            // Ajout des panneaux au conteneur de cartes avec des noms associés
            
            cardContainer.add(loginPanel, "Login");
            cardContainer.add(createAccountPanel, "CreateAccount");  
            cardContainer.add(ing_recPanel, "Ing_Rec");
            cardContainer.add(ingPanel, "Ing");
            cardContainer.add(recPanel, "Rec");
            cardContainer.add(listPanel, "List");
            cardContainer.add(favPanel, "Fav");
            
            // Obtention du CardLayout à partir du conteneur de cartes
            CardLayout cardLayout = (CardLayout) cardContainer.getLayout();
            
            // Définition du CardLayout comme gestionnaire de mise en page du conteneur principal
            frame.setLayout(new BorderLayout());
            frame.add(cardContainer, BorderLayout.CENTER);
                        
            // Configuration des gestionnaires d'événements pour les boutons de chaque panneau
            
            // Connexion et Inscription
            loginPanel.getCreateAccButton().addActionListener(e -> cardLayout.show(cardContainer, "CreateAccount"));
            createAccountPanel.getBackButton().addActionListener(e -> cardLayout.show(cardContainer, "Login"));
            
            // Ingredients/Recettes
            ing_recPanel.getlogOutButton().addActionListener(e -> {
            	cardLayout.show(cardContainer, "Login");
            	ShoppingListPanel.resetShoppingList();
            });
            ing_recPanel.getIngRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing_Rec");
                Ing_RecPanel.showIngredientList();
                Ing_RecPanel.showRecetteList();
            });
            ing_recPanel.getIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            ing_recPanel.getExtendIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            ing_recPanel.getRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            ing_recPanel.getExtendRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            ing_recPanel.getShoppingListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "List");
                ShoppingListPanel.showRecetteList();
            });
            ing_recPanel.getFavListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Fav");
                FavoritePanel.showFavList();
            });
            
            //Ingredient
            ingPanel.getlogOutButton().addActionListener(e -> {
            	cardLayout.show(cardContainer, "Login");
            	ShoppingListPanel.resetShoppingList();
            });
            ingPanel.getIngRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing_Rec");
                Ing_RecPanel.showIngredientList();
                Ing_RecPanel.showRecetteList();
            });
            ingPanel.getIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            ingPanel.getRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            ingPanel.getShoppingListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "List");
                ShoppingListPanel.showRecetteList();
            });
            ingPanel.getFavListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Fav");
                FavoritePanel.showFavList();
            });
            
            //Recettes
            recPanel.getlogOutButton().addActionListener(e -> {
            	cardLayout.show(cardContainer, "Login");
            	ShoppingListPanel.resetShoppingList();
            });
            recPanel.getIngRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing_Rec");
                Ing_RecPanel.showIngredientList();
                Ing_RecPanel.showRecetteList();
            });
            recPanel.getIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            recPanel.getRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            recPanel.getShoppingListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "List");
                ShoppingListPanel.showRecetteList();
            });
            recPanel.getFavListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Fav");
                FavoritePanel.showFavList();
            });
            
            //Shopping List
            listPanel.getlogOutButton().addActionListener(e -> {
            	cardLayout.show(cardContainer, "Login");
            	ShoppingListPanel.resetShoppingList();
            });
            listPanel.getIngRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing_Rec");
                Ing_RecPanel.showIngredientList();
                Ing_RecPanel.showRecetteList();
            });
            listPanel.getIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            listPanel.getRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            listPanel.getShoppingListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "List");
                ShoppingListPanel.showRecetteList();
            });
            listPanel.getFavListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Fav");
                FavoritePanel.showFavList();

            });
            
            //Favorites
            favPanel.getlogOutButton().addActionListener(e -> {
            	cardLayout.show(cardContainer, "Login");
            	ShoppingListPanel.resetShoppingList();
            });
            favPanel.getIngRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing_Rec");
                Ing_RecPanel.showIngredientList();
                Ing_RecPanel.showRecetteList();
            });
            favPanel.getIngButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Ing");
                IngPanel.showIngredientList();
            });
            favPanel.getRecButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Rec");
                RecPanel.showRecetteList();
            });
            favPanel.getShoppingListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "List");
                ShoppingListPanel.showRecetteList();
            });
            favPanel.getFavListButton().addActionListener(e -> {
                cardLayout.show(cardContainer, "Fav");
                FavoritePanel.showFavList();
            });
            
            // Définition de la taille de la fenêtre
            frame.setSize(1160, 800); 

            // Définition de l'opération par défaut lorsque la fenêtre est fermée
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Centrage de la fenêtre sur l'écran
            frame.setLocationRelativeTo(null);
            // Rendre la fenêtre visible
            frame.setVisible(true);
            
            // Réarrangement des panneaux d'information pour garantir l'ordre d'affichage
            frame.setComponentZOrder(Ing_RecPanel.getIngInfoPanel(), 0);
            frame.setComponentZOrder(Ing_RecPanel.getRecInfoPanel(), 0);
            frame.setComponentZOrder(IngPanel.getIngInfoPanel(), 0);
            frame.setComponentZOrder(RecPanel.getRecInfoPanel(), 0);
        });
	}	
}
