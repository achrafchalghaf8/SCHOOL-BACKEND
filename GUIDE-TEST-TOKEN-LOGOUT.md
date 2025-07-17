# Guide de Test - Authentification avec Token et Logout

Ce guide explique comment tester les nouvelles fonctionnalités d'authentification avec token et logout avec Postman.

## 🔐 Nouvelles Fonctionnalités Ajoutées

1. **Génération de token lors du login**
2. **Endpoint de logout pour invalider le token**
3. **Endpoint de validation de token**
4. **Endpoint pour récupérer les infos utilisateur authentifié**
5. **Toutes les APIs sont accessibles sans authentification (optionnelle)**

## 📋 Endpoints Disponibles

### 1. Login avec Token
- **URL**: `POST http://localhost:8080/api/auth/login`
- **Headers**: `Content-Type: application/json`
- **Body**:
```json
{
    "email": "admin@ecole.com",
    "password": "admin123"
}
```

**Réponse attendue**:
```json
{
    "status": "success",
    "message": "Authentification réussie",
    "id": 1,
    "email": "admin@ecole.com",
    "nom": "Admin",
    "role": "ADMIN",
    "token": "AbCdEf123456...",
    "tokenExpiration": "2025-07-08 17:42:00"
}
```

### 2. Logout
- **URL**: `POST http://localhost:8080/api/auth/logout`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer YOUR_TOKEN_HERE`

**Réponse attendue**:
```json
{
    "status": "success",
    "message": "Déconnexion réussie"
}
```

### 3. Validation de Token
- **URL**: `GET http://localhost:8080/api/auth/validate`
- **Headers**: `Authorization: Bearer YOUR_TOKEN_HERE`

**Réponse attendue**:
```json
{
    "status": "success",
    "message": "Token valide",
    "userId": 1,
    "email": "admin@ecole.com",
    "role": "ADMIN",
    "expiration": "2025-07-08 17:42:00"
}
```

### 4. Informations Utilisateur Authentifié
- **URL**: `GET http://localhost:8080/api/auth/me`
- **Headers**: `Authorization: Bearer YOUR_TOKEN_HERE` (optionnel)

**Avec token valide**:
```json
{
    "status": "success",
    "message": "Utilisateur authentifié",
    "authenticated": true,
    "user": {
        "id": 1,
        "email": "admin@ecole.com",
        "role": "ADMIN"
    }
}
```

**Sans token**:
```json
{
    "status": "error",
    "message": "Token manquant - utilisateur non authentifié",
    "authenticated": false
}
```

### 5. Test d'Endpoint API (Accessible sans token)
- **URL**: `GET http://localhost:8080/api/comptes`
- **Headers**: Aucun header requis

**Note**: Toutes les APIs sont maintenant accessibles sans authentification. L'authentification par token est optionnelle.

## 🧪 Scénarios de Test

### Scénario 1: Login et récupération du token
1. Faire un POST sur `/api/auth/login` avec des identifiants valides
2. Vérifier que la réponse contient un token et une date d'expiration
3. Copier le token pour les tests suivants

### Scénario 2: Utilisation du token pour accéder aux endpoints protégés
1. Utiliser le token obtenu dans le header `Authorization: Bearer TOKEN`
2. Faire un GET sur `/api/comptes` ou tout autre endpoint protégé
3. Vérifier que l'accès est autorisé

### Scénario 3: Validation du token
1. Faire un GET sur `/api/auth/validate` avec le token
2. Vérifier que les informations utilisateur sont retournées

### Scénario 4: Logout
1. Faire un POST sur `/api/auth/logout` avec le token
2. Vérifier que la déconnexion est réussie
3. Essayer d'utiliser le même token sur `/api/auth/me`
4. Vérifier que le token est invalidé

### Scénario 5: Token expiré ou invalide
1. Utiliser un token inexistant ou modifié
2. Essayer d'accéder à `/api/auth/me` ou `/api/auth/validate`
3. Vérifier que le token est rejeté

### Scénario 6: Accès aux APIs sans authentification
1. Faire des requêtes GET/POST sur n'importe quel endpoint API sans token
2. Vérifier que l'accès est autorisé
3. L'authentification est maintenant optionnelle

## 🔧 Configuration Postman

### Variables d'environnement recommandées:
- `base_url`: `http://localhost:8080`
- `auth_token`: (à définir après le login)

### Collection de requêtes:

1. **Login**
   - Method: POST
   - URL: `{{base_url}}/api/auth/login`
   - Body: JSON avec email/password
   - Test script pour extraire le token:
   ```javascript
   if (pm.response.code === 200) {
       const response = pm.response.json();
       pm.environment.set("auth_token", response.token);
   }
   ```

2. **Logout**
   - Method: POST
   - URL: `{{base_url}}/api/auth/logout`
   - Headers: `Authorization: Bearer {{auth_token}}`

3. **Validate Token**
   - Method: GET
   - URL: `{{base_url}}/api/auth/validate`
   - Headers: `Authorization: Bearer {{auth_token}}`

4. **Test Informations Utilisateur**
   - Method: GET
   - URL: `{{base_url}}/api/auth/me`
   - Headers: `Authorization: Bearer {{auth_token}}`

5. **Test API Sans Authentification**
   - Method: GET
   - URL: `{{base_url}}/api/comptes`
   - Headers: Aucun (optionnel)

## ⚠️ Points Importants

1. **Format du token**: Toujours utiliser `Bearer TOKEN` dans le header Authorization
2. **Durée de vie**: Les tokens expirent après 24 heures
3. **Stockage**: Les tokens sont stockés en mémoire (redémarrage = perte des tokens)
4. **Sécurité**: Le système n'utilise pas JWT mais un système de tokens simples
5. **Authentification optionnelle**: Toutes les APIs sont accessibles sans token
6. **Endpoints d'authentification**: `/api/auth/*` pour gérer les tokens
7. **Protection désactivée**: L'intercepteur est désactivé pour permettre l'accès libre aux APIs

## 🚀 Démarrage de l'Application

```bash
mvn spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`
