# Guide de test d'authentification avec Postman

Ce guide vous explique comment tester les fonctionnalités d'authentification de l'application Ecole en utilisant Postman.

## Prérequis

1. Assurez-vous que l'application est en cours d'exécution (port 8004)
2. Assurez-vous que la base de données MySQL est en cours d'exécution
3. Postman est installé sur votre machine

## Configuration de l'environnement Postman

Pour faciliter les tests, créez un environnement Postman avec les variables suivantes :

1. Cliquez sur l'icône d'engrenage (⚙️) en haut à droite
2. Cliquez sur "Add" pour créer un nouvel environnement
3. Nommez-le "Ecole API"
4. Ajoutez les variables suivantes :
   - `base_url` : `http://localhost:8004`
   - `email` : `test@example.com`
   - `password` : `password123`
5. Cliquez sur "Save"
6. Sélectionnez l'environnement "Ecole API" dans le menu déroulant en haut à droite

## Test 1 : Inscription (Register)

### Création de la requête

1. Créez une nouvelle requête dans Postman
2. Configurez la requête comme suit :
   - Méthode : **POST**
   - URL : `{{base_url}}/api/auth/register`
   - Headers : 
     - Key: `Content-Type`, Value: `application/json`
   - Body (raw, JSON) :
   ```json
   {
       "email": "{{email}}",
       "nom": "Test User",
       "password": "{{password}}",
       "role": "ADMIN"
   }
   ```

### Exécution et vérification

1. Cliquez sur "Send" pour envoyer la requête
2. Vérifiez la réponse :
   - Code de statut : `200 OK`
   - Corps de la réponse : Un objet JSON contenant les informations du compte créé (sans le mot de passe)

```json
{
    "id": 1,
    "email": "test@example.com",
    "nom": "Test User",
    "role": "ADMIN"
}
```

## Test 2 : Connexion (Login)

### Création de la requête

1. Créez une nouvelle requête dans Postman
2. Configurez la requête comme suit :
   - Méthode : **POST**
   - URL : `{{base_url}}/api/auth/login`
   - Headers : 
     - Key: `Content-Type`, Value: `application/json`
   - Body (raw, JSON) :
   ```json
   {
       "email": "{{email}}",
       "password": "{{password}}"
   }
   ```

### Exécution et vérification

1. Cliquez sur "Send" pour envoyer la requête
2. Vérifiez la réponse :
   - Code de statut : `200 OK`
   - Corps de la réponse : Un objet JSON contenant les informations d'authentification

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

## Test 3 : Connexion avec des identifiants incorrects

### Création de la requête

1. Créez une nouvelle requête dans Postman
2. Configurez la requête comme suit :
   - Méthode : **POST**
   - URL : `{{base_url}}/api/auth/login`
   - Headers : 
     - Key: `Content-Type`, Value: `application/json`
   - Body (raw, JSON) :
   ```json
   {
       "email": "{{email}}",
       "password": "wrong_password"
   }
   ```

### Exécution et vérification

1. Cliquez sur "Send" pour envoyer la requête
2. Vérifiez la réponse :
   - Code de statut : `401 Unauthorized`
   - Corps de la réponse : "Email ou mot de passe incorrect"

## Création d'une collection Postman

Pour organiser vos tests, créez une collection Postman :

1. Cliquez sur "Collections" dans le panneau de gauche
2. Cliquez sur le bouton "+" pour créer une nouvelle collection
3. Nommez-la "Ecole API Tests"
4. Ajoutez un dossier "Authentication" à cette collection
5. Déplacez vos requêtes de test dans ce dossier

## Automatisation des tests

Vous pouvez automatiser les tests en utilisant les scripts de test Postman :

### Script pour le test de connexion réussie

Ajoutez ce script dans l'onglet "Tests" de votre requête de connexion :

```javascript
var jsonData = pm.response.json();

pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Authentication successful", function () {
    pm.expect(jsonData.status).to.eql("success");
    pm.expect(jsonData.message).to.eql("Authentification réussie");
});

pm.test("Response contains user information", function () {
    pm.expect(jsonData.id).to.exist;
    pm.expect(jsonData.email).to.exist;
    pm.expect(jsonData.nom).to.exist;
    pm.expect(jsonData.role).to.exist;
});

// Stocker l'ID utilisateur pour une utilisation ultérieure
pm.environment.set("user_id", jsonData.id);
```

## Dépannage

Si vous rencontrez des problèmes lors des tests :

1. **Erreur 404 (Not Found)** : Vérifiez que l'application est en cours d'exécution et que l'URL est correcte
2. **Erreur 500 (Internal Server Error)** : Vérifiez les logs de l'application pour identifier l'erreur
3. **Erreur CORS** : Vérifiez que la configuration CORS permet les requêtes depuis Postman

## Valeurs possibles pour le champ "role"

Lors de l'inscription, le champ "role" peut prendre l'une des valeurs suivantes :

- `ADMIN` : Administrateur du système
- `PARENT` : Parent d'élève
- `ENSEIGNANT` : Enseignant

## Conclusion

Ce guide vous a montré comment tester les fonctionnalités d'authentification de l'application Ecole en utilisant Postman. Vous pouvez maintenant créer des comptes, vous connecter et vérifier que l'authentification fonctionne correctement.