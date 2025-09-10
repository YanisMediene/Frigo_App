package com.tse.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.tse.app.DTO.IngredientDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.UserIngredient;
import com.tse.app.service.IngredientService;
import com.tse.app.service.RecetteService;
import com.tse.app.util.ApiKey;
import com.tse.app.util.UserIdConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Ing_RecPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// Side Bar
	private JButton userButton;
	private JButton logOut;
	private JButton ing_recButton;
	private JButton ingButton;
	private JButton recButton;
	private JButton shoppingList;
	private JButton favButton;

	// Ingredients
	private JLabel ingLabel;
	private static JTextField ingredientNameField;
	private JButton searchIngButton;
	private static JPanel searchIngResultsPanel;
	private static JScrollPane scrollIngResultsPanel;
	private JButton filterIngButton;
	private JButton trashIngButton;
    private static JPanel ingredientsPanel;
	private static JScrollPane scrollIngPanel;
	private JButton extendIngButton;
	private static JPanel ingInfoPanel;
	// Recettes
	private JLabel recLabel;
	private JTextField recetteNameField;
	private JButton searchRecButton;
	private JPanel searchRecResultsPanel;
	private JScrollPane scrollRecResultsPanel;
	private JButton trashRecButton;
	private static JPanel recettesPanel;
	private JScrollPane scrollRecPanel;
	private JButton extendRecButton;
	private static JPanel recInfoPanel;

    public Ing_RecPanel() {
    	 
        setLayout(null);
        setName("Ing_RecPanel");

        ImageIcon userIcon = new ImageIcon("./assets/user_icone.png"); 
		Image userImage = userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedUserIcon = new ImageIcon(userImage);
        userButton = new JButton(resizedUserIcon);
        userButton.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
        userButton.setBounds(60, 20, 120, 120);
        userButton.setName("userButton");
        userButton.setBackground(Color.white);
		add(userButton);
		
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
		
		ingLabel = new JLabel("INGREDIENTS");
		ingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		ingLabel.setBounds(220, 20, 280, 50);
		ingLabel.setName("ingLabel");
		add(ingLabel);
		
		ingredientNameField = new JTextField();
		ingredientNameField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		ingredientNameField.setBounds(500, 20, 393, 50);
		ingredientNameField.setColumns(20);
		ingredientNameField.setName("ingredientNameField");
		add(ingredientNameField);
		
		ImageIcon loupeIcon = new ImageIcon("./assets/loupe_icone.png"); 
		Image loupeImage = loupeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedLoupeIcon = new ImageIcon(loupeImage);
		searchIngButton = new JButton(resizedLoupeIcon);
		searchIngButton.setBounds(903, 20, 50, 50);
		searchIngButton.setName("searchIngButton");
		searchIngButton.setBackground(Color.white);
		add(searchIngButton);
		
		searchIngResultsPanel = new JPanel();
	    searchIngResultsPanel.setBounds(240, 90, 773, 280);
        searchIngResultsPanel.setVisible(false);
        searchIngResultsPanel.setName("searchIngResultsPanel");
	    add(searchIngResultsPanel);
	    
	    scrollIngResultsPanel = new JScrollPane(searchIngResultsPanel);
	    scrollIngResultsPanel.setBounds(240, 90, 773, 280); // Définir la position et la taille du JScrollPane
	    scrollIngResultsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	    scrollIngResultsPanel.setVisible(false);
	    scrollIngResultsPanel.setName("scrollIngResultsPanel");
        add(scrollIngResultsPanel);
		
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserSettingsFrame userSettingsFrame = new UserSettingsFrame();
            	UserSettingsFrame.initialiseCheckboxes();
            }
        });
        
	    searchIngButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String ingredientName = ingredientNameField.getText();

	            if (ingredientName.isEmpty()) {
	                JOptionPane.showMessageDialog(Ing_RecPanel.this, "Veuillez entrer un ingrédient.");
	                return;
	            }
	            // Recherche API quand on clique sur le bouton Search
	            searchIngredientsFromAPI(ingredientName);
                // Montrer panneau résultats de recherche
	            searchIngResultsPanel.setVisible(true);
	            scrollIngResultsPanel.setVisible(true);
	            ingredientsPanel.setVisible(false);
	            scrollIngPanel.setVisible(false);
	            // Effacer les champs de saisie après l'authentification
	            ingredientNameField.setText("");
	        }
	    });
		
		ImageIcon trashIcon = new ImageIcon("./assets/trash_icone.png"); 
		Image trashImage = trashIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedTrashIcon = new ImageIcon(trashImage);
		trashIngButton = new JButton(resizedTrashIcon);
		trashIngButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		trashIngButton.setBounds(963, 20, 50, 50);
		trashIngButton.setName("trashIngButton");
		trashIngButton.setBackground(Color.white);
		add(trashIngButton);
		
		trashIngButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Fenêtre de dialogue pour confirmation
		        int confirmation = JOptionPane.showConfirmDialog(Ing_RecPanel.this, "Confirmer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
		        // Vérifier si l'utilisateur a cliqué sur oui
		        if (confirmation == JOptionPane.YES_OPTION) {
		            //Retirer l'ingrédient de la liste et mettre à jour GUI
		            IngredientService.removeAllIngredientsFromUser(UserIdConnection.getUserId());
		            // Vider ingredientsPanel and mettre à jour le GUI
		            ingredientsPanel.removeAll();
		            ingredientsPanel.revalidate();
		            ingredientsPanel.repaint();
		        }
		    }
		});

		ingredientsPanel = new JPanel();
		ingredientsPanel.setBounds(240, 90, 773, 280);
		ingredientsPanel.setName("ingredientsPanel");
        add(ingredientsPanel);
        
        scrollIngPanel = new JScrollPane(ingredientsPanel);
        scrollIngPanel.setBounds(240, 90, 773, 280); // Définir la position et la taille du JScrollPane
        scrollIngPanel.setName("scrollIngPanel");
        add(scrollIngPanel);
        
		ImageIcon extendIcon = new ImageIcon("./assets/extend_icon.png"); 
		Image extendImage = extendIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedExtendIcon = new ImageIcon(extendImage);
        extendIngButton = new JButton(resizedExtendIcon);
        extendIngButton.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
        extendIngButton.setBounds(1023, 90, 60, 280);
        extendIngButton.setName("extendIngButton");
        extendIngButton.setBackground(Color.white);
		add(extendIngButton);
		
        ingInfoPanel = new JPanel();
        ingInfoPanel.setBounds(90, 90, 975, 545);
        ingInfoPanel.setVisible(false);
        ingInfoPanel.setName("ingInfoPanel");
        add(ingInfoPanel);
		
        recLabel = new JLabel("RECIPES       ");
		recLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		recLabel.setBounds(220, 380, 280, 50);
		recLabel.setName("recLabel");
		add(recLabel);
		
		recetteNameField = new JTextField();
		recetteNameField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		recetteNameField.setBounds(500, 380, 393, 50);
		recetteNameField.setColumns(20);
		recetteNameField.setName("recetteNameField");
		add(recetteNameField);
		
		searchRecButton = new JButton(resizedLoupeIcon);
		searchRecButton.setBounds(903, 380, 50, 50);
		searchRecButton.setName("searchRecButton");
		add(searchRecButton);
		
        searchRecResultsPanel = new JPanel();
        searchRecResultsPanel.setBounds(240, 440, 773, 280);
        searchRecResultsPanel.setVisible(false);  
        searchRecResultsPanel.setName("searchRecResultsPanel");
        add(searchRecResultsPanel);
        
	    scrollRecResultsPanel = new JScrollPane(searchRecResultsPanel);
	    scrollRecResultsPanel.setBounds(240, 440, 773, 280); // Définir la position et la taille du JScrollPane
	    scrollRecResultsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	    scrollRecResultsPanel.setVisible(false);
	    scrollRecResultsPanel.setName("scrollRecResultsPanel");
        add(scrollRecResultsPanel);
        
		
        searchRecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recetteName = recetteNameField.getText();
                // Requête API quand bouton "Search" appuyé
                searchRecettesFromAPI(recetteName);
                // Show the search results panel
                searchRecResultsPanel.setVisible(true);
	            scrollRecResultsPanel.setVisible(true);
	            recettesPanel.setVisible(false);
	            scrollRecPanel.setVisible(false); 
                // Effacer les champs de saisie après l'authentification
                recetteNameField.setText("");
            }
        });
		
		trashRecButton = new JButton(resizedTrashIcon);
		trashRecButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		trashRecButton.setBounds(963, 380, 50, 50);
		trashRecButton.setName("trashRecButton");
		trashRecButton.setBackground(Color.white);
		add(trashRecButton);
		trashRecButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Afficher dualogue de confirmation
		        int confirmation = JOptionPane.showConfirmDialog(Ing_RecPanel.this, "Confirmer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
		        // Check if the user clicked 'Yes' (option 0)
		        if (confirmation == JOptionPane.YES_OPTION) {
		            // Retirer ingrédient de la liste et mettre à jour GUI
		            RecetteService.removeAllRecettesFromUser(UserIdConnection.getUserId());

		            // Vider ingredientsPanel et mettre à jour GUI
		            recettesPanel.removeAll();
		            recettesPanel.revalidate();
		            recettesPanel.repaint();
		        }
		    }
		});
		
		recettesPanel = new JPanel();
		recettesPanel.setBounds(240, 440, 773, 280);
		recettesPanel.setName("recettesPanel");
        add(recettesPanel);
        
        scrollRecPanel = new JScrollPane(recettesPanel);
        scrollRecPanel.setBounds(240, 440, 773, 280); 
        scrollRecPanel.setName("scrollRecPanel");
        scrollRecPanel.setName("scrollRecPanel");
        add(scrollRecPanel);        
		
		extendRecButton = new JButton(resizedExtendIcon);
		extendRecButton.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		extendRecButton.setBounds(1023, 440, 60, 280);
		extendRecButton.setName("extendRecButton");
		extendRecButton.setBackground(Color.white);
		add(extendRecButton);
		
        recInfoPanel = new JPanel();
        recInfoPanel.setBounds(90, 90, 975, 545);
        recInfoPanel.setVisible(false);
        recInfoPanel.setName("recInfoPanel");
        add(recInfoPanel);		
    }
    
 // Méthode pour afficher la liste des ingrédients dans le panneau
    public static void showIngredientList() {
    	 //Afficher chaque ingrédient dans le frigo
    	ingredientsPanel.removeAll();
	    ArrayList<IngredientDTO> ingredientList = IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
	    for (IngredientDTO ingredient : ingredientList) {
	    	IngCard ingCardPanel = new IngCard(ingredient,ingredientsPanel,ingInfoPanel);
	    	ingredientsPanel.add(ingCardPanel.getIngCardPanel());
	    }
       	ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
    }

 // Méthode pour gérer la réponse de l'API d'ingrédients
	private void handleIngAPIResponse(String jsonResponse) {
	    try {
	        Gson gson = new Gson();
	        JsonObject responseJson = gson.fromJson(jsonResponse, JsonObject.class);
	        JsonArray resultsArray = responseJson.getAsJsonArray("results");
	        searchIngResultsPanel.removeAll();
	        for (int i = 0; i < resultsArray.size(); i++) {
	        	JsonObject ingredientObject = resultsArray.get(i).getAsJsonObject();
	            int ingredientId = ingredientObject.get("id").getAsInt();
	            String ingredientName = ingredientObject.get("name").getAsString();
	            String ingredientImage = ingredientObject.get("image").getAsString();
	            Ingredient ing = new Ingredient(ingredientId,ingredientName,ingredientImage);
	            //Date de péremption dans un mois par défaut
	            Date currentDate = new Date();
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(currentDate);
	            calendar.add(Calendar.MONTH, 1);
	            Date nextMonthDate = calendar.getTime();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	            String dateString = sdf.format(nextMonthDate);
	            Date date=sdf.parse(dateString);
	            UserIngredient user_ing = new UserIngredient(UserIdConnection.getUserId(),ingredientId,0L,0,date);
	            IngredientDTO ing_dto = new IngredientDTO(UserIdConnection.getUserId(),ingredientId,ingredientName,ingredientImage,1.0,1,date);
	            String url = "https://spoonacular.com/cdn/ingredients_250x250/"+ing_dto.getImage();
	    		JLayeredPane searchCard = new JLayeredPane();
	    		searchCard.setBounds(0, 0, 250, 250);
	    		searchCard.setPreferredSize(new Dimension(250, 250)); // Ajuster taille
	    		JLabel ingNameLabel = new JLabel(ingredientName);
	            ingNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	            ingNameLabel.setBounds(50, 220, 150, 30);
	            searchCard.add(ingNameLabel, JLayeredPane.PALETTE_LAYER);
	            try {
	            	BufferedImage img = ImageIO.read(new URL(url));
	            	Image scaledImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	            	ImageIcon scaledIcon = new ImageIcon(scaledImage);
	            	JButton btnIngredient = new JButton(scaledIcon);
	            	btnIngredient.setBounds(0, 0, 250, 250);
	            	btnIngredient.setBackground(Color.white);
	            	btnIngredient.addActionListener(new ActionListener() {
	            		@Override
	            		public void actionPerformed(ActionEvent e) {	
	            			IngredientService.addIngredientToUser(ing_dto);
	            			showIngredientList();
	            			searchIngResultsPanel.setVisible(false);
	            			scrollIngResultsPanel.setVisible(false);
	            			ingredientsPanel.setVisible(true);
	            			scrollIngPanel.setVisible(true);
	            			ingredientNameField.setText("");
	            		}
	            	});
	            	searchCard.add(btnIngredient);
	            	
	            } catch (MalformedURLException e) {
	            	e.printStackTrace();
	            } catch (IOException e) {
	            	e.printStackTrace();
	            }
	            searchIngResultsPanel.add(searchCard);
	            searchIngResultsPanel.revalidate();
		        searchIngResultsPanel.repaint();
	        }
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
		}
	
	 // Méthode pour effectuer une recherche d'ingrédients via l'API
	private void searchIngredientsFromAPI(String query) {
	    try {
	        String apiUrl = "https://api.spoonacular.com/food/ingredients/search?query=" + query + "&apiKey=" + ApiKey.getApiKey();
	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        int responseCode = connection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }

	            in.close();

	            
	            handleIngAPIResponse(response.toString());

	            
	            searchIngResultsPanel.setVisible(true);
	        } else {
	            System.out.println("Error: " + responseCode);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// Méthode pour afficher la liste des recettes dans le panneau
	public static void showRecetteList() {
   	 //Afficher chaque recette dans le frigo
		recettesPanel.removeAll();
	    ArrayList<RecetteDTO> recetteList = RecetteService.getAllRecettesForUser(UserIdConnection.getUserId());
	    for (RecetteDTO recette : recetteList) {
	    	RecCard recCardPanel = new RecCard(recette,recettesPanel,recInfoPanel);
	    	recettesPanel.add(recCardPanel.getRecetteCardPanel());  
	    }
	    recettesPanel.revalidate();
      	recettesPanel.repaint();
   }
	

	// Méthode pour gérer la réponse de l'API de recettes
    private void handleRecAPIResponse(String jsonResponse) {
        try {
        	Gson gson = new Gson();
        	JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");
            searchRecResultsPanel.removeAll();
            int numberOfRecipes = jsonObject.get("totalResults").getAsInt();
            if(numberOfRecipes==0) {
            	JOptionPane.showMessageDialog(this,"No recipes found !");
            }
            else {
            	for (int i = 0; i < resultsArray.size(); i++) {
                    JsonObject recetteObject = resultsArray.get(i).getAsJsonObject();
                    int recetteId = recetteObject.get("id").getAsInt();
                    String recetteTitle = recetteObject.get("title").getAsString();
                    String recetteImage = recetteObject.get("image").getAsString();
                    RecetteDTO rec_dto = new RecetteDTO(UserIdConnection.getUserId(), recetteId , recetteTitle, recetteImage, false);
                    String url = rec_dto.getImage();
            		try {
            			JLayeredPane searchCard = new JLayeredPane();
        	    		searchCard.setBounds(0, 0, 250, 250);
        	    		searchCard.setPreferredSize(new Dimension(250, 250)); 
        	    		JLabel recNameLabel = new JLabel(recetteTitle);
        	    		recNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        	            recNameLabel.setBounds(50, 220, 150, 30);
        	            searchCard.add(recNameLabel, JLayeredPane.PALETTE_LAYER);
        	            BufferedImage img = ImageIO.read(new URL(url));
    	            	Image scaledImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    	            	ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	            	JButton btnRecette = new JButton(scaledIcon);
    	            	btnRecette.setBounds(0, 0, 250, 250);
    	            	btnRecette.setBackground(Color.white);
                        btnRecette.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                RecetteService.addRecetteToUser(rec_dto);
                                showRecetteList();
                                // Cacher panneau résultats de recherche après sélection
                                searchRecResultsPanel.setVisible(false);
                                scrollRecResultsPanel.setVisible(false);
                                recettesPanel.setVisible(true);
                                scrollRecPanel.setVisible(true);
                                recetteNameField.setText("");
                            }
                            
                        });
                        searchCard.add(btnRecette);
            	        searchRecResultsPanel.add(searchCard);
            		} catch (MalformedURLException e) {
            			e.printStackTrace();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
                    
                }
            }
            searchRecResultsPanel.revalidate();
            searchRecResultsPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour effectuer une recherche de recettes via l'API
    private void searchRecettesFromAPI(String query) {
        try {     
            StringBuilder ingredientsList = new StringBuilder();
            // Récupérer ingrédients de la liste de l'utilisateur
            ArrayList<IngredientDTO> ingredientList = IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
            for (IngredientDTO ingredient : ingredientList) {
                ingredientsList.append(ingredient.getName()).append(",");
            }
            if (ingredientsList.length() > 0) {
                ingredientsList.deleteCharAt(ingredientsList.length() - 1);
            }
            String intolerances=UserSettingsFrame.getIntolerancesForRecipes();
            String apiUrl ="";
            if("".equals(query)) {
            	apiUrl = "https://api.spoonacular.com/recipes/complexSearch?apiKey=" + ApiKey.getApiKey() + "&query="+query+"&includeIngredients=" + ingredientsList.toString()+"&intolerances="+intolerances+"&sort=max-used-ingredients";
            }
            else {
                apiUrl = "https://api.spoonacular.com/recipes/complexSearch?apiKey=" + ApiKey.getApiKey() + "&query="+query+"&includeIngredients=" + ingredientsList.toString()+"&intolerances="+intolerances;
            }   
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                handleRecAPIResponse(response.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
	
	public JButton getFilterIngButton() {
		return filterIngButton;
	}
	
	public JButton getTrashIngButton() {
		return filterIngButton;
	}
	
	public JButton getFilterRecButton() {
		return trashRecButton;
	}
	
	public JButton getTrashRecButton() {
		return trashRecButton;
	}
	
	public static JPanel getIngredientsPanel() {
		return ingredientsPanel;
	}
	
	public static JPanel getRecettesPanel() {
		return recettesPanel;
	}

	public JButton getExtendIngButton() {
		return extendIngButton;
	}
	
	public JButton getExtendRecButton() {
		return extendRecButton;
	}
	
	public static JPanel getIngInfoPanel() {
		return ingInfoPanel;
	}
	public static JPanel getRecInfoPanel() {
		return recInfoPanel;
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
	public static JPanel getSearchIngResultsPanel() {
		return searchIngResultsPanel;
	}
	public static JScrollPane getScrollIngResultsPanel() {
		return scrollIngResultsPanel;
	}
	public static JScrollPane getScrollIngPanel() {
		return scrollIngPanel;
	}
	public static JTextField getIngredientNameField() {
		return ingredientNameField;
	}
}
