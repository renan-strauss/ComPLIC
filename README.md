##PLIC
```
    Copyright 2015 Renan Strauss

    PLIC is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PLIC is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PLIC.  If not, see <http://www.gnu.org/licenses/>

```

Le langage PLIC est un langage qui se veut très simple (cf fonctionnalités actuelles).
Ce compilateur transforme le code PLIC en code assembleur MIPS.
La version de base de ce compilateur a été réalisée à l'IUT Nancy-Charlemagne, en 2ème année de DUT Informatique, dans le module Compilation.

##Fonctionnalités

* Commentaires sur une ( // ) ou plusieurs ( /* ... */ ) lignes
* Déclaration de variables (de type entier ou booleen)
* Affectation (valeur, variable, ou expression)
	- verification de la compatibilite des types
* Expressions
	- multiplication, addition, division, difference entre entiers
	- operations et/ou entre booleens ou expressions booleennes
	- comparaisons (=, <, >, <=, >=, !=) entre entiers
* Instructions
	- programme nom { ... } OBLIGATOIRE
	- ecrire idf : affiche la valeur en memoire de idf avec un retour charriot
	- si ( condition ) alors { ... } sinon { ... }
	- tantque ( condition ) repeter { ... }
	- pour idf dans expression .. expression repeter { ... }

Le fichier Test0.plic reprend toutes ces fonctionnalités.
Les autres fichiers testent differentes fonctionnalites : tantque, pour, boucles imbriquees, etc.

Pour lancer le code MIPS généré, téléchargez [le simulateur MARS](http://courses.missouristate.edu/KenVollmar/MARS/).
Avec mars.jar dans le repertoire, on peut dire au compilateur de lancer directement le programme MIPS avec Mars:

```bash
java plic.Main -exec FichierSource.plic
```

Sinon, pour generer out.mips:

```bash
java plic.Main FichierSource.plic
```