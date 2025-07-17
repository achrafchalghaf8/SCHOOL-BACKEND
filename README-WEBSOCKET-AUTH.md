# Système d'Authentification WebSocket

Ce document explique comment utiliser le système d'authentification WebSocket implémenté dans l'application.

## Aperçu

Ce système d'authentification WebSocket permet :

- L'inscription (création de compte)
- L'authentification (connexion)
- La protection des endpoints WebSocket

Le système n'utilise pas JWT ni Spring Security pour l'authentification, mais s'appuie sur les sessions WebSocket et un intercepteur personnalisé.

## Architecture

### Composants principaux

1. **WebSocketAuthInterceptor** : Intercepte les messages WebSocket pour vérifier l'authentification
2. **WebSocketConfig** : Configure les endpoints WebSocket et ajoute l'intercepteur
3. **WebSocketAuthController** : Gère les endpoints d'inscription et d'authentification
4. **WebSocketEventListener** : Gère les événements de connexion et déconnexion

## Endpoints WebSocket

### Endpoints publics (accessibles sans authentification)

- `/app/auth/register` : Pour créer un nouveau compte
- `/app/auth/login` : Pour s'authentifier

### Endpoints protégés

Tous les autres endpoints WebSocket nécessitent une authentification préalable.

## Utilisation côté client

### Connexion au serveur WebSocket

```javascript
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);
stompClient.connect({}, onConnected, onError);
```

### Inscription (création de compte)

```javascript
const registerRequest = {
    email: "utilisateur@example.com",
    nom: "Nom Utilisateur",
    password: "motdepasse",
    role: "ADMIN" // ou "PARENT", "ENSEIGNANT"
};

stompClient.send('/app/auth/register', {}, JSON.stringify(registerRequest));
```

### Authentification (connexion)

```javascript
const loginRequest = {
    email: "utilisateur@example.com",
    password: "motdepasse"
};

stompClient.send('/app/auth/login', {}, JSON.stringify(loginRequest));
```

### Réception des réponses d'authentification

```javascript
stompClient.subscribe('/user/queue/auth', function(response) {
    const authResponse = JSON.parse(response.body);
    // Traiter la réponse
});
```

## Exemple complet

Un exemple complet d'utilisation est disponible dans les fichiers :

- `src/main/resources/static/auth-example.html` : Page HTML d'exemple
- `src/main/resources/static/js/websocket-auth.js` : Client JavaScript

Pour tester l'exemple, lancez l'application et accédez à `http://localhost:8004/auth-example.html`.

## Sécurité

- Les mots de passe sont hachés avec BCrypt avant d'être stockés en base de données
- Les sessions WebSocket sont suivies pour garantir l'authentification
- Les endpoints publics sont explicitement définis, tous les autres nécessitent une authentification

## Personnalisation

Pour ajouter de nouveaux endpoints protégés, créez simplement de nouveaux contrôleurs avec des méthodes annotées `@MessageMapping`. L'intercepteur vérifiera automatiquement l'authentification.

Pour ajouter des endpoints publics, modifiez la méthode `preSend` dans `WebSocketAuthInterceptor` pour ajouter les nouveaux chemins à la liste des exceptions.