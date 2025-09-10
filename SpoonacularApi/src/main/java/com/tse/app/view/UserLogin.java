package com.tse.app.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.tse.app.model.User;
import com.tse.app.service.LoginService;
import com.tse.app.util.TextFieldUtils;

public class UserLogin extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton createAccButton;
	
	public UserLogin(CardLayout cardLayout, JPanel cardContainer) {
        loginForm(cardLayout, cardContainer);
    }


	public void loginForm(CardLayout cardLayout, JPanel cardContainer) {
		setLayout(null);
		// Premier groupe (centré)
		JPanel firstGroup = new JPanel(null);
		JLabel lblLoginLabel = new JLabel("Login");
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
		/*
		 * JPanel fourthGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		 * loginButton = new JButton("Login"); loginButton.setFont(new Font("Tahoma",
		 * Font.PLAIN, 26)); fourthGroup.add(loginButton); add(fourthGroup);
		 * 
		 * JPanel fourthGroup1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		 * createAccButton = new JButton("Create Account"); createAccButton.setFont(new
		 * Font("Tahoma", Font.PLAIN, 26)); createAccButton.add(createAccButton);
		 * add(fourthGroup1);
		 */	
		JPanel fourthGroup = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		loginButton = new JButton("Login"); loginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		loginButton.setBackground(Color.white);
		fourthGroup.add(loginButton);
		fourthGroup.setBounds(80,400,1000,100);
		add(fourthGroup);
		
		JPanel fifthGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		createAccButton = new JButton("Create Account"); 
		createAccButton.setFont(new Font("Tahoma", Font.PLAIN, 26)); 
		createAccButton.setBackground(Color.white);
		fifthGroup.add(createAccButton);
		fifthGroup.setBounds(80,500,1000,100);
		add(fifthGroup);
		
		 createAccButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Passez au panneau de création de compte
	                cardLayout.show(cardContainer, "CreateAccount");
	            }
	        });
			
		// Ajout d'un gestionnaire d'événements pour le bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                User user = new User();
                user.setUsername(username);
                user.setPassword(new String(password));

                //boolean isAuthenticated = LoginService.authenticate(user);

                if (LoginService.authenticate(user)) {
                	cardLayout.show(cardContainer, "Ing_Rec");
                	Ing_RecPanel.showIngredientList();
                	Ing_RecPanel.showRecetteList();
                	IngPanel.showIngredientList();
                	RecPanel.showRecetteList();
                    //JOptionPane.showMessageDialog(UserLogin.this, "Authentification réussie!");
                } else {
                    JOptionPane.showMessageDialog(UserLogin.this, "Authentification échouée. Veuillez réessayer.", "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
                }

                // Effacer les champs de saisie après l'authentification
                usernameField.setText("");
                passwordField.setText("");
            }
        });
		 
	}
	
	public JButton getCreateAccButton() {
        return createAccButton;
    }
	
	public JButton getLoginButton() {
        return loginButton;
    }

}
