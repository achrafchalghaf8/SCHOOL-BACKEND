# Guide de test de l'API d'authentification avec Postman

## Introduction

Ce dépôt contient tous les fichiers nécessaires pour tester l'API d'authentification de l'application Ecole avec Postman. L'API d'authentification permet aux utilisateurs de s'inscrire et de se connecter à l'application.

## Fichiers inclus

1. **GUIDE-TEST-POSTMAN.md** - Guide détaillé pour tester l'API d'authentification avec Postman
2. **GUIDE-VISUEL-POSTMAN.md** - Guide visuel avec des captures d'écran pour configurer et utiliser Postman
3. **Ecole_API_Postman_Collection.json** - Collection Postman prête à l'emploi pour tester l'API
4. **test_api.bat** - Script batch pour tester rapidement l'API sans Postman
5. **README-TROUBLESHOOTING.md** - Guide de dépannage pour résoudre les problèmes courants

## Prérequis

1. **Postman** - Téléchargez et installez Postman depuis [postman.com](https://www.postman.com/downloads/)
2. **MySQL** - Assurez-vous que MySQL est installé et en cours d'exécution
3. **Application Ecole** - Assurez-vous que l'application Spring Boot est en cours d'exécution sur le port 8004

## Guide rapide

### Option 1 : Utiliser le script automatisé

1. Exécutez le script `test_api.bat`
2. Le script vérifiera si MySQL et l'application Spring Boot sont en cours d'exécution
3. Si l'application n'est pas en cours d'exécution, le script vous proposera de la démarrer
4. Le script testera ensuite les endpoints d'inscription et de connexion

### Option 2 : Utiliser Postman

1. Ouvrez Postman
2. Importez la collection `Ecole_API_Postman_Collection.json`
3. Configurez l'environnement comme décrit dans le guide visuel
4. Exécutez les requêtes d'inscription et de connexion

## Structure de l'API

### Endpoints

1. **POST /api/auth/register** - Inscription d'un nouvel utilisateur
   - Corps de la requête :
     ```json
     {
         "email": "test@example.com",
         "nom": "Test User",
         "password": "password123",
         "role": "ADMIN"
     }
     ```
   - Réponse :
     ```json
     {
         "id": 1,
         "email": "test@example.com",
         "nom": "Test User",
         "role": "ADMIN"
     }
     ```

2. **POST /api/auth/login** - Connexion d'un utilisateur existant
   - Corps de la requête :
     ```json
     {
         "email": "test@example.com",
         "password": "password123"
     }
     ```
   - Réponse :
     ```json
     {
         "status": "success",
         "message": "Authentification réussie",
         "id": 1,
         "email": "test@example.com",
         "nom": "Test User",
         "role": "ADMIN"
     }
     ```

## Valeurs possibles pour le champ "role"

Lors de l'inscription, le champ "role" peut prendre l'une des valeurs suivantes :

- `ADMIN` - Administrateur du système
- `PARENT` - Parent d'élève
- `ENSEIGNANT` - Enseignant

## Dépannage

Si vous rencontrez des problèmes lors des tests, consultez le fichier `README-TROUBLESHOOTING.md` pour des solutions aux problèmes courants.

## Ressources supplémentaires

- [Documentation officielle de Postman](https://learning.postman.com/docs/getting-started/introduction/)
- [Guide des tests Postman](https://learning.postman.com/docs/writing-scripts/test-scripts/)
- [Variables d'environnement Postman](https://learning.postman.com/docs/sending-requests/variables/)

## Conclusion

Ce guide vous a montré comment tester l'API d'authentification de l'application Ecole en utilisant Postman. Vous pouvez maintenant créer des comptes, vous connecter et vérifier que l'authentification fonctionne correctement.