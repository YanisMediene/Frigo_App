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

public class RecCardFav extends JLayeredPane {
    private static final long serialVersionUID = 1L;

    // Constructeur de la carte de recette favorite
    public RecCardFav(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel) {
        createFavRecettePanel(recette, recettesPanel, recInfoPanel);
    }

    // Méthode pour créer le panneau de recette favorite
    public void createFavRecettePanel(RecetteDTO recette, JPanel recettesPanel, JPanel recInfoPanel) {
        // Définir le layout du panneau
        setLayout(null);
        setPreferredSize(new Dimension(250, 250));

        // Ajouter le titre de la recette
        JLabel recTitleLabel = new JLabel(recette.getTitle());
        recTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recTitleLabel.setBounds(0, 230, 250, 20);
        recTitleLabel.setBackground(Color.white);
        add(recTitleLabel, JLayeredPane.PALETTE_LAYER);

        // Récupérer l'URL de l'image de la recette
        String url = recette.getImage();

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
                // Changer l'état de favori de la recette et mettre à jour l'interface graphique
                RecetteService.switchFavorieStateRecette(recette);
                FavoritePanel.showFavList();
                /*
                // Supprimer la recette de la liste et mettre à jour l'interface graphique
                RecetteService.switchFavorieStateRecette(recette);
                recettesPanel.removeAll();
                ArrayList<RecetteDTO> recetteList = RecetteService.getAllFavRecettesForUser(UserIdConnection.getUserId());
                for (RecetteDTO recette : recetteList) {
                    RecCard recCardPanel = new RecCard(recette, recettesPanel, recInfoPanel);
                    recettesPanel.add(recCardPanel.getRecetteCardPanel());
                }
                */
            }
        });

        try {
            // Charger l'image depuis l'URL
            BufferedImage img = ImageIO.read(new URL(url));
            Image scaledImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Ajouter le bouton de recette avec l'image
            JButton btnRecette = new JButton(scaledIcon);
            btnRecette.setBounds(0, 0, 250, 250);
            btnRecette.setBackground(Color.white);

            // Ajouter un écouteur pour le bouton de recette
            btnRecette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Masquer les composants du panneau parent
                    Container panel = getParent().getParent().getParent().getParent();
                    Component[] components = panel.getComponents();
                    for (Component component : components) {
                        component.setVisible(false);
                    }

                    // Afficher le panneau d'informations sur la recette
                    recInfoPanel.setVisible(true);
                    recInfoPanel.add(new RecInfoCard(recette, recettesPanel, recInfoPanel, panel).getRecetteCardPanel());
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
}
