PasserelleFTP
=============

Auteurs
-------

Thomas Durieux

Introduction
------------

Ce projet offre la possibilité d’interagir avec un serveur FTP grâce à une interface REST. Le projet offre également un client léger réalisé avec AngularJS. Ce client permet de télécharger les fichiers, supprimer des fichiers, lister les ressources, d'envoyer des fichiers et de créer des répertoires.

L'authentification au serveur FTP utilise la norme 'Basic Authentification' qui est implémentée par la plupart des clients HTTP moderne.

REST API
--------

Les champs entre '[]' sont optionnels. Les champs en _italique_ sont des variables (ex: _filePath_ est le chemin absolu - à partir de la racine du serveur FTP- vers un fichier du serveur FTP).

HTTP Méthodes | URL                                 |Body           | Description
--------------|-------------------------------------|---------------|---------------------------------------------------------------------
GET           | /rest/_filePath_                     |               |Télécharge un fichier
DELETE        | /rest/_filePath_                     |               |Supprime un fichier
PUT           | /rest/_filePath_                     |               |Envoie un fichier
GET           | /rest/dir/_pathDir_/[html/json/xml]  |               |Récupère l'arborescence d'un répertoire au format: html, xml ou json. Le type produit peut également être précisé dans l'entête de la requête.
POST          | /rest/mkdir/_pathDir_                |               |Créer un répertoire
POST          | /rest/rmDir/_pathDir_                |               |Non implémenté
POST          | /rest/rename/_oldPathDir_            |Nouveau chemin |Non implémenté

Packages
--------


Nom                     | Description
------------------------|-------------------------------------------------------------------------------------------------------------------
config                  | Ce package contient les fichiers de configuration de l'application et les classes permettant de les lire. Les différentes configurations sont expliquées dans la section configuration.
exception               | Ce package contient les exceptions spécifique au domaine de l'application. Les différentes exceptions sont expliquées dans le section exception.
passerelleFTP           | Ce package contient le business de l'application.
passerelleFTP.resource  | Ce package contient les différentes représentation des ressources accessibles via FTP (répertoire et fichier).

Interfaces
----------
- config.PasserelleFTPConfiguration

Interface contenant un singleton qui permet d'accéder aux différentes options de la passerelle FTP. Cette interface hérite de l'interface config.PropertiesUtility.

- config.PropertiesUtility

Interface qui décrit les différentes méthodes disponibles qui permet d'accéder aux propriétés d'un fichier .ini.

- passerelleFTP.AuthenticationManager

Interface contenant un singleton, qui permet de récupérer un objet clientSession à partir de l'entête d'une requête.

- passerelleFTP.ClientSession

Interface permettant de gérer la session de connexion d'un client  à un serveur FTP. Elle permet de le connexion, déconnecter, récupérer son nom d'utilisateur, mot de passe et l'objet connecté aux serveur FTP (FTPClient).

- passerelleFTP.FTPCommand

Interface contenant un singleton, qui permet de communiquer avec un serveur FTP.

- passerelleFTP.PaserelleFTP

Interface décrivant les différentes actions disponibles à travers l'API REST.

- paserelleFTP.resource.Resource

Interface décrivant une ressource FTP (nom, droit, taille, date de modification, ...). Cette interface décrit aussi les méthodes de sérialisation JSON, XML et HTML.

Classes abstraites
------------------

- paserelleFTP.resource.ResourceImpl

Cette classe abstraite offre les éléments de base d'une ressources (nom, date de modification, droits, taille,...) cette classe permet également de sérialiser les ressources en JSON, XML et HTML grâce à un système d'introspection. Le système d'introspection sera détaillé dans la section 'Exemple de code'.

Exceptions
----------
Les différentes exceptions crées dans ce projet héritent de la classe RuntimeExcetpion.

- AuthenticationException

Exception qui est lancée de la contexte de l'authentification d'un utilisateur.
L'exception est lancée principalement dans deux cas: informations de connexion manquante ou lorsque les données de connexion sont incorrectes.
(Exception lancée dans passerelleFTP.AuthenticationManagerImpl aux lignes: 37, 60)

- ClientSessionException
Exception lancée par la classe passerelleFTP.ClientSessionImpl.

Exception qui sert principalement à encapsuler les exceptions FTPCommandException lancées par la classe passerelleFTP.FTPCommandImpl.

- FTPCommandException

Exception qui sert principalement à encapsuler les exceptions lancée lors de la communication avec le serveur FTP.

Configuration
-------------
Dans le fichier de configuration il est possible de définir l'emplacement du serveur FTP.

``` ini
ftpPort=2121
ftpHost=localhost
```

Annotations
-----------

Ce projet utilise plusieurs annotations misent à disposition par Jersey.

- @GET, @POST, @PUT, @DELETE:
Ces différentes annotations de méthodes sont utilisés dans la classe passerelleFTP.PaserelleFTPImpl pour décrire avec quelle type de méthode HTTP les ressources sont accédées.
- L'annotation @GET est utilisée pour récupérer les informations des ressources: télécharger une ressource ou lister un répertoire
- L'annotation @POST est utilisée par la création d'un dossier
- L'annotation @PUT est utilisée pour le téléversement d'une ressource
- L'annotation @DELETE est utilisée pour la suppression d'une ressource

- @Context

Cette annotation est utilisée dans la classe passerelleFTP.PaserelleFTPImpl pour récupérer l'entête de la requête. L'entête de la requête est utilisée pour la connexion de l'utilisateur et pour récupérer le content-type pour lister le contenu d'un répertoire.

- @Path

Cette annotation est utilisée dans la classe passerelleFTP.PaserelleFTPImpl elle permet de définir l'URL qui doit être utilisée pour accéder à un méthode.

- @PathParam

Récupérer une variable dans l'URL définie dans l'annotation @Path

- @Consume


- @Produce



Exemples de code
----------------

- Sérialisation des ressources par introspection

- Gestion des sessions des connexions au serveur FTP

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
