package com.tse.app.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.tse.app.model.User;
import com.tse.app.service.LoginService;
import com.tse.app.util.TextFieldUtils;

public class CreateUserAccount  extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;
    
    public CreateUserAccount(CardLayout cardLayout, JPanel cardPanel) {
        createAccountForm(cardLayout, cardPanel);
    }
    
    
	public void createAccountForm(CardLayout cardLayout, JPanel cardPanel){
		setLayout(null);
		// Premier groupe (centré)
		JPanel firstGroup = new JPanel(null);
		JLabel lblLoginLabel = new JLabel("Create account");
		lblLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginLabel.setForeground(Color.BLACK);
		lblLoginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
		lblLoginLabel.setBounds(0, 0, 1000, 100);
		firstGroup.setBounds(80,100,1000,100);
		firstGroup.add(lblLoginLabel);
		add(firstGroup);

		usernameField = new JTextField();
		JPanel secondGroup = TextFieldUtils.configureInputPanel(usernameField, "Username");
		add(secondGroup);

		// Troisième groupe (passwordField et lblPassword)
		passwordField = new JPasswordField();
		JPanel thirdGroup = TextFieldUtils.configurePasswordPanel(passwordField, "Password");
		add(thirdGroup);
		
		// Quatrième groupe (centré)
		JPanel fourthGroup = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		registerButton = new JButton("Register");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		registerButton.setBackground(Color.white);
		fourthGroup.add(registerButton);
		fourthGroup.setBounds(80,400,1000,100);
		add(fourthGroup);
		
		JPanel fifthGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        backButton.setBackground(Color.white);
		fifthGroup.add(backButton);
		fifthGroup.setBounds(80,500,1000,100);
		add(fifthGroup);
		
		
		
		
		 // Ajout d'un gestionnaire d'événements pour le bouton d'enregistrement
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Obtenir les valeurs des champs de saisie
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                // Vérifier que les champs ne sont pas vides
                if (username.isEmpty() || password.length == 0) {
                    JOptionPane.showMessageDialog(CreateUserAccount.this, "Veuillez remplir tous les champs.");
                    return; // Ne continuez pas si les champs sont vides
                }

                // Convertir le mot de passe en une chaîne
                String passwordString = new String(password);
                
                boolean alreadyExist=LoginService.checkUsername(username);
                if (!alreadyExist) {
                	User user = new User();
                    user.setUsername(username);
                    user.setPassword(new String(password));
                    
                    // Appeler le service de base de données pour enregistrer l'utilisateur
                    boolean registrationSuccessful = LoginService.registerUser(user);

                    if (registrationSuccessful) {
                        JOptionPane.showMessageDialog(CreateUserAccount.this, "Utilisateur enregistré avec succès!");
                        // Effacer les champs après l'enregistrement réussi
                        usernameField.setText("");
                        passwordField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(CreateUserAccount.this, "Échec de l'enregistrement. Veuillez réessayer.",
                                "Erreur d'enregistrement", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                	JOptionPane.showMessageDialog(CreateUserAccount.this, "Username already used.");
                    return; // Ne pas continuer si les champs sont vides
                }
                
            }
            
        });
        
        // Ajout d'un gestionnaire d'événements pour le bouton de retour
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Revenir à la page de connexion
                cardLayout.show(cardPanel, "login");
            }
        });
		 
	}
	 public JButton getBackButton() {
	        return backButton;
	    }
}

