-- Script pour corriger la structure de la base de données
-- Exécutez ce script dans votre base de données MySQL

USE gestion_ecole_test;

-- Supprimer les tables existantes si elles existent
DROP TABLE IF EXISTS admins;

-- Modifier la table admins pour qu'elle ait un ID auto-généré et une référence vers compte
CREATE TABLE admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    compte_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE
);

-- Vérifier la structure des tables
DESCRIBE compte;
DESCRIBE admins;

-- Test d'insertion pour vérifier que tout fonctionne
-- INSERT INTO compte (email, nom, password, role) VALUES ('test@admin.com', 'Test Admin', 'password123', 'ADMIN');
-- INSERT INTO admins (compte_id) VALUES (LAST_INSERT_ID());
-- SELECT * FROM compte;
-- SELECT * FROM admins;

-- Exemple de requête pour voir les admins avec leurs comptes
-- SELECT a.id as admin_id, c.id as compte_id, c.email, c.nom, c.role
-- FROM admins a
-- JOIN compte c ON a.compte_id = c.id;
