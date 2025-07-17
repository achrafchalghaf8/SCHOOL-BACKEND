/**
 * Exemple de client JavaScript pour l'authentification WebSocket
 */

// Connexion au serveur WebSocket
let stompClient = null;
let connected = false;
let currentUser = null;

// Fonction pour se connecter au serveur WebSocket
function connectWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    
    // Connexion sans authentification initiale
    stompClient.connect({}, function(frame) {
        console.log('Connecté au serveur WebSocket: ' + frame);
        connected = true;
        
        // S'abonner au canal de réponse d'authentification
        stompClient.subscribe('/user/queue/auth', function(response) {
            const authResponse = JSON.parse(response.body);
            handleAuthResponse(authResponse);
        });
    }, function(error) {
        console.error('Erreur de connexion WebSocket:', error);
        connected = false;
    });
}

// Fonction pour s'inscrire (créer un compte)
function register(email, nom, password, role) {
    if (!connected) {
        console.error('Non connecté au serveur WebSocket');
        return;
    }
    
    const registerRequest = {
        email: email,
        nom: nom,
        password: password,
        role: role
    };
    
    stompClient.send('/app/auth/register', {}, JSON.stringify(registerRequest));
}

// Fonction pour se connecter (authentification)
function login(email, password) {
    if (!connected) {
        console.error('Non connecté au serveur WebSocket');
        return;
    }
    
    const loginRequest = {
        email: email,
        password: password
    };
    
    stompClient.send('/app/auth/login', {}, JSON.stringify(loginRequest));
}

// Fonction pour gérer la réponse d'authentification
function handleAuthResponse(response) {
    console.log('Réponse d\'authentification reçue:', response);
    
    if (response.status === 'success') {
        // Authentification réussie
        currentUser = {
            id: response.id,
            email: response.email,
            nom: response.nom,
            role: response.role
        };
        
        console.log('Utilisateur authentifié:', currentUser);
        
        // Déclencher un événement pour informer l'application
        const event = new CustomEvent('auth:success', { detail: currentUser });
        document.dispatchEvent(event);
    } else {
        // Échec de l'authentification
        console.error('Échec de l\'authentification:', response.message);
        
        // Déclencher un événement pour informer l'application
        const event = new CustomEvent('auth:error', { detail: response.message });
        document.dispatchEvent(event);
    }
}

// Fonction pour se déconnecter
function logout() {
    currentUser = null;
    
    // Déclencher un événement pour informer l'application
    const event = new CustomEvent('auth:logout');
    document.dispatchEvent(event);
}

// Fonction pour vérifier si l'utilisateur est authentifié
function isAuthenticated() {
    return currentUser !== null;
}

// Fonction pour obtenir l'utilisateur actuel
function getCurrentUser() {
    return currentUser;
}

// Initialiser la connexion WebSocket au chargement de la page
document.addEventListener('DOMContentLoaded', function() {
    connectWebSocket();
});