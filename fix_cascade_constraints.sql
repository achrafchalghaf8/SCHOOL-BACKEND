-- Script pour ajouter les contraintes ON DELETE CASCADE pour la table classes

-- Supprimer les contraintes existantes si elles existent
ALTER TABLE etudiants DROP FOREIGN KEY IF EXISTS fk_etudiant_classe;
ALTER TABLE emplois DROP FOREIGN KEY IF EXISTS fk_emploi_classe;
ALTER TABLE enseignant_classe DROP FOREIGN KEY IF EXISTS fk_enseignant_classe_classe;
ALTER TABLE exercice_classes DROP FOREIGN KEY IF EXISTS fk_exercice_classes_classe;

-- Ajouter les nouvelles contraintes avec ON DELETE CASCADE
ALTER TABLE etudiants 
ADD CONSTRAINT fk_etudiant_classe 
FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE;

ALTER TABLE emplois 
ADD CONSTRAINT fk_emploi_classe 
FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE;

ALTER TABLE enseignant_classe 
ADD CONSTRAINT fk_enseignant_classe_classe 
FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE;

ALTER TABLE exercice_classes 
ADD CONSTRAINT fk_exercice_classes_classe 
FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE;

-- Vérifier les contraintes
SHOW CREATE TABLE etudiants;
SHOW CREATE TABLE emplois;
SHOW CREATE TABLE enseignant_classe;
SHOW CREATE TABLE exercice_classes; 