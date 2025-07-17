@echo off
echo ======================================================
echo = Test de l'API d'authentification avec Postman =
echo ======================================================
echo.

REM Vérifier si MySQL est en cours d'exécution
echo Vérification de MySQL...
netstat -an | findstr "3306" > nul
if %errorlevel% neq 0 (
    echo [ERREUR] MySQL ne semble pas être en cours d'exécution sur le port 3306.
    echo Veuillez démarrer MySQL avant de continuer.
    pause
    exit /b 1
) else (
    echo [OK] MySQL est en cours d'exécution.
)

REM Vérifier si l'application Spring Boot est en cours d'exécution
echo Vérification de l'application Spring Boot...
netstat -an | findstr "8004" > nul
if %errorlevel% neq 0 (
    echo [AVERTISSEMENT] L'application Spring Boot ne semble pas être en cours d'exécution sur le port 8004.
    echo Voulez-vous démarrer l'application maintenant ? (O/N)
    set /p choice=
    if /i "%choice%"=="O" (
        echo Démarrage de l'application Spring Boot...
        start cmd /c "run_app.bat"
        echo Attente du démarrage de l'application (15 secondes)...
        timeout /t 15 /nobreak > nul
    ) else (
        echo Veuillez démarrer l'application manuellement avant de tester l'API.
        pause
        exit /b 1
    )
) else (
    echo [OK] L'application Spring Boot est en cours d'exécution.
)

REM Tester l'API
echo.
echo Test de l'API d'authentification...
echo.

REM Test de l'endpoint /api/auth/register
echo Test de l'endpoint /api/auth/register...
curl -X POST -H "Content-Type: application/json" -d "{\"email\":\"test@example.com\",\"nom\":\"Test User\",\"password\":\"password123\",\"role\":\"ADMIN\"}" http://localhost:8004/api/auth/register
echo.
echo.

REM Test de l'endpoint /api/auth/login
echo Test de l'endpoint /api/auth/login...
curl -X POST -H "Content-Type: application/json" -d "{\"email\":\"test@example.com\",\"password\":\"password123\"}" http://localhost:8004/api/auth/login
echo.
echo.

echo ======================================================
echo = Tests terminés =
echo ======================================================
echo.
echo Pour tester l'API avec Postman :
echo 1. Ouvrez Postman
echo 2. Importez la collection Ecole_API_Postman_Collection.json
echo 3. Suivez les instructions dans le fichier GUIDE-TEST-POSTMAN.md
echo.

pause