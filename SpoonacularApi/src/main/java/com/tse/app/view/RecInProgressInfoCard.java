package com.tse.app.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
import com.google.gson.Gson;
import com.tse.app.DTO.IngredientDTO;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.Ingredient;
import com.tse.app.model.RecetteNutrition;
import com.tse.app.model.RecipeDetails;
import com.tse.app.service.IngredientService;
import com.tse.app.service.RecetteService;
import com.tse.app.util.ApiKey;
import com.tse.app.util.UserIdConnection;

public class RecInProgressInfoCard extends JPanel {

    // Constructeur de la classe
    public RecInProgressInfoCard(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel, Container panel) {
        createRecettePanel(recette, recettesPanel, recInfoPanel, panel);
    }

    // Méthode pour créer le panneau d'information de recette
    private void createRecettePanel(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel, Container panel) {
        setPreferredSize(new Dimension(965, 535)); // Ajustez la taille selon vos besoins
        setBorder(new LineBorder(Color.BLACK, 5));
        setLayout(null);

        // Étiquette pour le titre de la recette
        JLabel recTitleLabel = new JLabel(recette.getTitle());
        recTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recTitleLabel.setBounds(15, 15, 1000, 35);
        recTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        add(recTitleLabel);

        // Bouton pour fermer la carte de recette en cours
        ImageIcon deleteIcon = new ImageIcon("./assets/delete_icone.png"); // Remplacez par le chemin réel de votre icône
        Image deleteImage = deleteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedDeleteIcon = new ImageIcon(deleteImage);
        JButton exitButton = new JButton(resizedDeleteIcon);
        exitButton.setBounds(915, 15, 35, 35);
        add(exitButton);

        // Action lorsqu'on clique sur le bouton de fermeture
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Supprimer la recette de la liste et mettre à jour l'interface graphique
                Component[] components = panel.getComponents();
                for (Component component : components) {
                    if (component.getName() != "searchIngResultsPanel" && component.getName() != "scrollIngResultsPanel"
                            && component.getName() != "ingInfoPanel" && component.getName() != "searchRecResultsPanel"
                            && component.getName() != "scrollRecResultsPanel" && component.getName() != "recInfoPanel") {
                        component.setVisible(true);
                    }
                }
                recInfoPanel.setVisible(false);
                recInfoPanel.remove(RecInProgressInfoCard.this);
                recInfoPanel.revalidate();
                recInfoPanel.repaint();
            }
        });

        // Téléchargement et affichage de l'image de la recette
        String url = recette.getImage();
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            int buttonWidth = 450;
            int buttonHeight = 450;
            Image scaledImage = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            LineBorder border;
            if (recette.isFavorite()) {
                border = new LineBorder(Color.YELLOW, 2);
            } else {
                border = new LineBorder(Color.WHITE, 2);
            }

            JButton btnRecette = new JButton(scaledIcon);
            btnRecette.setBounds(15, 70, 450, 450);
            btnRecette.setBorder(border);
            add(btnRecette, BorderLayout.CENTER);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Texte détaillé sur la recette
        JTextArea recDetails = new JTextArea();
        recDetails.setBackground(recInfoPanel.getBackground());
        recDetails.setEditable(false);
        recDetails.setLineWrap(true);
        recDetails.setWrapStyleWord(true);
        recDetails.setBounds(500, 70, 450, 800);

        // Récupération des détails de la recette et de la nutrition
        Gson gson = new Gson();
        String recipeDetails = getRecipeDetails(recette.getIdRecette());
        String recetteNutrition = getRecipeNutritionDetails(recette.getIdRecette());
        RecipeDetails recipe = gson.fromJson(recipeDetails, RecipeDetails.class);
        RecetteNutrition nutrition = gson.fromJson(recetteNutrition, RecetteNutrition.class);
        buildDetailsText(recipe, nutrition, recDetails);
        recDetails.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(recDetails);
        scrollPane.setBounds(500, 70, 450, 450);
        add(scrollPane);
    }

    // Méthode pour obtenir les détails de nutrition d'une recette
    private static String getRecipeNutritionDetails(int recipeId) {
        try {
            String recipeNutrition = " https://api.spoonacular.com/recipes/" + recipeId
                    + "/nutritionWidget.json?apiKey=" + ApiKey.getApiKey();
            URL urlNutri = new URL(recipeNutrition);
            HttpURLConnection connectionNutri = (HttpURLConnection) urlNutri.openConnection();
            connectionNutri.setRequestMethod("GET");
            int responseCodeNutri = connectionNutri.getResponseCode();
            if (responseCodeNutri == HttpURLConnection.HTTP_OK) {
                BufferedReader inNutri = new BufferedReader(new InputStreamReader(connectionNutri.getInputStream()));
                String inputLineNutri;
                StringBuilder responseNutri = new StringBuilder();
                while ((inputLineNutri = inNutri.readLine()) != null) {
                    responseNutri.append(inputLineNutri);
                }
                inNutri.close();
                return responseNutri.toString();
            } else {
                System.out.println("Erreur lors de la requête pour obtenir les détails de la nutrition. Code de réponse : "
                        + responseCodeNutri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour obtenir les détails d'une recette
    public static String getRecipeDetails(int recipeId) {
        try {
            String recipeDetailsUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey="
                    + ApiKey.getApiKey();
            URL url = new URL(recipeDetailsUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("Erreur lors de la requête pour obtenir les détails de la recette. Code de réponse : "
                        + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour construire le texte détaillé sur la recette
    private void buildDetailsText(RecipeDetails recipe, RecetteNutrition nutrition, JTextArea detailsTextArea) {
        StringBuilder detailsText = new StringBuilder();
        detailsText.append("Titre : ").append(recipe.getTitle()).append("\n");
        detailsText.append("\n");
        detailsText.append("Score de santé : ").append(recipe.getHealthScore()).append("\n");
        detailsText.append("\n");
        detailsText.append("Répartition calorique :\n");
        detailsText.append("- Calories : " + nutrition.getCalories() + "kcal\n");
        detailsText.append("- Graisses : " + nutrition.getFat() + "\n");
        detailsText.append("- Glucides : " + nutrition.getCarbs() + "\n");
        detailsText.append("- Protéines : " + nutrition.getProteins() + "\n");
        detailsText.append("\n");
        detailsText.append("Temps de préparation : ").append(recipe.prepTime(recipe.getReadyInMinutes())).append("\n");
        detailsText.append("\n");
        detailsText.append("Ingrédients :\n");
        for (Ingredient ingredient : recipe.getExtendedIngredients()) {
            String unit = ingredient.getMeasures().getMetric().getUnitShort();
            detailsText.append("- ").append(ingredient.getName()).append(" ").append(ingredient.getMeasures().getMetric().getAmount())
                    .append(" ").append(unit).append("\n");
        }
        detailsText.append("\n");
        String instruction = recipe.getInstructions();
        if ("".equals(instruction)) {
            detailsText.append("Instructions : non disponibles.\n");
            detailsTextArea.setText(detailsText.toString());
        } else if (instruction.contains("<ol>")) {
            detailsText.append("Instructions :\n");
            Pattern liPattern = Pattern.compile("<li>(.*?)</li>");
            Matcher matcher = liPattern.matcher(recipe.getInstructions());
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

    // Méthode pour obtenir le panneau de la carte de recette
    public JPanel getRecetteCardPanel() {
        return this;
    }
}

