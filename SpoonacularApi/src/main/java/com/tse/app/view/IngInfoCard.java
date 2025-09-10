package com.tse.app.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.tse.app.DTO.IngredientDTO;
import com.tse.app.service.IngredientService;
import com.tse.app.util.UserIdConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IngInfoCard extends JPanel {
    private static final long serialVersionUID = 1L;

    // Constructeur de la classe
    public IngInfoCard(IngredientDTO ingredient, JPanel ingredientsPanel, JPanel ingInfoPanel, Container panel) {
        // Appelle la méthode pour créer le panneau de la carte d'information sur l'ingrédient
        createIngredientPanel(ingredient, ingredientsPanel, ingInfoPanel, panel);
    }

    // Méthode pour créer le panneau de la carte d'information sur l'ingrédient
    private void createIngredientPanel(IngredientDTO ingredient, JPanel ingredientsPanel, JPanel ingInfoPanel, Container panel) {
        // Définir la taille préférée du panneau
        setPreferredSize(new Dimension(965, 535));
        setBorder(new LineBorder(Color.BLACK, 5));
        setLayout(null);

        // Étiquette pour afficher le nom de l'ingrédient
        JLabel ingNameLabel = new JLabel(ingredient.getName());
        ingNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingNameLabel.setBounds(15, 15, 1000, 50);
        ingNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
        add(ingNameLabel);

        // Icône de suppression avec bouton
        ImageIcon deleteIcon = new ImageIcon("./assets/delete_icone.png"); // Remplacer par le chemin réel de votre icône
        Image deleteImage = deleteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedDeleteIcon = new ImageIcon(deleteImage);
        JButton exitButton = new JButton(resizedDeleteIcon);
        exitButton.setBounds(915, 15, 35, 35);
        add(exitButton);

        // Ajouter un écouteur pour gérer la fermeture de la carte d'information sur l'ingrédient
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rendre visible les composants du conteneur principal
                Component[] components = panel.getComponents();
                for (Component component : components) {
                    if (component.getName() != "searchIngResultsPanel" && component.getName() != "scrollIngResultsPanel" && component.getName() != "ingInfoPanel" && component.getName() != "searchRecResultsPanel" && component.getName() != "scrollRecResultsPanel" && component.getName() != "recInfoPanel") {
                        component.setVisible(true);
                    }
                }
                // Cacher le panneau d'information sur l'ingrédient
                ingInfoPanel.setVisible(false);
                ingInfoPanel.remove(IngInfoCard.this);
                ingInfoPanel.revalidate();
                ingInfoPanel.repaint();
            }
        });

        // Étiquette pour afficher la quantité de l'ingrédient
        JLabel ingLabelQuantity = new JLabel("Quantité : ");
        ingLabelQuantity.setBounds(480, 150, 150, 40);
        ingLabelQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(ingLabelQuantity);

        // Champ de texte pour entrer la quantité de l'ingrédient
        JTextField ingQuantity = new JTextField("" + ingredient.getAmount());
        ingQuantity.setBounds(580, 155, 100, 30);
        add(ingQuantity);
        ingQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));

        // Étiquette pour afficher l'unité de mesure de l'ingrédient
        JLabel ingLabelUnit = new JLabel("Unité (pièce, g ou ml) : ");
        ingLabelUnit.setBounds(480, 220, 400, 40);
        ingLabelUnit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(ingLabelUnit);

        // Champ de texte pour entrer l'unité de mesure de l'ingrédient
        JTextField ingUnit = new JTextField();
        ingUnit.setBounds(730, 225, 100, 30);
        ingUnit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(ingUnit);

        // Étiquette pour afficher la date de péremption de l'ingrédient
        JLabel ingLabelPeremptionDate = new JLabel("Date de péremption : ");
        ingLabelPeremptionDate.setBounds(480, 290, 350, 40);
        ingLabelPeremptionDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(ingLabelPeremptionDate);

        // Champ de texte pour afficher la date de péremption de l'ingrédient
        JTextField ingPeremptionDate = new JTextField("" + ingredient.getPeremptionDate());
        ingPeremptionDate.setBounds(650, 295, 175, 30);
        ingPeremptionDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(ingPeremptionDate);

        // Bouton pour modifier/ajouter les informations sur l'ingrédient
        JButton modifyButton = new JButton("Modifier/Ajouter");
        modifyButton.setBounds(750, 460, 200, 60);
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        modifyButton.setBackground(Color.white);
        add(modifyButton);

        // Ajouter un écouteur pour gérer la modification des informations sur l'ingrédient
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int unit_id = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date;
                try {
                    // Changement des informations de l'ingrédient
                    date = sdf.parse(ingPeremptionDate.getText());
                    String unit = ingUnit.getText();
                    if ("g".equals(unit) || "ml".equals(unit) || "piece".equals(unit)) {
                        if ("g".equals(unit)) {
                            unit_id = 2;
                        }
                        if ("ml".equals(unit)) {
                            unit_id = 3;
                        }
                        if ("piece".equals(unit)) {
                            unit_id = 1;
                        }
                        Double newQuantity = Double.parseDouble(ingQuantity.getText());
                        IngredientDTO ingredientUpdated = new IngredientDTO(UserIdConnection.getUserId(), ingredient.getIngredientId(), ingredient.getName(), ingredient.getImage(), newQuantity, unit_id, date);
                        IngredientService.updateIngredientInUser(ingredientUpdated, UserIdConnection.getUserId());
                        // Actualiser la Card D'info
                        Component[] components = panel.getComponents();
                        for (Component component : components) {
                            if (component.getName() != "searchIngResultsPanel" && component.getName() != "scrollIngResultsPanel" && component.getName() != "ingInfoPanel" && component.getName() != "searchRecResultsPanel" && component.getName() != "scrollRecResultsPanel" && component.getName() != "recInfoPanel") {
                                component.setVisible(true);
                            }
                        }
                        ingInfoPanel.setVisible(false);
                        ingInfoPanel.remove(IngInfoCard.this);
                        ingInfoPanel.revalidate();
                        ingInfoPanel.repaint();
                        // Actualiser la liste des ingrédients
                        ingredientsPanel.removeAll();
                        ArrayList<IngredientDTO> ingredientList = IngredientService.getAllIngredientsForUser(UserIdConnection.getUserId());
                        for (IngredientDTO ingredient : ingredientList) {
                            IngCard ingCardPanel = new IngCard(ingredient, ingredientsPanel, ingInfoPanel);
                            ingredientsPanel.add(ingCardPanel.getIngCardPanel());
                        }
                        ingredientsPanel.revalidate();
                        ingredientsPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(IngInfoCard.this, "Veuillez entrer une unité correcte");
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        });

        // URL de l'image de l'ingrédient
        String url = "https://spoonacular.com/cdn/ingredients_250x250/" + ingredient.getImage();
        try {
            BufferedImage img = ImageIO.read(new URL(url));

            // Mise à l'échelle de l'image pour s'adapter aux dimensions du bouton
            int buttonWidth = 450;
            int buttonHeight = 450;
            Image scaledImage = img.getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            Date dateActuelle = new Date(); // Récupérer la date actuelle
            Date peremptionDate = ingredient.getPeremptionDate();

            // Calcul de la différence en millisecondes entre les deux dates
            long differenceEnMillis = peremptionDate.getTime() - dateActuelle.getTime();

            // Convertir la différence en jours
            long differenceEnJours = differenceEnMillis / (24 * 60 * 60 * 1000);

            LineBorder bordure;

            // Ajuster la couleur de la bordure en fonction du temps restant
            if (differenceEnJours > 30) {
                // Plus d'un mois restant (couleur verte)
                bordure = new LineBorder(Color.GREEN, 2);
            } else if (differenceEnJours >= 7) {
                // Entre 1 mois et 1 semaine (couleur orange)
                bordure = new LineBorder(Color.ORANGE, 2);
            } else {
                // Moins d'une semaine restante (couleur rouge)
                bordure = new LineBorder(Color.RED, 2);
            }

            // Bouton pour afficher l'image de l'ingrédient avec la bordure colorée
            JButton btnIngredient = new JButton(scaledIcon);
            btnIngredient.setBounds(15, 70, 450, 450);
            btnIngredient.setBorder(bordure);
            btnIngredient.setBackground(Color.white);
            add(btnIngredient);

        } catch (MalformedURLException e) {
            // Gérer ou consigner l'exception
            e.printStackTrace();
        } catch (IOException e) {
            // Gérer ou consigner l'exception
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir le panneau de la carte d'information sur l'ingrédient
    public JPanel getIngCardPanel() {
        return this;
    }
}
