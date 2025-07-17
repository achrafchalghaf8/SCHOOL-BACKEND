-- Script pour créer et initialiser la base de données

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS ecole;

-- Utiliser la base de données
USE ecole;

-- Vérifier si la table compte existe, sinon la créer
CREATE TABLE IF NOT EXISTS compte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nom VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Vérifier si la table notification existe, sinon la créer
CREATE TABLE IF NOT EXISTS notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    contenu TEXT NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Vérifier si la table compte_notifications existe, sinon la créer
CREATE TABLE IF NOT EXISTS compte_notifications (
    compte_id BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    PRIMARY KEY (compte_id, notification_id),
    FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE,
    FOREIGN KEY (notification_id) REFERENCES notification(id) ON DELETE CASCADE
);

-- Vérifier si la table admin existe, sinon la créer
CREATE TABLE IF NOT EXISTS admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    compte_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE
);

-- Vérifier si la table parent existe, sinon la créer
CREATE TABLE IF NOT EXISTS parent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    compte_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE
);

-- Vérifier si la table enseignant existe, sinon la créer
CREATE TABLE IF NOT EXISTS enseignant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    compte_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE
);

-- Afficher les tables créées
SHOW TABLES;