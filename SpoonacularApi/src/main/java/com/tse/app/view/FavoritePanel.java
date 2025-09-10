package com.tse.app.view;
import com.tse.app.DTO.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.tse.app.service.IngredientService;
import com.tse.app.service.RecetteService;
import com.tse.app.util.UserIdConnection;

public class FavoritePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// Side Bar
	private JButton userButton;
	private JButton ing_recButton;
	private JButton ingButton;
	private JButton logOut;
	private JButton shoppingList;
	private JButton recButton;
	private JButton favButton;
	// Ingredients
	private JLabel favLabel;
    private static JPanel favPanel;
	private JScrollPane scrollFavPanel;
    private static JPanel recInfoPanel;

    public FavoritePanel() {
    	// Définition de la disposition de la fenêtre
    	setLayout(null);
    	
    	 // Création d'un bouton avec une icône utilisateur
    	ImageIcon userIcon = new ImageIcon("./assets/user_icone.png"); // chemin de l'icône
		Image userImage = userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedUserIcon = new ImageIcon(userImage);
        userButton = new JButton(resizedUserIcon);
        userButton.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
        userButton.setBounds(60, 20, 120, 120);
        userButton.setName("userButton");
        userButton.setBackground(Color.white);
		add(userButton);
		
		// Ajout des boutons pour la navigation dans la barre latérale
		
		ing_recButton = new JButton("Home");
		ing_recButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		ing_recButton.setBounds(60, 180, 120, 30);
		ing_recButton.setName("ing_recButton");
		ing_recButton.setBackground(Color.white);
		add(ing_recButton);
	
		ingButton = new JButton("Ingredients");
		ingButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		ingButton.setBounds(60, 230, 120, 30);
		ingButton.setName("ingButton");
		ingButton.setBackground(Color.white);
		add(ingButton);
		
		recButton = new JButton("Recipes");
		recButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		recButton.setBounds(60, 280, 120, 30);
		recButton.setName("recButton");
		recButton.setBackground(Color.white);
		add(recButton);
		
		logOut = new JButton("Log Out");
	    logOut.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
	    logOut.setBounds(60, 430, 120, 30);
	    logOut.setBackground(Color.white);
		add(logOut);
		
		shoppingList = new JButton("Shopping List");
		shoppingList.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		shoppingList.setBounds(60, 330, 120, 30);
		shoppingList.setBackground(Color.white);
		add(shoppingList);
		
		favButton = new JButton("Favorites");
		favButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		favButton.setBounds(60, 380, 120, 30);
		favButton.setBackground(Color.white);
		add(favButton);
		
		favLabel = new JLabel("FAVORITES");
		favLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		favLabel.setBounds(220, 20, 280, 50);
		favLabel.setName("favLabel");
		add(favLabel);
		
		// Action à effectuer lorsqu'on clique sur le bouton utilisateur
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserSettingsFrame userSettingsFrame = new UserSettingsFrame();
            	UserSettingsFrame.initialiseCheckboxes();
            }
        });

        // Configurations des panneaux
        
        favPanel = new JPanel();
        favPanel.setLayout(new GridLayout(0, 3)); // Using a single column for vertical arrangement
        favPanel.setName("favPanel");
        
        scrollFavPanel = new JScrollPane(favPanel);
        scrollFavPanel.setBounds(240, 90, 870, 630); // Définissez la position et la taille du JScrollPane
        scrollFavPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollFavPanel);
        
        recInfoPanel = new JPanel();
        recInfoPanel.setBounds(90, 90, 975, 545);
        recInfoPanel.setVisible(false);
        recInfoPanel.setName("recInfoPanel");
        add(recInfoPanel);

    }
    
    // Méthode pour afficher la liste des recettes favorites
    
    public static void showFavList() {
      	 //Afficher chaque recette dans le frigo
      	favPanel.removeAll();
   	    ArrayList<RecetteDTO> recetteList = RecetteService.getFavorieRecettesForUser(UserIdConnection.getUserId());
   	    for (RecetteDTO recette : recetteList) {
   	    	RecCardFav recCardPanel = new RecCardFav(recette,favPanel,recInfoPanel);
   	    	favPanel.add(recCardPanel);
   	    }
   	    favPanel.revalidate();
   	    favPanel.repaint();
      }
    
    // Getters et setters
    
    public JButton getUserButton() {
        return userButton;
    }

	public JButton getIngRecButton() {
		return ing_recButton;
	}

	public JButton getIngButton() {
		return ingButton;
	}

	public JButton getRecButton() {
		return recButton;
	}
	public JButton getlogOutButton() {
		return logOut;
	}
	public JButton getShoppingListButton() {
		return shoppingList;
	}
	public JButton getFavListButton() {
		return favButton;
	}
	public static JPanel getFavPanel() {
		return favPanel;
	}
}
