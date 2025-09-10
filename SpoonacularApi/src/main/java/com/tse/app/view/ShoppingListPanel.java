package com.tse.app.view;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import com.tse.app.DTO.RecetteDTO;
import com.tse.app.model.IngredientForRecette;
import com.tse.app.service.RecetteService;
import com.tse.app.util.UserIdConnection;
//Panneau principal pour la liste de courses
public class ShoppingListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// Barre latérale avec des boutons pour les fonctionnalités
	private JButton userButton;
	private JButton ing_recButton;
	private JButton ingButton;
	private JButton logOut;
	private JButton shoppingList;
	private JButton recButton;
	private JButton downloadButton;
	private JButton trashButton;
	// Ingredients
	private JLabel listLabel;
    private static JPanel listPanel;
	private JScrollPane scrollRecetteListPanel;
	private JButton favButton;
    private static JPanel recInfoPanel;
    private JScrollPane scrollListPanel;
    private static JTextArea liste;
    private static StringBuilder detailsText;
 // Constructeur du panneau de liste de courses
    public ShoppingListPanel() {
    	// Initialisation du layout et du texte détaillé
    	setLayout(null);
    	detailsText = new StringBuilder();
    	 // Création du bouton utilisateur avec une icône
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
		
		listLabel = new JLabel("SHOPPING LIST");
		listLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		listLabel.setBounds(220, 20, 320, 50);
		listLabel.setName("listLabel");
		add(listLabel);
		
		 // Gestionnaire d'événements pour le bouton utilisateur
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserSettingsFrame userSettingsFrame = new UserSettingsFrame();
            	UserSettingsFrame.initialiseCheckboxes();
            }
        });

        listPanel = new JPanel();
        listPanel.setBounds(240, 90, 843, 280);
        listPanel.setName("listPanel");
        add(listPanel);
        
        scrollRecetteListPanel = new JScrollPane(listPanel);
        scrollRecetteListPanel.setBounds(240, 90, 843, 280); // Définissez la position et la taille du JScrollPane
        scrollRecetteListPanel.setName("scrollRecetteListPanel");
        add(scrollRecetteListPanel);
        
        liste = new JTextArea();
        liste.setEditable(false);
        liste.setLineWrap(true);
        liste.setWrapStyleWord(true);
        liste.setBackground(listPanel.getBackground());
        liste.setBounds(240, 380, 843, 350);
        
        scrollListPanel = new JScrollPane(liste);
        scrollListPanel.setBounds(240, 380, 843, 350);
        add(scrollListPanel);
        
        ImageIcon downloadIcon = new ImageIcon("./assets/download.png"); // Remplacez par le chemin réel de votre icône
		Image downloadImage = downloadIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedDownloadIcon = new ImageIcon(downloadImage);
		// Bouton pour télécharger la liste de courses au format PDF
		downloadButton = new JButton(resizedDownloadIcon);
		downloadButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		downloadButton.setBounds(1033, 20, 50, 50);
		downloadButton.setName("downloadButton");
		downloadButton.setBackground(Color.white);
		add(downloadButton); 
		// Gestionnaire d'événements pour le bouton de téléchargement
		downloadButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		        	// Création d'une boîte de dialogue pour choisir l'emplacement du fichier
		            JFileChooser fileChooser = new JFileChooser();
		            fileChooser.setDialogTitle("Save PDF");
		            int userSelection = fileChooser.showSaveDialog(ShoppingListPanel.this);

		            if (userSelection == JFileChooser.APPROVE_OPTION) {
		                
		                java.io.File fileToSave = fileChooser.getSelectedFile();

		                
		                String filePath = fileToSave.getAbsolutePath();
		                if (!filePath.toLowerCase().endsWith(".pdf")) {
		                    filePath += ".pdf";
		                    fileToSave = new java.io.File(filePath);
		                }

		             // Création du PDF à partir du texte de la liste de courses
		                createPdfFromShoppingList(getShoppingText(), fileToSave.getAbsolutePath());

		                JOptionPane.showMessageDialog(ShoppingListPanel.this, "PDF saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		
		ImageIcon trashIcon = new ImageIcon("./assets/trash_icone.png"); 
		Image trashImage = trashIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedTrashIcon = new ImageIcon(trashImage);
		trashButton = new JButton(resizedTrashIcon);
		trashButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		trashButton.setBounds(973, 20, 50, 50);
		trashButton.setName("trashButton");
		trashButton.setBackground(Color.white);
		add(trashButton);  
		trashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	resetShoppingList();
            }
        });
		
		recInfoPanel = new JPanel();
        recInfoPanel.setBounds(90, 90, 975, 545);
        recInfoPanel.setVisible(false);
        recInfoPanel.setName("recInfoPanel");
        add(recInfoPanel);
        showRecetteList();
    }
 // Méthode pour afficher la liste de recettes
    public static void showRecetteList() {
    	listPanel.removeAll();
	    ArrayList<RecetteDTO> recetteList = RecetteService.getInProgessRecettesForUser(UserIdConnection.getUserId());
	    for (RecetteDTO recette : recetteList) {
	    	RecCardShop recCardPanel = new RecCardShop(recette,listPanel,recInfoPanel);
	    	listPanel.add(recCardPanel);  
	    }
	    listPanel.revalidate();
	    listPanel.repaint();
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
	public JButton getlogOutButton() {
		return logOut;
	}
	public JButton getShoppingListButton() {
		return shoppingList;
	}
	public static JPanel getListPanel() {
		return listPanel;
	}
	public static JTextArea getTextArea() {
		return liste;
	}
	public JButton getFavListButton() {
		return favButton;
	}
	public static StringBuilder getStringBuilder() {
		return detailsText;
	}
	public static void  resetText() {
		detailsText=new StringBuilder();
	}
	public static void resetShoppingList() {
		resetText();
        JTextArea detailTextArea=getTextArea();
		StringBuilder text=getStringBuilder();
		detailTextArea.setText(text.toString());
    }
	public static void buildShoppingList(RecetteDTO recette,List<IngredientForRecette> missedIngredients, JTextArea detailsTextArea, StringBuilder detailsText) {
		detailsText.append(recette.getTitle()+"\n");
		for (IngredientForRecette ing : missedIngredients) {
        	String name=ing.getName();
        	Double amount=ing.getAmount();
        	String unit=ing.getUnit();
        	detailsText.append("- "+name+" "+amount+" "+unit+"\n");
        }
        detailsTextArea.setText(detailsText.toString());
    }
	
	private static String getShoppingText() {
        return liste.getText();
    }
	 // Méthode pour créer un texte de liste de courses au format PDF
	 private static void createPdfFromShoppingList(String text, String filePath) throws IOException {
	        PDDocument document = new PDDocument();
	        PDPage page = new PDPage();
	        document.addPage(page);
	        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
	            float margin = 20;
	            float yStart = page.getMediaBox().getHeight() - margin;
	            float yPosition = yStart;
	            float lineHeight = 14;  // Adjust this value based on your font size and line spacing
	            contentStream.beginText();
	            contentStream.newLineAtOffset(margin, yPosition);
	            String[] lines = text.split("\n");
	            for (String line : lines) {
	                if (yPosition - lineHeight < page.getMediaBox().getLowerLeftY() + margin) {
	                    // Start a new page if the current page is full
	                    contentStream.endText();
	                    contentStream.close();
	                    page = new PDPage();
	                    document.addPage(page);
	                    contentStream.moveTo(margin, yStart);
	                    yPosition = yStart;
	                    contentStream.beginText();
	                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
	                }
	                contentStream.showText(line);
	                contentStream.newLineAtOffset(0, -lineHeight);
	                yPosition -= lineHeight;
	            }
	            contentStream.endText();
        		resetShoppingList();
	        }
	        document.save(filePath);
	        document.close();
	    }
}
