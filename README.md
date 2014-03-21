PasserelleFTP
============

Rest API
--------

HTTP Method | URL                                 |Body           | Description
------------|-------------------------------------|---------------|---------------------------------------------------------------------
GET         | rest/_filePath_                     |               |Télécharge un fichier
DELETE      | rest/_filePath_                     |               |Supprime un fichier
PUT         | rest/_filePath_                     |               |Envoie un fichier
GET         | rest/dir/_pathDir_/(html|json|xml)  |               | Récupère l'arborescence d'un répertoire au format: html, xml ou json
POST        | rest/mkdir/_pathDir_                |               | Créer un répertoire
POST        | rest/rmDir/_pathDir_                |               | Non implémenté
POST        | rest/rename/_oldPathDir_            |Nouveau chemin | Non implémenté

fonctionnalités
---------
* connection (login/mdp) (DONE)
* lister les fichiers (DONE)
* télécharger les fichiers vers un emplacement à sélectionner
* uploader un fichier (formulaire web)
* supprimer un fichier (DONE)
* se déconnecter

autres critères
---------------
* être fonctionnel avec firefox
* proposer une documentation (en s'inspirant de googlecal API ou twitter API)
* tests unitaires (bibliothèque rest-assured?)
* gestion des exceptions
