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
import com.tse.app.model.Ingredient;
import com.tse.app.model.UserIngredient;
import com.tse.app.service.IngredientService;
import com.tse.app.util.ApiKey;
import com.tse.app.util.UserIdConnection;

public class IngPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    // Barre latérale
    private JButton userButton;
    private JButton ing_recButton;
    private JButton ingButton;
    private JButton logOut;
    private JButton recButton;
    private JButton shoppingList;
    private JButton favButton;

    // Ingrédients
    private JLabel ingLabel;
    private JTextField ingredientNameField;
    private JButton searchIngButton;
    private JButton trashIngButton;
    private JPanel searchIngResultsPanel;
    private JScrollPane scrollIngResultsPanel;
    private JButton filterIngButton;
    private static JPanel ingredientsPanel;
    private JScrollPane scrollIngPanel;
    private static JPanel ingInfoPanel;

    public IngPanel() {

        setLayout(null);
        setName("IngPanel");

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

        ImageIcon loupeIcon = new ImageIcon("./assets/loupe_icone.png"); // Remplacez par le chemin réel de votre icône
        Image loupeImage = loupeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedLoupeIcon = new ImageIcon(loupeImage);
        searchIngButton = new JButton(resizedLoupeIcon);
        searchIngButton.setBounds(903, 20, 50, 50);
        searchIngButton.setName("searchIngButton");
        searchIngButton.setBackground(Color.white);
        add(searchIngButton);

        searchIngResultsPanel = new JPanel();
        searchIngResultsPanel.setVisible(false);  // Set the initial visibility to false
        searchIngResultsPanel.setLayout(new GridLayout(0, 3)); // Using a single column for vertical arrangement
        searchIngResultsPanel.setName("searchIngResultsPanel");

        scrollIngResultsPanel = new JScrollPane(searchIngResultsPanel);
        scrollIngResultsPanel.setBounds(240, 90, 810, 640); // Définissez la position et la taille du JScrollPane
        scrollIngResultsPanel.setVisible(false);
        scrollIngResultsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
                    JOptionPane.showMessageDialog(IngPanel.this, "Veuillez entrer un ingrédient.");
                    return;
                }
                // Effectuer la requête API lorsque le bouton "Rechercher" est cliqué
                searchIngredientsFromAPI(ingredientName);
                // Afficher le panneau des résultats de recherche
                searchIngResultsPanel.setVisible(true);
                scrollIngResultsPanel.setVisible(true);
                ingredientsPanel.setVisible(false);
                scrollIngPanel.setVisible(false);
                // Effacer les champs de saisie après la recherche
                ingredientNameField.setText("");
            }
        });

        ImageIcon trashIcon = new ImageIcon("./assets/trash_icone.png"); // Remplacez par le chemin réel de votre icône
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
                // Afficher une boîte de dialogue de confirmation
                int confirmation = JOptionPane.showConfirmDialog(IngPanel.this, "Confirmer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                // Vérifier si l'utilisateur a cliqué sur 'Oui' (option 0)
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Supprimer l'ingrédient de la liste et mettre à jour l'interface graphique
                    IngredientService.removeAllIngredientsFromUser(UserIdConnection.getUserId());
                    // Effacer le panneau des ingrédients et mettre à jour l'interface graphique
                    ingredientsPanel.removeAll();
                    ingredientsPanel.revalidate();
                    ingredientsPanel.repaint();
                }
            }
        });

        ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new GridLayout(0, 3)); // Utilisation d'une seule colonne pour un arrangement vertical
        ingredientsPanel.setName("ingredientsPanel");

        scrollIngPanel = new JScrollPane(ingredientsPanel);
        scrollIngPanel.setBounds(240, 90, 790, 630); // Définissez la position et la taille du JScrollPane
        scrollIngPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollIngPanel.setName("scrollIngPanel");
        add(scrollIngPanel);

        ingInfoPanel = new JPanel();
        ingInfoPanel.setBounds(90, 90, 975, 545);
        ingInfoPanel.setVisible(false);
        ingInfoPanel.setName("ingInfoPanel");
        add(ingInfoPanel);
    }

    public static void showIngredientList() {
        // Afficher chaque ingrédient dans le frigo
        ingredientsPanel.removeAll();
        ArrayList<IngredientDTO> ingredientList = IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
        for (IngredientDTO ingredient : ingredientList) {
            IngCard ingCardPanel = new IngCard(ingredient, ingredientsPanel, ingInfoPanel);
            ingredientsPanel.add(ingCardPanel.getIngCardPanel());
        }
        ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
    }

    private void handleIngAPIResponse(String jsonResponse) {
        try {
            Gson gson = new Gson();
            JsonObject responseJson = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray resultsArray = responseJson.getAsJsonArray("results");
            // Effacer les composants existants dans le panneau de résultats de recherche
            searchIngResultsPanel.removeAll();
            for (int i = 0; i < resultsArray.size(); i++) {
                JsonObject ingredientObject = resultsArray.get(i).getAsJsonObject();
                int ingredientId = ingredientObject.get("id").getAsInt();
                String ingredientName = ingredientObject.get("name").getAsString();
                String ingredientImage = ingredientObject.get("image").getAsString();
                Ingredient ing = new Ingredient(ingredientId, ingredientName, ingredientImage);
                // Définir la date d'expiration un mois après la date actuelle, par défaut
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.MONTH, 1);
                Date nextMonthDate = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateString = sdf.format(nextMonthDate);
                Date date = sdf.parse(dateString);
                UserIngredient user_ing = new UserIngredient(UserIdConnection.getUserId(), ingredientId, 0L, 0, date);
                IngredientDTO ing_dto = new IngredientDTO(UserIdConnection.getUserId(), ingredientId, ingredientName, ingredientImage, 1.0, 1, date);
                String url = "https://spoonacular.com/cdn/ingredients_250x250/" + ing_dto.getImage();
                JLayeredPane searchCard = new JLayeredPane();
                searchCard.setBounds(0, 0, 250, 250);
                searchCard.setPreferredSize(new Dimension(250, 250)); // Ajustez la taille selon les besoins
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
                    // Gérer ou consigner l'exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Gérer ou consigner l'exception
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

                // Analyser la réponse JSON et afficher les résultats dans le panneau de résultats de recherche
                handleIngAPIResponse(response.toString());

                // Afficher le panneau des résultats de recherche
                searchIngResultsPanel.setVisible(true);
            } else {
                // Gérer la réponse d'erreur
                System.out.println("Erreur : " + responseCode);
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
	
	public JButton getEditIngButton() {
		return filterIngButton;
	}
	
	public static JPanel getIngredientsPanel() {
		return ingredientsPanel;
	}
	
	public static JPanel getIngInfoPanel() {
		return ingInfoPanel;
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
}
