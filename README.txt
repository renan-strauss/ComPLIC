Fonctionnalités:
	* Commentaires sur une ( // ) ou plusieurs ( /* ... */ ) lignes
	* Déclaration de variables (de type entier ou booleen)
	* Affectation (valeur, variable, ou expression) =>
		- verification de la compatibilite des types
	* Expressions =>
		- multiplication, addition, division, difference entre entiers
		- operations et/ou entre booleens ou expressions booleennes
		- comparaisons (=, <, >, <=, >=, !=) entre entiers
	* Instructions =>
		- programme <nomProg> { ... }
		- ecrire <idf> : affiche la valeur en memoire de <idf> avec un retour charriot
		- si ( <cond> ) alors { ... } sinon { ... } finsi ;
			* avec <cond> expression booleenne

Le fichier Test0.plic reprend toutes ces fonctionnalités.
Le dossier TestsPlic0/ contient les tests ecrits pour la version 0 de PLIC.

Avec mars.jar dans le repertoire, on peut dire au compilateur de lancer directement le programme MIPS avec Mars:

	java plic.Main -exec FichierSource.plic

Sinon, pour generer out.mips:

	java plic.Main FichierSource.plic