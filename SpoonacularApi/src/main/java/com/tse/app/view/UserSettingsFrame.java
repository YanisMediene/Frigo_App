package com.tse.app.view;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import com.tse.app.DTO.IngredientDTO;
import com.tse.app.model.User;
import com.tse.app.service.IntolerancesService;
import com.tse.app.util.UserIdConnection;
public class UserSettingsFrame extends JFrame{
	private static JCheckBox dairy;
    private static JCheckBox egg;
    private static JCheckBox gluten;
    private static JCheckBox grain;
    private static JCheckBox peanut;
    private static JCheckBox seafood;
    private static JCheckBox sesame;
    private static JCheckBox shellfish;
    private static JCheckBox soy;
    private static JCheckBox sulfite;
    private static JCheckBox treenut;
    private static JCheckBox wheat;
    private static JLabel title;
    private static JLabel intolerances;
    private static boolean[] userIntolerances; 
    private JButton save;
    
	public UserSettingsFrame() {
		setSize(300, 400);
        title= new JLabel("User Settings");
        intolerances= new JLabel("Select your intolerances :");
        dairy = new JCheckBox("Dairy");
        egg = new JCheckBox("Egg");
        gluten = new JCheckBox("Gluten");
        grain = new JCheckBox("Grain");
        peanut = new JCheckBox("Peanut");
        seafood = new JCheckBox("Seafood");
        sesame = new JCheckBox("Sesame");
        shellfish = new JCheckBox("Shellfish");
        soy = new JCheckBox("Soy");
        sulfite = new JCheckBox("Sulfite");
        treenut = new JCheckBox("Tree nut");
        wheat = new JCheckBox("Wheat");
        userIntolerances = new boolean[12];
        save = new JButton("Save");
        // layout manager null pour positionnement absolu
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        // positions de checkboxes et labels
        title.setBounds(100, 0, 100, 20);
        intolerances.setBounds(20,30,200,20);
        dairy.setBounds(20, 60, 100, 20);
        egg.setBounds(20, 80, 100, 20);
        gluten.setBounds(20, 100, 100, 20);
        grain.setBounds(20, 120, 100, 20);
        peanut.setBounds(20, 140, 100, 20);
        seafood.setBounds(20, 160, 100, 20);
        sesame.setBounds(20, 180, 100, 20);
        shellfish.setBounds(20, 200, 100, 20);
        soy.setBounds(20, 220, 100, 20);
        sulfite.setBounds(20, 240, 100, 20);
        treenut.setBounds(20, 260, 100, 20);
        wheat.setBounds(20, 280, 100, 20);
        save.setBounds(150, 300, 65, 20);
        // Ajouter checkboxes et labels à la frame
        contentPane.add(title);
        contentPane.add(intolerances);
        contentPane.add(dairy);
        contentPane.add(egg);
        contentPane.add(gluten);
        contentPane.add(grain);
        contentPane.add(peanut);
        contentPane.add(seafood);
        contentPane.add(sesame);
        contentPane.add(shellfish);
        contentPane.add(soy);
        contentPane.add(sulfite);
        contentPane.add(treenut);
        contentPane.add(wheat);
        contentPane.add(save);
        // Définir visibilité frame et si redimensionnable
		setVisible(true);        
		setResizable(false);
		
		dairy.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (dairy.isSelected()) {
	        		userIntolerances[0]=true;
                } else {
                	userIntolerances[0]=false;
                }
	        }
	    });
		
		egg.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (egg.isSelected()) {
	        		userIntolerances[1]=true;
                } else {
                	userIntolerances[1]=false;
                }
	        }
	    });
		
		gluten.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (gluten.isSelected()) {
	        		userIntolerances[2]=true;
                } else {
                	userIntolerances[2]=false;
                }
	        }
	    });
		
		grain.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (grain.isSelected()) {
	        		userIntolerances[3]=true;
                } else {
                	userIntolerances[3]=false;
                }
	        }
	    });
		
		peanut.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (peanut.isSelected()) {
	        		userIntolerances[4]=true;
                } else {
                	userIntolerances[4]=false;
                }
	        }
	    });
		
		seafood.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (seafood.isSelected()) {
	        		userIntolerances[5]=true;
                } else {
                	userIntolerances[5]=false;
                }
	        }
	    });
		
		sesame.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (sesame.isSelected()) {
	        		userIntolerances[6]=true;
                } else {
                	userIntolerances[6]=false;
                }
	        }
	    });
		
		shellfish.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (shellfish.isSelected()) {
	        		userIntolerances[7]=true;
                } else {
                	userIntolerances[7]=false;
                }
	        }
	    });
		
		soy.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (soy.isSelected()) {
	        		userIntolerances[8]=true;
                } else {
                	userIntolerances[8]=false;
                }
	        }
	    });
		
		sulfite.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (sulfite.isSelected()) {
	        		userIntolerances[9]=true;
                } else {
                	userIntolerances[9]=false;
                }
	        }
	    });
		
		treenut.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (treenut.isSelected()) {
	        		userIntolerances[10]=true;
                } else {
                	userIntolerances[10]=false;
                }
	        }
	    });
		
		wheat.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (wheat.isSelected()) {
	        		userIntolerances[11]=true;
                    
                } else {
                	userIntolerances[11]=false;
                }
	        }
	    });
		
		save.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Boolean itWorked=IntolerancesService.setIntolerances(intolerancesForSQL(),UserIdConnection.getUserId());
	        	if (itWorked) {
	        		JOptionPane.showMessageDialog(UserSettingsFrame.this,"Intolerances saved !");
	        	}
	        }
	    });
		
	}	
	
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		 SwingUtilities.invokeLater(() -> {
	            new UserSettingsFrame();
	        });
	}

	public static boolean[] getUserIntolerances() {
		return userIntolerances;
	}
	
	//Créer string représentant intolérances user pour base de données
	public static String intolerancesForSQL() {
		String intolerancesForSQL="";
		for (int i=0;i<getUserIntolerances().length;i++) {
			if (userIntolerances[i]==true)
				intolerancesForSQL+="1";
			else
				intolerancesForSQL+="0";
		}
		return intolerancesForSQL;
	}
	
	//Initialiser checkboxes et booléens
	public static void initialiseCheckboxes() {
		String intolerances=IntolerancesService.getIntolerances(UserIdConnection.getUserId());
		if (intolerances.charAt(0)=='1') {
			dairy.setSelected(true);
    		userIntolerances[0]=true;
		}
		if (intolerances.charAt(1)=='1') {
			egg.setSelected(true);
    		userIntolerances[1]=true;
		}
		if (intolerances.charAt(2)=='1') {
			gluten.setSelected(true);
    		userIntolerances[2]=true;
		}
		if (intolerances.charAt(3)=='1') {
			grain.setSelected(true);
    		userIntolerances[3]=true;
		}
		if (intolerances.charAt(4)=='1') {
			peanut.setSelected(true);
    		userIntolerances[4]=true;
		}
		if (intolerances.charAt(5)=='1') {
			seafood.setSelected(true);
    		userIntolerances[5]=true;
		}
		if (intolerances.charAt(6)=='1') {
			sesame.setSelected(true);
    		userIntolerances[6]=true;
		}
		if (intolerances.charAt(7)=='1') {
			shellfish.setSelected(true);
    		userIntolerances[7]=true;
		}
		if (intolerances.charAt(8)=='1') {
			soy.setSelected(true);
    		userIntolerances[8]=true;
		}
		if (intolerances.charAt(9)=='1') {
			sulfite.setSelected(true);
    		userIntolerances[9]=true;
		}
		if (intolerances.charAt(10)=='1') {
			treenut.setSelected(true);
    		userIntolerances[10]=true;
		}
		if (intolerances.charAt(11)=='1') {
			wheat.setSelected(true);
    		userIntolerances[11]=true;
		}
	}
	
	// Créer string qui permet d'ajouter des intolérances quand on cherche des recettes
	public static String getIntolerancesForRecipes() {
		ArrayList<String> liste = new ArrayList<>();
		liste.add("Dairy,");
		liste.add("Egg,");
		liste.add("Gluten,");
		liste.add("Grain,");
		liste.add("Peanut,");
		liste.add("Seafood,");
		liste.add("Sesame,");
		liste.add("Shellfish,");
		liste.add("Soy,");
		liste.add("Sulfite,");
		liste.add("Treenut,");
		liste.add("Wheat,");
		String intolerances=IntolerancesService.getIntolerances(UserIdConnection.getUserId());
		String intolerancesForRecipes="";
		for (int i=0;i<12;i++){
			if (intolerances.charAt(i)=='1') {
				intolerancesForRecipes+=liste.get(i);
			}
		}
		return intolerancesForRecipes;
	}
}
