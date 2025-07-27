-- Script pour ajouter les contraintes ON DELETE CASCADE pour la table parents

-- Supprimer les contraintes existantes si elles existent
ALTER TABLE etudiants DROP FOREIGN KEY IF EXISTS fk_etudiant_parent;

-- Ajouter les nouvelles contraintes avec ON DELETE CASCADE
ALTER TABLE etudiants 
ADD CONSTRAINT fk_etudiant_parent 
FOREIGN KEY (parent_id) REFERENCES parents(id) ON DELETE CASCADE;

-- Vérifier les contraintes
SHOW CREATE TABLE etudiants; 