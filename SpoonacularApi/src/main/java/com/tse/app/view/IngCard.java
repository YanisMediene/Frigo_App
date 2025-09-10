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
import java.util.Date;

public class IngCard extends JLayeredPane {
    private static final long serialVersionUID = 1L;

    // Constructeur de la classe
    public IngCard(IngredientDTO ingredient, JPanel ingredientsPanel, JPanel ingInfoPanel) {
        // Appelle la méthode pour créer le panneau de la carte d'ingrédient
        createIngredientPanel(ingredient, ingredientsPanel, ingInfoPanel);
    }

    // Méthode pour créer le panneau de la carte d'ingrédient
    private void createIngredientPanel(IngredientDTO ingredient, JPanel ingredientsPanel, JPanel ingInfoPanel) {
        // Définir la taille préférée du panneau
        setPreferredSize(new Dimension(250, 250));
        
        // Étiquette pour afficher le nom de l'ingrédient
        JLabel ingNameLabel = new JLabel(ingredient.getName());
        ingNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingNameLabel.setBounds(50, 220, 150, 30);
        add(ingNameLabel, JLayeredPane.PALETTE_LAYER);
        
        // Icône de suppression avec bouton
        ImageIcon deleteIcon = new ImageIcon("./assets/delete_icone.png"); // Remplacer par le chemin réel de votre icône
        Image deleteImage = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedDeleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(resizedDeleteIcon);
        deleteButton.setBounds(220, 5, 25, 25);
        deleteButton.setBackground(Color.white);
        add(deleteButton, JLayeredPane.PALETTE_LAYER);

        // Ajouter un écouteur pour gérer la suppression de l'ingrédient
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Supprimer l'ingrédient de la liste et mettre à jour l'interface graphique
                IngredientService.removeIngredientFromUser(ingredient);
                ingredientsPanel.remove(IngCard.this);
                ingredientsPanel.revalidate();
                ingredientsPanel.repaint();
            }
        });
        
        // Afficher la quantité de l'ingrédient
        String unitName = IngredientService.getIngredientUserUnitById(UserIdConnection.getUserId(), ingredient.getIngredientId());
        JLabel ingQuantity = new JLabel("" + ingredient.getAmount() + " " + unitName);
        ingQuantity.setBounds(5, 0, 200, 30);
        add(ingQuantity, JLayeredPane.PALETTE_LAYER);
        
        // URL de l'image de l'ingrédient
        String url = "https://spoonacular.com/cdn/ingredients_250x250/" + ingredient.getImage();
        try {
            // Lire l'image depuis l'URL
            BufferedImage img = ImageIO.read(new URL(url));
            // Redimensionner l'image
            Image scaledImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Obtenir la date actuelle
            Date dateActuelle = new Date();
            // Obtenir la date de péremption de l'ingrédient
            Date peremptionDate = ingredient.getPeremptionDate();

            // Calculer la différence en millisecondes entre les deux dates
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

            // Bouton pour afficher l'image de l'ingrédient
            JButton btnIngredient = new JButton(scaledIcon);
            btnIngredient.setBounds(0, 0, 250, 250);
            btnIngredient.setBorder(bordure);
            btnIngredient.setBackground(Color.white);
            
            // Ajouter un écouteur pour gérer le clic sur l'ingrédient
            btnIngredient.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Masquer tous les composants de l'écran principal
                    Container panel = getParent().getParent().getParent().getParent();
                    Component[] components = panel.getComponents();
                    for (Component component : components) {
                        component.setVisible(false);
                    }
                    
                    // Afficher le panneau d'information sur l'ingrédient sélectionné
                    ingInfoPanel.setVisible(true);
                    ingInfoPanel.add(new IngInfoCard(ingredient, ingredientsPanel, ingInfoPanel, panel).getIngCardPanel());
                    ingInfoPanel.revalidate();
                    ingInfoPanel.repaint();
                }
            });
            // Ajouter le bouton à la carte d'ingrédient
            add(btnIngredient);

        } catch (MalformedURLException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        } catch (IOException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir le panneau de la carte d'ingrédient
    public JLayeredPane getIngCardPanel() {
        return IngCard.this;
    }
}

