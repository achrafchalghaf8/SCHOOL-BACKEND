# Guide de dépannage pour l'application Ecole

Ce document fournit des instructions pour résoudre les problèmes courants rencontrés lors de l'exécution de l'application.

## Problème : Erreur 404 lors des requêtes API

D'après les captures d'écran Postman, les requêtes vers `/api/auth/register` et `/api/auth/login` renvoient des erreurs 404 (Not Found). Voici les étapes pour résoudre ce problème :

### 1. Vérifier la base de données

1. Assurez-vous que MySQL est en cours d'exécution sur votre machine
2. Exécutez le script `create_database.sql` pour créer et initialiser la base de données :
   ```
   mysql -u root < create_database.sql
   ```

### 2. Vérifier la configuration de l'application

Assurez-vous que le fichier `application.properties` contient les bonnes informations de connexion à la base de données :

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ecole
spring.datasource.username=root
spring.datasource.password=
```

### 3. Redémarrer l'application avec le mode debug

Utilisez le script `run_app.bat` pour démarrer l'application en mode debug :

```
run_app.bat
```

Observez les logs pour identifier les erreurs potentielles.

### 4. Tester les API avec Postman

#### Pour l'inscription (Register) :

1. Créez une requête POST vers `http://localhost:8004/api/auth/register`
2. Dans l'onglet Body, sélectionnez "raw" et "JSON"
3. Ajoutez le corps de la requête :
```json
{
    "email": "test@example.com",
    "nom": "Test User",
    "password": "password123",
    "role": "ADMIN"
}
```
4. Envoyez la requête

#### Pour l'authentification (Login) :

1. Créez une requête POST vers `http://localhost:8004/api/auth/login`
2. Dans l'onglet Body, sélectionnez "raw" et "JSON"
3. Ajoutez le corps de la requête :
```json
{
    "email": "test@example.com",
    "password": "password123"
}
```
4. Envoyez la requête

## Problèmes courants et solutions

### Problème : CORS (Cross-Origin Resource Sharing)

Si vous rencontrez des erreurs CORS, vérifiez que :

1. La configuration CORS est correcte dans `CorsConfig.java`
2. La configuration de sécurité dans `SecurityConfig.java` permet les requêtes CORS

### Problème : Erreurs de base de données

Si vous rencontrez des erreurs liées à la base de données :

1. Vérifiez que MySQL est en cours d'exécution
2. Vérifiez que la base de données `ecole` existe
3. Vérifiez que les tables nécessaires existent
4. Vérifiez les informations de connexion dans `application.properties`

### Problème : Erreurs de compilation

Si vous rencontrez des erreurs de compilation :

1. Vérifiez que toutes les dépendances sont correctement configurées dans `pom.xml`
2. Exécutez `mvnw.cmd clean` pour nettoyer le projet
3. Exécutez `mvnw.cmd compile` pour compiler le projet

## Vérification de l'état de l'application

Pour vérifier que l'application fonctionne correctement :

1. Accédez à `http://localhost:8004/auth-example.html` dans votre navigateur
2. Essayez de vous inscrire et de vous connecter
3. Vérifiez les logs de l'application pour détecter d'éventuelles erreurs

## Contact

Si vous rencontrez toujours des problèmes après avoir suivi ces instructions, veuillez contacter l'équipe de développement.