package com.tse.app.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tse.app.DTO.IngredientDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.UserIngredient;
import com.tse.app.service.IngredientService;
import com.tse.app.service.RecetteService;
import com.tse.app.util.ApiKey;
import com.tse.app.util.UserIdConnection;

public class RecPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// Barre latérale
	private JButton userButton;
	private JButton logOut;
	private JButton ing_recButton;
	private JButton ingButton;
	private JButton recButton;
	private JButton shoppingList;
	private JButton favButton;
	// Recettes
	private JLabel recLabel;
	private JTextField recetteNameField;
	private JButton searchRecButton;
	private JButton trashRecButton;
	private JPanel searchRecResultsPanel;
	private JScrollPane scrollRecResultsPanel;
	private JButton filterRecButton;
    private static JPanel recettesPanel;
	private JScrollPane scrollRecPanel;
    private static JPanel recInfoPanel;
    
    public RecPanel() {
    	setLayout(null);
        setName("RecPanel");

        ImageIcon userIcon = new ImageIcon("./assets/user_icone.png"); // Remplacez par le chemin réel de votre icône
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
		
		logOut = new JButton("Log Out");
	    logOut.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
	    logOut.setBounds(60, 430, 120, 30);
	    logOut.setBackground(Color.white);
		add(logOut);
		
		recLabel = new JLabel("RECIPES");
		recLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		recLabel.setBounds(220, 20, 280, 50);
		recLabel.setName("recLabel");
		add(recLabel);
		
		recetteNameField = new JTextField();
		recetteNameField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		recetteNameField.setBounds(500, 20, 393, 50);
		recetteNameField.setColumns(20);
		recetteNameField.setName("recetteNameField");
		add(recetteNameField);
		
		ImageIcon loupeIcon = new ImageIcon("./assets/loupe_icone.png"); // Remplacez par le chemin réel de votre icône
		Image loupeImage = loupeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedLoupeIcon = new ImageIcon(loupeImage);
		searchRecButton = new JButton(resizedLoupeIcon);
		searchRecButton.setBounds(903, 20, 50, 50);
		searchRecButton.setName("searchRecButton");
		searchRecButton.setBackground(Color.white);
		add(searchRecButton);
		
		searchRecResultsPanel = new JPanel();
		searchRecResultsPanel.setVisible(false);  // Set the initial visibility to false
		searchRecResultsPanel.setLayout(new GridLayout(0, 3)); // Using a single column for vertical arrangement
		searchRecResultsPanel.setName("searchRecResultsPanel");
	    
		scrollRecResultsPanel = new JScrollPane(searchRecResultsPanel);
		scrollRecResultsPanel.setBounds(240, 90, 810, 640); // Définissez la position et la taille du JScrollPane
		scrollRecResultsPanel.setVisible(false);
		scrollRecResultsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollRecResultsPanel.setName("scrollRecResultsPanel");
        add(scrollRecResultsPanel);
		
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserSettingsFrame userSettingsFrame = new UserSettingsFrame();
            	UserSettingsFrame.initialiseCheckboxes();
            }
        });
        
        searchRecButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String ingredientName = recetteNameField.getText();
	            // Effectuer la requête API lorsque le bouton "Rechercher" est cliqué
	            searchRecettesFromAPI(ingredientName);
                // Afficher le panneau des résultats de recherche
	            searchRecResultsPanel.setVisible(true);
	            scrollRecResultsPanel.setVisible(true);
	            recettesPanel.setVisible(false);
	            scrollRecPanel.setVisible(false);
	            // Effacer les champs de saisie après l'authentification
	            recetteNameField.setText("");
	        }
	    });
        
        recettesPanel = new JPanel();
        recettesPanel.setLayout(new GridLayout(0, 3)); // Using a single column for vertical arrangement
        recettesPanel.setName("recettesPanel");
        
        scrollRecPanel = new JScrollPane(recettesPanel);
        scrollRecPanel.setBounds(240, 90, 790, 630); // Définissez la position et la taille du JScrollPane
        scrollRecPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollRecPanel.setName("scrollRecPanel");
        add(scrollRecPanel);
        
        recInfoPanel = new JPanel();
        recInfoPanel.setBounds(90, 90, 975, 545);
        recInfoPanel.setVisible(false);
        recInfoPanel.setName("recInfoPanel");
        add(recInfoPanel);	
        
        ImageIcon trashIcon = new ImageIcon("./assets/trash_icone.png"); // Remplacez par le chemin réel de votre icône
		Image trashImage = trashIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedTrashIcon = new ImageIcon(trashImage);
		trashRecButton = new JButton(resizedTrashIcon);
		trashRecButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		trashRecButton.setBounds(963, 20, 50, 50);
		trashRecButton.setName("trashRecButton");
		trashRecButton.setBackground(Color.white);
		add(trashRecButton);
		
		trashRecButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Afficher une boîte de dialogue de confirmation
		        int confirmation = JOptionPane.showConfirmDialog(RecPanel.this, "Confirmer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
		        // Vérifier si l'utilisateur a cliqué sur 'Oui' (option 0)
		        if (confirmation == JOptionPane.YES_OPTION) {
		            // Supprimer la recette de la liste et mettre à jour l'interface graphique
		            RecetteService.removeAllRecettesFromUser(UserIdConnection.getUserId());
		            // Effacer le panneau des recettes et mettre à jour l'interface graphique
		            recettesPanel.removeAll();
		            recettesPanel.revalidate();
		            recettesPanel.repaint();
		        }
		    }
		});
    }
    
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
    
    private void searchRecettesFromAPI(String query) {
        try {     
            StringBuilder ingredientsList = new StringBuilder();
            // Récupérer les ingrédients de la liste de l'utilisateur
            ArrayList<IngredientDTO> ingredientList = IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
            for (IngredientDTO ingredient : ingredientList) {
                ingredientsList.append(ingredient.getName()).append(",");
            }
            // Supprimer la virgule finale
            if (ingredientsList.length() > 0) {
                ingredientsList.deleteCharAt(ingredientsList.length() - 1);
            }
            String intolerances=UserSettingsFrame.getIntolerancesForRecipes();
            String apiUrl ="";
            if("".equals(query)) {
            	apiUrl = "https://api.spoonacular.com/recipes/complexSearch?apiKey=" + ApiKey.getApiKey() + "&query="+query+"&includeIngredients=" + ingredientsList.toString()+"&intolerances="+intolerances+"&sort=max-used-ingredients";
            }
            else {
                apiUrl = "https://api.spoonacular.com/recipes/complexSearch?apiKey=" + ApiKey.getApiKey() + "&query="+query+"&includeIngredients=" + ingredientsList.toString()+"&intolerances="+intolerances+"&sort=max-used-ingredients";
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

                // Analyser la réponse JSON et afficher les résultats dans le panneau des résultats de recherche
                handleRecAPIResponse(response.toString());
            } else {
                // Gérer la réponse d'erreur
                System.out.println("Erreur : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void handleRecAPIResponse(String jsonResponse) {
        try {
        	Gson gson = new Gson();
        	JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");
            // Effacer les composants existants dans searchRecResultsPanel
            searchRecResultsPanel.removeAll();
            int numberOfRecipes = jsonObject.get("totalResults").getAsInt();
            if(numberOfRecipes==0) {
            	JOptionPane.showMessageDialog(this,"Aucune recette trouvée !");
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
        	    		searchCard.setPreferredSize(new Dimension(250, 250)); // Ajuster la taille selon les besoins
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
                                // Masquer le panneau des résultats de recherche
                                searchRecResultsPanel.setVisible(false);
                                scrollRecResultsPanel.setVisible(false);
                                recettesPanel.setVisible(true);
                                scrollRecPanel.setVisible(true);
                                recetteNameField.setText("");
                            }
                        });
                        searchCard.add(btnRecette);
            	        // Ajouter le bouton à searchRecResultsPanel
            	        searchRecResultsPanel.add(searchCard);
            		} catch (MalformedURLException e) {
            			e.printStackTrace();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
                }
            }
            // Revalider et repeindre pour mettre à jour searchRecResultsPanel
            searchRecResultsPanel.revalidate();
            searchRecResultsPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

	public JButton getIngRecButton() {
		return ing_recButton;
	}

	public JButton getIngButton() {
		return ingButton;
	}

	public JButton getRecButton() {
		return recButton;
	}

	public static JPanel getRecInfoPanel() {
		return recInfoPanel;
	}
}
