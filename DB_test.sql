create database spoonacular;
use spoonacular;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    intolerances VARCHAR(255) NOT NULL
);

INSERT INTO users(username,password,intolerances) VALUES ('adnane','1234','000000000000');

-- Create PossibleUnits Table
CREATE TABLE units (
    unit_id INT PRIMARY KEY,
    unitName VARCHAR(255)
);
INSERT INTO Units (unit_id, unitName) VALUES
(1, 'Liter'),
(2, 'Kilogram'),
(3, 'Unit');
-- Table des ingrédients
CREATE TABLE ingredients (
    ingredient_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(100)
);

-- Table de liaison entre les utilisateurs et les ingrédients dans leur frigo
CREATE TABLE user_ingredients (
    user_id INT,
    ingredient_id INT,
    amount Long,
    unit_id INT,
    peremption_date date,
    PRIMARY KEY (user_id, ingredient_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
);

-- Create Recette Table
CREATE TABLE recettes (
    recette_id INT PRIMARY KEY,
    title VARCHAR(255),
    image VARCHAR(255)
);

CREATE TABLE user_recettes(
	user_id INT,
    recette_id INT,
    favorite BOOLEAN,
    PRIMARY KEY (user_id, recette_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recette_id) REFERENCES recettes(recette_id)
);
-- Table Intolerances 
CREATE TABLE Intolerances (
    intolerance_id INT PRIMARY KEY,
    Dairy BOOLEAN,
    Egg BOOLEAN,
    Gluten BOOLEAN,
    Grain BOOLEAN,
    Peanut BOOLEAN,
    Seafood BOOLEAN,
    Sesame BOOLEAN,
    Shellfish BOOLEAN,
    Soy BOOLEAN,
    Sulfite BOOLEAN,
    Tree_Nut BOOLEAN,
    Wheat BOOLEAN
);


#Débuts TESTS
INSERT INTO ingredients (ingredient_id, name, image) values 
    (1, "chicken", "chicken.png"),
    (2, "egg", "egg.png"),
    (3, "milk", "milk.png")
;

#Reset TESTS
SET SQL_SAFE_UPDATES = 0;
delete from ingredients;