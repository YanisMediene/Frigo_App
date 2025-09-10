package com.tse.app.model;

public class Measures {
	
	// Détails de mesure pour le système de mesure américain (US)
	private MeasureDetails us;
	
	// Détails de mesure pour le système métrique
    private MeasureDetails metric;
    
    // Getter pour les détails de mesure du système américain (US)
    public MeasureDetails getUs() {
        return us;
    }
    
    // Getter pour les détails de mesure du système métrique
    public MeasureDetails getMetric() {
        return metric;
    }
    
    // Classe statique imbriquée représentant les détails de mesure
    public static class MeasureDetails {
    	
    	// Quantité de mesure
        private double amount;
        
        // Unité de mesure courte
        private String unitShort;
        
        // Unité de mesure longue
        private String unitLong;
        
        // Getter pour la quantité de mesure
        public Double getAmount() {
            return amount;
        }
        
        // Getter pour l'unité de mesure courte
        public String getUnitShort() {
            return unitShort;
        }
        
        // Getter pour l'unité de mesure longue
        public String getUnitLong() {
            return unitLong;
        }
    }
}
