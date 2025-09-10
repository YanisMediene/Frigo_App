package com.tse.app.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.tse.app.DTO.RecetteDTO;
import com.tse.app.service.RecetteService;
import com.tse.app.util.UserIdConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecCardIng extends JLayeredPane {
    private static final long serialVersionUID = 1L;

    // Constructeur de la carte de recette avec ingrédient
    public RecCardIng(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel) {
        createRecettePanel(recette, recettesPanel, recInfoPanel);
    }

    // Méthode pour créer le panneau de recette avec ingrédient
    private void createRecettePanel(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel) {
        // Définir le layout du panneau
        setLayout(null);
        setPreferredSize(new Dimension(250, 250));

        // Ajouter le titre de la recette
        JLabel recTitleLabel = new JLabel(recette.getTitle());
        recTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recTitleLabel.setBounds(0, 230, 250, 20);
        recTitleLabel.setBackground(Color.white);
        add(recTitleLabel, JLayeredPane.PALETTE_LAYER);

        // Ajouter le bouton favori
        ImageIcon favIcon = new ImageIcon("./assets/fav_icone.png");
        Image favImage = favIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedFavIcon = new ImageIcon(favImage);
        JButton favButton = new JButton(resizedFavIcon);
        favButton.setBounds(5, 5, 25, 25);
        favButton.setBackground(Color.white);
        add(favButton, JLayeredPane.PALETTE_LAYER);

        // Ajouter un écouteur pour le bouton favori
        favButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Supprimer la recette de la liste et mettre à jour l'interface graphique
                RecetteService.switchFavorieStateRecette(recette);
                recettesPanel.removeAll();
                ArrayList<RecetteDTO> recetteList = RecetteService.getAllRecettesForUser(UserIdConnection.getUserId());
                for (RecetteDTO recette : recetteList) {
                    RecCard recCardPanel = new RecCard(recette, recettesPanel, recInfoPanel);
                    recettesPanel.add(recCardPanel.getRecetteCardPanel());
                }
                recettesPanel.revalidate();
                recettesPanel.repaint();
            }
        });

        // Ajouter le bouton de suppression
        ImageIcon deleteIcon = new ImageIcon("./assets/delete_icone.png");
        Image deleteImage = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedDeleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(resizedDeleteIcon);
        deleteButton.setBounds(220, 5, 25, 25);
        deleteButton.setBackground(Color.white);
        add(deleteButton, JLayeredPane.PALETTE_LAYER);

        // Ajouter un écouteur pour le bouton de suppression
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Supprimer la recette de l'utilisateur et mettre à jour l'interface graphique
                RecetteService.removeRecetteFromUser(recette);
                recettesPanel.remove(RecCardIng.this);
                recettesPanel.revalidate();
                recettesPanel.repaint();
            }
        });

        // Récupérer l'URL de l'image de la recette
        String url = recette.getImage();

        try {
            // Charger l'image depuis l'URL
            BufferedImage img = ImageIO.read(new URL(url));
            // Redimensionner l'image pour s'adapter aux dimensions du bouton
            Image scaledImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
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
            btnRecette.setBounds(0, 0, 250, 250);
            btnRecette.setBorder(border);
            btnRecette.setBackground(Color.white);

            // Ajouter un écouteur pour le bouton de recette
            btnRecette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Masquer les composants du panneau parent
                    Container panel = getRecetteCardPanel().getParent().getParent().getParent().getParent();
                    Component[] components = panel.getComponents();
                    for (Component component : components) {
                        component.setVisible(false);
                    }

                    // Afficher le panneau d'informations sur la recette
                    recInfoPanel.add(new RecInfoCard(recette, recettesPanel, recInfoPanel, panel).getRecetteCardPanel());
                    recInfoPanel.setVisible(true);
                    recInfoPanel.revalidate();
                    recInfoPanel.repaint();
                }
            });

            // Ajouter le bouton de recette au panneau
            add(btnRecette, BorderLayout.CENTER);

        } catch (MalformedURLException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        } catch (IOException e) {
            // Gérer ou journaliser l'exception
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir le panneau de carte de recette
    public JLayeredPane getRecetteCardPanel() {
        return this;
    }
}

