package com.tse.app.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tse.app.DTO.IngredientDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.IngredientForRecette;
import com.tse.app.model.RecetteNutrition;
import com.tse.app.model.RecipeDetails;
import com.tse.app.service.IngredientService;
import com.tse.app.service.RecetteService;
import com.tse.app.util.ApiKey;
import com.tse.app.util.UserIdConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class RecInfoCard extends JPanel {
    private static final long serialVersionUID = 1L;

    // Constructeur pour la carte d'informations sur la recette
    public RecInfoCard(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel, Container panel) {
        createRecettePanel(recette, recettesPanel, recInfoPanel, panel);
    }

    // Méthode pour créer le panneau d'informations sur la recette
    private void createRecettePanel(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel, Container panel) {
        // Définir la taille préférée du panneau
        setPreferredSize(new Dimension(965, 535));
        // Définir la bordure du panneau
        setBorder(new LineBorder(Color.BLACK, 5));
        // Définir le layout du panneau comme nul
        setLayout(null);

        // Ajouter le titre de la recette
        JLabel recTitleLabel = new JLabel(recette.getTitle());
        recTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recTitleLabel.setBounds(190, 15, 725, 35);
        recTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        add(recTitleLabel);

        // Ajouter un bouton pour vérifier les ingrédients
        JButton checkIngButton = new JButton("Check Ingredients");
        checkIngButton.setBackground(Color.white);
        checkIngButton.setBounds(15, 15, 175, 35);
        checkIngButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(checkIngButton);

        // Ajouter un écouteur pour le bouton de vérification des ingrédients
        checkIngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<IngredientForRecette> listeRecette=RecetteService.getListIngredientRecette(recette);
            	List<IngredientDTO> listeUser=IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
            	List<IngredientForRecette> missedIngredients=new ArrayList<>();
            	List<IngredientForRecette> usableIngredientsRecette=new ArrayList<>();
            	List<IngredientDTO> usableIngredientsUser=new ArrayList<>();
            	List<String> listNameRecette =  new ArrayList<>();
            	List<String> listNameUser =  new ArrayList<>();
            	for (IngredientForRecette ingredient : listeRecette) {
            		listNameRecette.add(ingredient.getName());
            	}
            	for (IngredientDTO ingredient : listeUser) {
            		listNameUser.add(ingredient.getName());
            	}
            	if(listNameUser.size()==0) {
            		missedIngredients=listeRecette;
            	}
            	else {
            		for(int i=0;i<listNameUser.size();i++) {
                		for (int j=0;j<listNameRecette.size();j++) {
                			String plural=listNameRecette.get(j)+"s";
                	        if (listNameRecette.get(j).toLowerCase().equals(listNameUser.get(i).toLowerCase())||plural.equals(listNameUser.get(i).toLowerCase())) {
                	        	usableIngredientsRecette.add(listeRecette.get(j));
                	        	usableIngredientsUser.add(listeUser.get(i));
                	        }
                		}
                	}
                	for(int i=0;i<listeRecette.size();i++) {	
            	        if (!(usableIngredientsRecette.contains(listeRecette.get(i)))) {
            	        	missedIngredients.add(listeRecette.get(i));
            	        }
                	}
            	}
				if (usableIngredientsUser.size()==listeRecette.size()) {
	            	int confirmation = JOptionPane.showConfirmDialog(RecInfoCard.this, "You have everything !!! Do the recipe ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			        if (confirmation == JOptionPane.YES_OPTION) {	
			        	List<IngredientForRecette> ingToAddToList=changeUserIngredients(recette,usableIngredientsRecette, missedIngredients,usableIngredientsUser);
			        }
	            }
				else {
	            	int confirmation = JOptionPane.showConfirmDialog(RecInfoCard.this, "Missing Ingredients !!! Do the recipe ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			        if (confirmation == JOptionPane.YES_OPTION) {	
			        	RecetteService.switchToInProgressStateRecette(recette);
			        	List<IngredientForRecette> ingToAddToList=changeUserIngredients(recette,usableIngredientsRecette, missedIngredients,usableIngredientsUser);
			        	for (int i=0;i<ingToAddToList.size();i++) {
			        		missedIngredients.add(ingToAddToList.get(i));
			        	}
			        	ShoppingListPanel.showRecetteList();
			        	ShoppingListPanel.buildShoppingList(recette,missedIngredients, ShoppingListPanel.getTextArea(),ShoppingListPanel.getStringBuilder());
			        }
				}
            }});

        // Ajouter le bouton pour quitter la vue des détails de la recette
        ImageIcon deleteIcon = new ImageIcon("./assets/delete_icone.png");
        Image deleteImage = deleteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedDeleteIcon = new ImageIcon(deleteImage);
        JButton exitButton = new JButton(resizedDeleteIcon);
        exitButton.setBounds(915, 15, 35, 35);
        add(exitButton);

        // Ajouter un écouteur pour le bouton de sortie
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restaurer la visibilité des composants dans le panneau parent
                Component[] components = panel.getComponents();
                for (Component component : components) {
                    if (component.getName() != "searchIngResultsPanel" && component.getName() != "scrollIngResultsPanel" &&
                        component.getName() != "ingInfoPanel" && component.getName() != "searchRecResultsPanel" &&
                        component.getName() != "scrollRecResultsPanel" && component.getName() != "recInfoPanel") {
                        component.setVisible(true);
                    }
                }
                // Cacher et retirer le panneau d'informations sur la recette
                recInfoPanel.setVisible(false);
                recInfoPanel.remove(RecInfoCard.this);
                recInfoPanel.revalidate();
                recInfoPanel.repaint();
            }
        });

        // Récupérer l'URL de l'image de la recette
        String url = recette.getImage();
        try {
            BufferedImage img = ImageIO.read(new URL(url));

            // Redimensionner l'image pour s'adapter aux dimensions du bouton
            int buttonWidth = 450;
            int buttonHeight = 450;
            Image scaledImage = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Définir la bordure en fonction de l'état favori
            LineBorder border;
            if (recette.isFavorite()) {
                border = new LineBorder(Color.YELLOW, 2);
            } else {
                border = new LineBorder(Color.WHITE, 2);
            }

            // Ajouter le bouton de recette avec l'image
            JButton btnRecette = new JButton(scaledIcon);
            btnRecette.setBounds(15, 70, 450, 450);
            btnRecette.setBorder(border);
            add(btnRecette, BorderLayout.CENTER);

        } catch (MalformedURLException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        } catch (IOException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        }

        // Ajouter une zone de texte pour les détails de la recette
        JTextArea recDetails = new JTextArea();
        recDetails.setBackground(recInfoPanel.getBackground());
        recDetails.setEditable(false);
        recDetails.setLineWrap(true);
        recDetails.setWrapStyleWord(true);
        recDetails.setBounds(500, 70, 450, 800);

        // Appeler les méthodes pour récupérer et afficher les détails de la recette
        Gson gson = new Gson();
        String recipeDetails = getRecipeDetails(recette.getIdRecette());
        String recetteNutrition = getRecipeNutritionDetails(recette.getIdRecette());
        RecipeDetails recipe = gson.fromJson(recipeDetails, RecipeDetails.class);
        RecetteNutrition nutrition = gson.fromJson(recetteNutrition, RecetteNutrition.class);
        buildDetailsText(recipe, nutrition, recDetails);
        recDetails.setCaretPosition(0);

        // Ajouter la zone de texte à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(recDetails);
        scrollPane.setBounds(500, 70, 450, 450);
        add(scrollPane);
    }

    // Méthode pour obtenir les détails de nutrition de la recette depuis l'API
    private static String getRecipeNutritionDetails(int recipeId) {
        try {
            String recipeNutrition = "https://api.spoonacular.com/recipes/" + recipeId + "/nutritionWidget.json?apiKey=" + ApiKey.getApiKey();
            URL urlNutri = new URL(recipeNutrition);
            HttpURLConnection connectionNutri = (HttpURLConnection) urlNutri.openConnection();
            connectionNutri.setRequestMethod("GET");
            int responseCodeNutri = connectionNutri.getResponseCode();
            if (responseCodeNutri == HttpURLConnection.HTTP_OK) {
                BufferedReader inNutri = new BufferedReader(new InputStreamReader(connectionNutri.getInputStream()));
                StringBuilder responseNutri = new StringBuilder();
                String inputLineNutri;
                while ((inputLineNutri = inNutri.readLine()) != null) {
                    responseNutri.append(inputLineNutri);
                }
                inNutri.close();
                return responseNutri.toString();
            } else {
                System.out.println("Erreur lors de la requête pour obtenir les détails de la nutrition. Code de réponse : " + responseCodeNutri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour obtenir les détails de la recette depuis l'API
    public static String getRecipeDetails(int recipeId) {
        try {
            String recipeDetailsUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + ApiKey.getApiKey();
            URL url = new URL(recipeDetailsUrl);
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
                return response.toString();
            } else {
                System.out.println("Erreur lors de la requête pour obtenir les détails de la recette. Code de réponse : " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour construire le texte des détails de la recette
    private void buildDetailsText(RecipeDetails recipe, RecetteNutrition nutrition, JTextArea detailsTextArea) {
        StringBuilder detailsText = new StringBuilder();
        detailsText.append("Tilte : ").append(recipe.getTitle()).append("\n");
        detailsText.append("\n");
        detailsText.append("HealthScore: ").append(recipe.getHealthScore()).append("\n");
        detailsText.append("\n");
        detailsText.append("Caloric breakdown :\n");
        detailsText.append("- Calories : " + nutrition.getCalories() + "kcal\n");
        detailsText.append("- Fat : " + nutrition.getFat() + "\n");
        detailsText.append("- Carbs : " + nutrition.getCarbs() + "\n");
        detailsText.append("- Proteins :" + nutrition.getProteins() + "\n");
        detailsText.append("\n");
        detailsText.append("Preparation time : ").append(recipe.prepTime(recipe.getReadyInMinutes())).append("\n");
        detailsText.append("\n");
        detailsText.append("Ingredients :\n");
        for (Ingredient ingredient : recipe.getExtendedIngredients()) {
            String unit = ingredient.getMeasures().getMetric().getUnitShort();
            detailsText.append("- ").append(ingredient.getName()).append(" " + ingredient.getMeasures().getMetric().getAmount() + " " + unit).append("\n");
        }
        detailsText.append("\n");
        String instruction = recipe.getInstructions();
        if (instruction == null) {
            detailsText.append("Instructions: unavailable.\n");
            detailsTextArea.setText(detailsText.toString());
        } else if (instruction.toString().contains("<ol>")) {
            detailsText.append("Instructions :\n");
            Pattern liPattern = Pattern.compile("<li>(.*?)</li>");
            // Créer un matcher
            Matcher matcher = liPattern.matcher(recipe.getInstructions());
            // Itérer sur les correspondances et afficher chaque étape
            while (matcher.find()) {
                String step = matcher.group(1);
                detailsText.append(step.trim()).append("\n");
                detailsTextArea.setText(detailsText.toString());
            }
        } else {
            detailsText.append("Instructions :\n");
            detailsText.append(instruction.toString());
            detailsTextArea.setText(detailsText.toString());
        }
    }

    // Méthode pour changer les ingrédients de l'utilisateur
    private List<IngredientForRecette> changeUserIngredients(RecetteDTO recette, List<IngredientForRecette> usableIngredientsRecette, List<IngredientForRecette> missedIngredients, List<IngredientDTO> usableIngredientsUser) {
        List<IngredientForRecette> usableIngredientNotEngought = new ArrayList<IngredientForRecette>();
        Double amountToChange = 0.0;
        for (int i = 0; i < usableIngredientsRecette.size(); i++) {
            String unitRecette = usableIngredientsRecette.get(i).getUnit();
            if (unitRecette.equals(null)) {
                unitRecette = "piece";
            }
            String unitUser = IngredientService.getIngredientUserUnitById(UserIdConnection.getUserId(), usableIngredientsUser.get(i).getIngredientId());
            String name = usableIngredientsUser.get(i).getName();
            Double amountRecette = usableIngredientsRecette.get(i).getAmount();
            Double amountUser = usableIngredientsUser.get(i).getAmount();
            System.out.println(unitUser);
            System.out.println(name);
            if (!(unitUser.equals(unitRecette))) {
                String urlForApi = "https://api.spoonacular.com/recipes/convert?apiKey=" + ApiKey.getApiKey() + "&ingredientName=" + name + "&sourceAmount=" + amountRecette + "&sourceUnit=" + unitRecette + "&targetUnit=" + unitUser;
                try {
                    URL url = new URL(urlForApi);
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
                        JsonParser jsonParser = new JsonParser();
                        JsonObject jsonObject = jsonParser.parse(response.toString()).getAsJsonObject();
                        amountRecette = jsonObject.get("targetAmount").getAsDouble();
                        amountToChange = amountUser - amountRecette;
                    } else {
                    	System.out.println("Erreur lors de la conversion d'unité : " + responseCode);
            		}
            	} 
				catch (Exception e1) {
					e1.printStackTrace();
                }
			}
			else {
				amountToChange=amountUser-amountRecette;
			}
			if(amountToChange<0) {
				int id=usableIngredientsUser.get(i).getIngredientId();
				Double amount=-amountToChange;
				IngredientForRecette notEnoughtIng = new IngredientForRecette(id,name,amount,unitUser);
				usableIngredientNotEngought.add(notEnoughtIng);
			}
			int id=usableIngredientsUser.get(i).getIngredientId();
	        RecetteService.updateIngredientAmount(UserIdConnection.getUserId(), id, amountToChange);
	        Ing_RecPanel.showIngredientList();
		}
    	return usableIngredientNotEngought;
	}
    
    public JPanel getRecetteCardPanel() {
        return this;
    }

}
