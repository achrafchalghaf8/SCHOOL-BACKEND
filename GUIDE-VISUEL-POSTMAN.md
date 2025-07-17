# Guide visuel pour tester l'authentification avec Postman

Ce guide vous montre étape par étape comment configurer et utiliser Postman pour tester l'API d'authentification de l'application Ecole.

## 1. Importer la collection Postman

1. Ouvrez Postman
2. Cliquez sur le bouton "Import" en haut à gauche

   ![Import Button](https://i.imgur.com/JqmLXX6.png)

3. Faites glisser le fichier `Ecole_API_Postman_Collection.json` ou cliquez sur "Upload Files" et sélectionnez-le

   ![Import Dialog](https://i.imgur.com/8JYpZZs.png)

4. Cliquez sur "Import" pour confirmer

## 2. Configurer l'environnement

1. Cliquez sur l'icône d'engrenage (⚙️) en haut à droite

   ![Environment Button](https://i.imgur.com/LmPMcnY.png)

2. Cliquez sur "Add" pour créer un nouvel environnement

   ![Add Environment](https://i.imgur.com/Y5Nq8Wd.png)

3. Nommez-le "Ecole API"
4. Ajoutez les variables suivantes :
   - `base_url` : `http://localhost:8004`
   - `email` : `test@example.com`
   - `password` : `password123`

   ![Environment Variables](https://i.imgur.com/QJGvJQZ.png)

5. Cliquez sur "Save"
6. Sélectionnez l'environnement "Ecole API" dans le menu déroulant en haut à droite

   ![Select Environment](https://i.imgur.com/LmPMcnY.png)

## 3. Tester l'inscription (Register)

1. Dans la collection "Ecole API", ouvrez le dossier "Authentication"
2. Cliquez sur la requête "Register"

   ![Register Request](https://i.imgur.com/8JYpZZs.png)

3. Vérifiez que la méthode est bien **POST** et que l'URL est `{{base_url}}/api/auth/register`

   ![Register URL](https://i.imgur.com/JqmLXX6.png)

4. Allez dans l'onglet "Body", sélectionnez "raw" et "JSON"
5. Vérifiez que le corps de la requête contient :

   ```json
   {
       "email": "{{email}}",
       "nom": "Test User",
       "password": "{{password}}",
       "role": "ADMIN"
   }
   ```

   ![Register Body](https://i.imgur.com/QJGvJQZ.png)

6. Cliquez sur "Send" pour envoyer la requête
7. Vérifiez la réponse dans le panneau inférieur

   ![Register Response](https://i.imgur.com/Y5Nq8Wd.png)

## 4. Tester la connexion (Login)

1. Dans la collection "Ecole API", ouvrez le dossier "Authentication"
2. Cliquez sur la requête "Login"

   ![Login Request](https://i.imgur.com/8JYpZZs.png)

3. Vérifiez que la méthode est bien **POST** et que l'URL est `{{base_url}}/api/auth/login`

   ![Login URL](https://i.imgur.com/JqmLXX6.png)

4. Allez dans l'onglet "Body", sélectionnez "raw" et "JSON"
5. Vérifiez que le corps de la requête contient :

   ```json
   {
       "email": "{{email}}",
       "password": "{{password}}"
   }
   ```

   ![Login Body](https://i.imgur.com/QJGvJQZ.png)

6. Cliquez sur "Send" pour envoyer la requête
7. Vérifiez la réponse dans le panneau inférieur

   ![Login Response](https://i.imgur.com/Y5Nq8Wd.png)

## 5. Tester la connexion avec des identifiants incorrects

1. Dans la collection "Ecole API", ouvrez le dossier "Authentication"
2. Cliquez sur la requête "Login (Invalid Credentials)"

   ![Invalid Login Request](https://i.imgur.com/8JYpZZs.png)

3. Vérifiez que la méthode est bien **POST** et que l'URL est `{{base_url}}/api/auth/login`

   ![Invalid Login URL](https://i.imgur.com/JqmLXX6.png)

4. Allez dans l'onglet "Body", sélectionnez "raw" et "JSON"
5. Vérifiez que le corps de la requête contient :

   ```json
   {
       "email": "{{email}}",
       "password": "wrong_password"
   }
   ```

   ![Invalid Login Body](https://i.imgur.com/QJGvJQZ.png)

6. Cliquez sur "Send" pour envoyer la requête
7. Vérifiez la réponse dans le panneau inférieur - vous devriez obtenir une erreur 401 Unauthorized

   ![Invalid Login Response](https://i.imgur.com/Y5Nq8Wd.png)

## 6. Ajouter des tests automatisés

1. Ouvrez la requête "Login"
2. Allez dans l'onglet "Tests"

   ![Tests Tab](https://i.imgur.com/JqmLXX6.png)

3. Ajoutez le script de test suivant :

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

   ![Tests Script](https://i.imgur.com/QJGvJQZ.png)

4. Cliquez sur "Send" pour envoyer la requête
5. Vérifiez les résultats des tests dans l'onglet "Test Results"

   ![Test Results](https://i.imgur.com/Y5Nq8Wd.png)

## 7. Exécuter toute la collection

1. Cliquez avec le bouton droit sur la collection "Ecole API"
2. Sélectionnez "Run collection"

   ![Run Collection](https://i.imgur.com/JqmLXX6.png)

3. Dans la fenêtre "Collection Runner", cliquez sur "Start Run"

   ![Collection Runner](https://i.imgur.com/8JYpZZs.png)

4. Vérifiez les résultats de l'exécution

   ![Run Results](https://i.imgur.com/Y5Nq8Wd.png)

## Conclusion

Vous avez maintenant configuré Postman pour tester l'API d'authentification de l'application Ecole. Vous pouvez utiliser ces requêtes pour vérifier que l'inscription et la connexion fonctionnent correctement.

## Ressources supplémentaires

- [Documentation officielle de Postman](https://learning.postman.com/docs/getting-started/introduction/)
- [Guide des tests Postman](https://learning.postman.com/docs/writing-scripts/test-scripts/)
- [Variables d'environnement Postman](https://learning.postman.com/docs/sending-requests/variables/)