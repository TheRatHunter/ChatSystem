# ChatSystem
A chat system with a GUI

Projet de Laurent CHASSERAT et Mathieu RAYNAUD

CE QUI FONCTIONNE : 
- Discussion en TCP avec un hôte distant (one to one)
- Récupération de la liste des utilisateurs connectés en UDP et gestion de plusieurs conversations en parallèle (one to many et one to one)
- Authentification avec récupération des autres utilisateurs connectés en UDP, vérification de l'unicité et envoi aux autres d'une notification de présence (one to many)
- Envoi d'une notification lors de la déconnection qui entraîne le retrait de la liste des utilisateurs
- Affichage graphique avec JavaFX

CE QUI NE FONCTIONNE PAS ENCORE :
- Enregistrement et récupération des conversations en local