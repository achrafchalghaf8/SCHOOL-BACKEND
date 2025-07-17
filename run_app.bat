@echo off
echo Démarrage de l'application Spring Boot...
echo.

cd %~dp0

echo Exécution de Maven pour construire et démarrer l'application...
echo.

call mvnw.cmd clean package spring-boot:run -Dspring-boot.run.arguments="--debug"

pause