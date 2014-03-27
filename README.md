PasserelleFTP
=============

Auteurs
-------

Thomas Durieux

Antoine Asseman

Introduction
------------

Ce projet offre la possibilité d’interagir avec un serveur FTP grâce à une
interface REST. Le projet offre également un client léger réalisé avec AngularJS.
Ce client permet de télécharger les fichiers, supprimer des fichiers, lister les
ressources, d'envoyer des fichiers et de créer des répertoires.

L'authentification au serveur FTP utilise la norme 'Basic Authentification' qui
est implémentée par la plupart des clients HTTP moderne.

REST API
--------

Les champs entre '[]' sont optionnels. Les champs en _italique_ sont des variables
(ex: _filePath_ est le chemin absolu - à partir de la racine du serveur FTP- vers un fichier du serveur FTP).

HTTP Méthodes | URL                                  |Body           | Description
--------------|--------------------------------------|---------------|---------------------------------------------------------------------
GET           | /rest/_filePath_                     |               |Télécharge un fichier
DELETE        | /rest/_filePath_                     |               |Supprime un fichier
PUT           | /rest/_filePath_                     |               |Envoie un fichier
GET           | /rest/dir/_pathDir_/[html/json/xml]  |               |Récupère l'arborescence d'un répertoire au format: html, xml ou json. Le type produit peut également être précisé dans l'entête de la requête.
POST          | /rest/mkdir/_pathDir_                |               |Créer un répertoire
DELETE        | /rest/rmDir/_pathDir_                |               |Supprime un répertoire vide
POST          | /rest/rename/_oldPathDir_            |Nouveau chemin |Non implémenté

Packages
--------


Nom                     | Description
------------------------|-------------------------------------------------------------------------------------------------------------------
config                  | Ce package contient les fichiers de configuration de l'application et les classes permettant de les lire. Les différentes configurations sont expliquées dans la section configuration.
exception               | Ce package contient les exceptions spécifiques au domaine de l'application. Les différentes exceptions sont expliquées dans la section 'Exception'.
passerelleFTP           | Ce package contient le business de l'application.
passerelleFTP.resource  | Ce package contient les différentes représentations des ressources accessibles via FTP (répertoire et fichier).

Interfaces
----------
#### config.PasserelleFTPConfiguration
Interface contenant un singleton qui permet d'accéder aux différentes options de
la passerelle FTP. Cette interface hérite de l'interface ```config.PropertiesUtility```.

#### config.PropertiesUtility
Interface qui décrit les différentes méthodes disponibles qui permet d'accéder
aux propriétés d'un fichier .ini.

#### passerelleFTP.AuthenticationManager
Interface contenant un singleton, qui permet de récupérer un objet clientSession
à partir de l'entête d'une requête.

#### passerelleFTP.ClientSession
Interface permettant de gérer la session de connexion d'un client  à un serveur FTP.
Elle permet de le connecter, déconnecter, récupérer son nom d'utilisateur,
mot de passe et l'objet connecté au serveur FTP (FTPClient).

#### passerelleFTP.FTPCommand
Interface contenant un singleton, qui permet de communiquer avec un serveur FTP.

#### passerelleFTP.PaserelleFTP
Interface décrivant les différentes actions disponibles à travers l'API REST.

#### passerelleFTP.resource.Resource
Interface décrivant une ressource FTP (nom, droit, taille, date de modification, ...).
Cette interface décrit aussi les méthodes de sérialisation JSON, XML et HTML.

Classes abstraites
------------------

#### paserelleFTP.resource.ResourceImpl
Cette classe abstraite offre les éléments de base d'une ressources (nom, date de modification, droits, taille,...)
Elle permet également de sérialiser les ressources en JSON, XML et HTML
grâce à un système d'introspection. Le système d'introspection sera détaillé
dans la section 'Exemple de code'.

Exceptions
----------
Les différentes exceptions créées dans ce projet héritent de la classe RuntimeExcetpion.

#### AuthenticationException
Exception qui est lancée dans le contexte de l'authentification d'un utilisateur.
L'exception est lancée principalement dans deux cas: informations de connexion
manquante ou lorsque les données de connexion sont incorrectes.
(Exception lancée dans ```passerelleFTP.AuthenticationManagerImpl``` aux lignes: 37, 60)

#### ClientSessionException
Exception lancée par la classe ```passerelleFTP.ClientSessionImpl```.

Exception qui sert principalement à encapsuler les exceptions FTPCommandException
lancées par la classe ```passerelleFTP.FTPCommandImpl```.

#### FTPCommandException
Exception qui sert principalement à encapsuler les exceptions lancées lors de la
communication avec le serveur FTP.

Configuration
-------------
Dans le fichier de configuration ```/config/ftp_config.ini```, il est possible de définir l'emplacement du serveur FTP.

``` ini
ftpPort=2121
ftpHost=localhost
```

Annotations
-----------

Ce projet utilise plusieurs annotations mises à disposition par Jersey.

#### _@GET_, _@POST_, _@PUT_, _@DELETE_
Ces différentes annotations de méthodes sont utilisées dans la classe
 ```passerelleFTP.PaserelleFTPImpl``` pour décrire avec quelle type de méthode
 HTTP les ressources sont accédées.

- L'annotation _@GET_ est utilisée pour récupérer les informations des ressources:
  télécharger une ressource ou lister un répertoire

- L'annotation _@POST_ est utilisée par la création d'un dossier

- L'annotation _@PUT_ est utilisée pour le téléversement d'une ressource

- L'annotation _@DELETE_ est utilisée pour la suppression d'une ressource

#### _@Context_
Cette annotation est utilisée dans la classe ```passerelleFTP.PaserelleFTPImpl```
pour récupérer l'entête de la requête.

#### _@Path_
Cette annotation est utilisée dans la classe ```passerelleFTP.PaserelleFTPImpl```
elle permet de définir l'URL qui doit être utilisée pour accéder à un méthode.

#### _@PathParam_
Récupérer une variable dans l'URL définie dans l'annotation @Path

#### _@Consumes_
Cette annotation permet de définir quels sont les types d’entête acceptés par la méthode,
par exemple de content-type.

#### _@Produces_
Cette annotation permet de définir l'entête de la réponse, en précisent
par exemple le type de ressource renvoyé.


Exemples de code
----------------

### Sérialisation des ressources par introspection
Classe: ```paserelleFTP/resource/ResourceImp``` (ligne: 174)

``` Java
public String toJson() {
  String json = "{";
  // récupère la liste des champs de la classe
  List<Field> fields = getAllFields();

  for (int i = 0, length = fields.size(); i < length; i++) {
    Field field = fields.get(i);
    try {
      // récupère la valeur du champ
      Object object = field.get(this);
      // sérialiser l'objet (supporte les iterables, les resources FTP -par appel récursif-, autrement on utilise le toString)
      String value = callSerializeMethodOnObject("toJson", object, ",");
      // format correctement le JSON
      json += "\"" + field.getName() + "\": " + value + "";
      if (i < length - 1) {
        json += ",";
      }
    } catch (Throwable ex) {
      // ignorer les champs qu'on ne peut pas sérialiser
      continue;
    }
  }
  return json + "}";
}
```
###Gestion des connexions lors d'une requête sur l'API REST

Classe: ```paserelleFTP/paserelleFTPImpl``` (ligne: 51)
``` Java
try {
  // essaye de connecter l'utilisateur sur base de l'entête de la requête
  session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
} catch (AuthenticationException e) {
  // la connexion a échouée, on informe l'utilisateur qu'il n'a pas les droits d'accéder à cette ressource
  Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
  // si la requête n'est pas une requête AJAX on ajoute l'entête qui permet au navigateur d'afficher un formulaire de connexion
  List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
  if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
    response = response.header("WWW-Authenticate", "Basic");
  }
  // on envoie la réponse à l'utilisateur
  return response.build();
}
```


Exécution
---------

Pour déployer ce projet, un serveur GlassFish est nécessaire. Il faut déployer le fichier ```TP_REST_Asseman_Durieux.war```
grâce à l'interface d'administration du serveur.

Un serveur FTP doit également être lancé, les informations de localisation (IP et port)
du serveur FTP peuvent être modifiées dans le fichier de configuration ```/config/ftp_config.ini```

Fonctionnalités
---------------
* connection (login/mdp)
* lister les fichiers
* télécharger les fichiers vers un emplacement à sélectionner
* uploader un fichier (formulaire web)
* supprimer un fichier
* se déconnecter

Autres critères
---------------
* fonctionnel avec firefox
* tests unitaires utilisant la bibliothèque rest-assured
* gestion des exceptions
