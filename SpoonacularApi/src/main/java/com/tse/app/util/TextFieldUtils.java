package com.tse.app.util;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Classe utilitaire pour configurer des panneaux d'entrée de texte avec des étiquettes.
 */

public class TextFieldUtils {
	
	/**
     * Configure un panneau d'entrée de texte avec une étiquette.
     *
     * textField   Champ de texte à configurer.
     * labelName   Nom de l'étiquette associée au champ de texte.
     * return Un panneau configuré avec l'étiquette et le champ de texte.
     */
	
	 public static JPanel configureInputPanel(JTextField textField,String labelName) {
		 	JPanel panel = new JPanel(new FlowLayout());
		 	try {
			JLabel lblUsername = new JLabel(labelName);
			lblUsername.setBackground(Color.BLACK);
			lblUsername.setForeground(Color.BLACK);
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
			panel.add(lblUsername);
			
			textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
			textField.setColumns(10);
			panel.add(textField);
			panel.setBounds(80,200,1000,100);
		 	} catch (Exception e) {
	            e.printStackTrace();
	        }
			return panel;
	}
	 
	 /**
	     * Configure un panneau d'entrée de mot de passe avec une étiquette.
	     *
	     * textField   Champ de texte de mot de passe à configurer.
	     * labelName   Nom de l'étiquette associée au champ de texte de mot de passe.
	     * return Un panneau configuré avec l'étiquette et le champ de texte de mot de passe.
	     */
	 
	 public static JPanel configurePasswordPanel(JPasswordField textField,String labelName) {
		 	JPanel panel = new JPanel(new FlowLayout());
		 	try {
			JLabel lblUsername = new JLabel(labelName);
			lblUsername.setBackground(Color.BLACK);
			lblUsername.setForeground(Color.BLACK);
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
			panel.add(lblUsername);
			textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
			textField.setColumns(10);
			panel.add(textField);
			panel.setBounds(80,300,1000,100);
		 	} catch (Exception e) {
	            e.printStackTrace();
	        }
			return panel;
	}
}
