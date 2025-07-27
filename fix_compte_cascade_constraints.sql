-- Script pour ajouter les contraintes ON DELETE CASCADE pour la table compte

-- Supprimer les contraintes existantes si elles existent
ALTER TABLE admins DROP FOREIGN KEY IF EXISTS fk_admin_compte;
ALTER TABLE parents DROP FOREIGN KEY IF EXISTS fk_parent_compte;
ALTER TABLE enseignants DROP FOREIGN KEY IF EXISTS fk_enseignant_compte;
ALTER TABLE compte_notifications DROP FOREIGN KEY IF EXISTS fk_compte_notifications_compte;

-- Ajouter les nouvelles contraintes avec ON DELETE CASCADE
ALTER TABLE admins 
ADD CONSTRAINT fk_admin_compte 
FOREIGN KEY (id) REFERENCES compte(id) ON DELETE CASCADE;

ALTER TABLE parents 
ADD CONSTRAINT fk_parent_compte 
FOREIGN KEY (id) REFERENCES compte(id) ON DELETE CASCADE;

ALTER TABLE enseignants 
ADD CONSTRAINT fk_enseignant_compte 
FOREIGN KEY (id) REFERENCES compte(id) ON DELETE CASCADE;

ALTER TABLE compte_notifications 
ADD CONSTRAINT fk_compte_notifications_compte 
FOREIGN KEY (compte_id) REFERENCES compte(id) ON DELETE CASCADE;

-- Vérifier les contraintes
SHOW CREATE TABLE admins;
SHOW CREATE TABLE parents;
SHOW CREATE TABLE enseignants;
SHOW CREATE TABLE compte_notifications; 