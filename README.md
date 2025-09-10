# Frigo App 🥘

Une application Java de gestion intelligente de frigo qui utilise l'API Spoonacular pour proposer des recettes basées sur les ingrédients disponibles.

## 📋 Description

Frigo App est une application desktop développée en Java avec Swing qui permet aux utilisateurs de :
- Gérer leur inventaire d'ingrédients dans leur frigo virtuel
- Découvrir des recettes basées sur les ingrédients disponibles
- Gérer leurs intolérances alimentaires
- Créer et gérer des listes de courses
- Sauvegarder leurs recettes favorites

L'application utilise l'API Spoonacular pour récupérer une large base de données de recettes et d'informations nutritionnelles.

## ✨ Fonctionnalités principales

- **Gestion du frigo virtuel** : Ajout, modification et suppression d'ingrédients
- **Recherche de recettes** : Suggestions basées sur les ingrédients disponibles
- **Gestion des intolérances** : Filtrage des recettes selon les restrictions alimentaires
- **Recettes favorites** : Sauvegarde et organisation des recettes préférées
- **Liste de courses** : Génération automatique de listes d'achats au format PDF
- **Interface utilisateur intuitive** : Interface graphique Swing moderne et ergonomique
- **Base de données** : Stockage persistant des données utilisateurs avec MySQL

## 🛠️ Technologies utilisées

- **Langage** : Java 17
- **Interface graphique** : Swing
- **Base de données** : MySQL
- **API externe** : Spoonacular API
- **Build tool** : Maven
- **Libraries** :
  - MySQL Connector/J 8.0.23
  - Gson 2.10.1
  - Apache PDFBox 2.0.27
  - JUnit 4.12

## 📋 Prérequis

- Java 17 ou version ultérieure
- MySQL Server
- Maven 3.6+
- Clé API Spoonacular (gratuite sur [spoonacular.com](https://spoonacular.com/food-api))

## 🚀 Installation

1. **Cloner le repository**
   ```bash
   git clone https://github.com/YanisMediene/Frigo_App.git
   cd Frigo_App
   ```

2. **Configuration de la base de données**
   ```bash
   # Créer la base de données MySQL
   mysql -u root -p < DB.sql
   ```

3. **Configuration de l'API Spoonacular**
   - Obtenir une clé API sur [spoonacular.com](https://spoonacular.com/food-api)
   - Configurer la clé dans le fichier `ApiKey.java`

4. **Compilation et exécution**
   ```bash
   cd SpoonacularApi
   mvn compile
   mvn exec:java -Dexec.mainClass="com.tse.app.App"
   ```

## 📖 Utilisation

1. **Première utilisation**
   - Créer un compte utilisateur
   - Configurer vos intolérances alimentaires

2. **Gestion du frigo**
   - Ajouter vos ingrédients disponibles
   - Spécifier les quantités et unités

3. **Recherche de recettes**
   - L'application propose automatiquement des recettes
   - Filtrer selon vos préférences et intolérances

4. **Liste de courses**
   - Générer une liste d'achats au format PDF
   - Imprimer ou sauvegarder la liste

## 📁 Structure du projet

```
Frigo_App/
├── SpoonacularApi/
│   ├── src/main/java/com/tse/app/
│   │   ├── model/          # Classes métier (User, Ingredient, Recette, etc.)
│   │   ├── service/        # Services (API calls, business logic)
│   │   ├── view/           # Interface utilisateur Swing
│   │   ├── util/           # Utilitaires (DB connection, API key)
│   │   └── DTO/            # Data Transfer Objects
│   └── pom.xml
├── DB.sql                  # Script de création de la base de données
├── Documentation technique.docx
└── Documentation utilisateur.docx
```

## 🧪 Tests

```bash
cd SpoonacularApi
mvn test
```

## 📚 Documentation

- **Documentation technique** : Disponible dans `Documentation technique.docx`
- **Documentation utilisateur** : Disponible dans `Documentation utilisateur.docx`

## 👥 Équipe de développement

- **Anne Arthur**
- **Drew Alice** 
- **Fey Pauline**
- **Mediene Yanis**
- **Mickael Osorio**
- **Ziani Zakaria**
- **Ez-Zaim Adnane**

## 📄 Licence

Ce projet est sous licence [voir LICENSE](LICENSE) pour plus de détails.

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork the project
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📞 Support

Pour toute question ou problème, vous pouvez :
- Ouvrir une issue sur GitHub
- Consulter la documentation utilisateur
- Contacter l'équipe de développement

## 🔮 Roadmap

- [ ] Interface web responsive
- [ ] Application mobile
- [ ] Synchronisation multi-appareils
- [ ] Suggestions nutritionnelles avancées
- [ ] Intégration avec d'autres APIs alimentaires

---