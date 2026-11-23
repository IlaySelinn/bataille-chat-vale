# Crafter son projet POO : version Lite (1h30)

> *« Laissez le code plus propre que vous ne l'avez trouvé. »* La règle du boy scout
>
> L'an dernier, vous avez livré un projet Java orienté objet. Aujourd'hui, vous avez **1h30** pour le reprendre avec un regard de crafter : poser un diagnostic, écrire des tests propres, puis améliorer un morceau de code sans rien casser.

---

## Contexte

Vous repartez de **votre projet de POO de l'année précédente** (Java).

---

## Déroulé conseillé

| Temps       | Étape        | Ce que vous faites                                                                 |
|-------------|--------------|------------------------------------------------------------------------------------|
| 0:00 → 0:15 | Diagnostiquer | Tag `lite-start`, analyse SonarQube for IDE, choix de 3 code smells               |
| 0:15 → 0:55 | Tester       | Tests unitaires, test paramétré, test double, autour du code que vous allez améliorer |
| 0:55 → 1:15 | Améliorer    | Un refactoring d'un smell, test vert après chaque étape                            |
| 1:15 → 1:30 | Relire       | Relecture avec la checklist, `CRAFT-LITE.md`, push                                  |

### 1. Diagnostiquer

Avant de modifier une seule ligne :

- Créez un **tag git `lite-start`** sur l'état de départ et poussez-le.
- Lancez **SonarQube for IDE** sur le projet et relevez : le nombre d'issues, et la classe la plus complexe.
- Identifiez **3 code smells**, localisés (fichier + classe/méthode). Choisissez celui que vous allez traiter.

> Choisissez un smell dans une classe **métier** (calcul, règle, validation), pas dans l'interface graphique : c'est là que les tests unitaires ont le plus de valeur.

### 2. Tester

Concentrez-vous sur la classe que vous allez améliorer :

- Écrivez des tests unitaires qui décrivent son **comportement actuel** (cas nominaux et cas limites).
- Quand plusieurs tests ne diffèrent que par leurs données, transformez-les en **test paramétré**.
- Si la classe dépend d'un composant difficile à utiliser en test (fichier, base, horloge, aléatoire, saisie utilisateur), remplacez-le par un **test double** (Stub, Mock ou Fake). Si la dépendance est créée avec un `new` dans la classe, passez-la par le **constructeur** pour pouvoir la remplacer.

### 3. Améliorer

- Corrigez le smell choisi par un **refactoring** à comportement constant : Extract Method, Rename, Extract Class, Introduce Parameter Object, Replace Conditional with Polymorphism...
- Lancez les tests **après chaque étape** et commitez quand c'est vert.
- Si un test révèle un bug, **ne le corrigez pas en douce** : notez-le dans `CRAFT-LITE.md`.

### 4. Relire

Relisez vos tests avec cette checklist avant de pousser :

- [ ] Chaque test a un **nom** qui décrit le comportement attendu
- [ ] Chaque test suit **Arrange / Act / Assert** et vérifie **un seul** comportement
- [ ] Aucun test sans assertion, aucune logique (`if`, boucle) dans un test
- [ ] Les tests sont **indépendants** : ils passent dans n'importe quel ordre
- [ ] Les mocks ne vérifient que ce qui compte (pas de sur-spécification)

---

## Exigences minimales

| #  | Exigence                                                                                                         |
|----|------------------------------------------------------------------------------------------------------------------|
| E1 | Au moins **5 tests unitaires** pertinents (un comportement par test, nommage explicite, AAA)                     |
| E2 | Au moins **1 test paramétré** (`@ParameterizedTest`) avec au moins 3 jeux de données                              |
| E3 | Au moins **1 test utilisant un test double** (Stub, Mock ou Fake) pour isoler une dépendance                     |
| E4 | Au moins **1 refactoring** d'un smell identifié au diagnostic, **couvert par des tests écrits avant**            |
| E5 | Un fichier **`CRAFT-LITE.md`** à la racine du dépôt (voir ci-dessous)                                            |

Un test sans assertion, ou qui ne vérifie rien de réel, **ne compte pas** dans les quotas.

---

## Le fichier `CRAFT-LITE.md`

Court, à remplir dans le dernier quart d'heure. Il doit permettre au correcteur de retrouver votre travail sans lire tout le code.

```markdown
# CRAFT LITE : [Nom du projet]

## 1. Lancement
Nom, prénom. Commande exacte pour lancer les tests.

## 2. Diagnostic (tag `lite-start`)
Nombre d'issues SonarQube for IDE, classe la plus complexe.

| # | Smell | Localisation | Pourquoi c'est un problème |
|---|-------|--------------|----------------------------|

Smell choisi et pourquoi.

## 3. Tests écrits
Classe testée, liste des tests (noms), test paramétré, test double utilisé et pour quelle dépendance.

## 4. Refactoring réalisé
Technique utilisée, tests qui le sécurisent, extrait avant / après, commits concernés.

## 5. Ce qui reste à faire
Smells non traités, bugs découverts, ce que vous feriez avec une heure de plus.
```

> Le `CRAFT-LITE.md` est confronté au code et à l'historique git. Un aveu honnête (« nous n'avons pas eu le temps d'écrire le test double ») vaut mieux qu'une affirmation fausse.

---

## Pratiques git

- **Conventional Commits** : `type(scope): description`.
- **Baby steps** : un commit = une idée, tests verts à chaque commit.
- **Tester avant de modifier** : dans l'historique, les commits `test:` précèdent le commit `refactor:` correspondant.

**Exemple de séquence attendue :**
```
test(order): describe total computation for regular customers
test(order): cover discount thresholds with a parameterized test
test(order): stub the price catalog to isolate OrderService
refactor(order): extract discount calculation into a dedicated method
refactor(order): rename variables to reveal discount intent
docs: add CRAFT-LITE.md
```

---

## Barème : /100 points

### 1. Diagnostic _(15 pts)_

| Critère                                                                     | Pts |
|-----------------------------------------------------------------------------|-----|
| Tag `lite-start` présent, métriques de départ relevées avec SonarQube for IDE | 5   |
| 3 code smells identifiés, localisés et justifiés                            | 10  |

### 2. Tests automatisés _(45 pts)_

| Critère                                                                                                     | Pts |
|-------------------------------------------------------------------------------------------------------------|-----|
| E1 : ≥ 5 tests unitaires pertinents, cas nominaux **et** cas limites                                        | 15  |
| E2 : test paramétré avec une source de données adaptée (`@CsvSource`, `@MethodSource`...)                   | 8   |
| E3 : test double pertinent (bon type de double, pas de sur-spécification, dépendance injectée)              | 10  |
| Clean tests : nommage, AAA, un comportement par test, assertions AssertJ, aucune logique, tests indépendants | 12  |

### 3. Refactoring _(20 pts)_

| Critère                                                                        | Pts |
|--------------------------------------------------------------------------------|-----|
| E4 : refactoring réel du smell choisi, lisibilité ou conception améliorée      | 12  |
| Comportement préservé : les tests écrits avant passent toujours après           | 8   |

### 4. Pratiques git _(15 pts)_

| Critère                                                                                    | Pts |
|--------------------------------------------------------------------------------------------|-----|
| Tests avant refactoring dans l'historique, tests verts à chaque commit                     | 8   |
| Commits atomiques et Conventional Commits                                                  | 7   |

### 5. `CRAFT-LITE.md` _(5 pts)_

| Critère                                                        | Pts |
|----------------------------------------------------------------|-----|
| Complet, honnête et **cohérent avec le code et l'historique git** | 5   |

### Récapitulatif

| Catégorie                            | Pts      |
|--------------------------------------|----------|
| 1. Diagnostic                        | /15      |
| 2. Tests automatisés                 | /45      |
| 3. Refactoring                       | /20      |
| 4. Pratiques git                     | /15      |
| 5. `CRAFT-LITE.md`                   | /5       |
| **Total**                            | **/100** |

**Conversion :** score / 5 → note sur 20

---

## Bonus _(jusqu'à +2 pts sur /20)_

| Critère                                                                                           | Bonus |
|---------------------------------------------------------------------------------------------------|-------|
| Travail rendu en **Pull Request**, relue par un autre étudiant avec la checklist (commentaires visibles) | +1    |
| Un **second refactoring** couvert par des tests                                                  | +0,5  |
| Un **Test Data Builder** pour rendre les setups lisibles                                          | +0,5  |

> La note finale est plafonnée à 20/20.

---

## Critères éliminatoires

| Situation                                                                       | Impact                          |
|---------------------------------------------------------------------------------|---------------------------------|
| Pas de fichier `CRAFT-LITE.md`                                                  | Catégorie 5 → 0/5               |
| Les tests ne compilent pas ou échouent sur la branche principale                | Catégorie 2 plafonnée à 22/45   |
| Aucun refactoring du code de production (seuls des tests ont été ajoutés)       | Catégorie 3 → 0/20              |
| Refactoring commité **avant** les tests qui le sécurisent                       | E4 plafonné à 6/12              |
| Aucun commit après le tag `lite-start`                                          | Catégorie 4 → 0/15              |

---

## Livrables (à la fin des 1h30)

- [ ] Repo git (pas forcément sur github / en ligne)
- [ ] **`CRAFT-LITE.md`** à la racine
- [ ] `mvn test` (ou `./gradlew test`) passe sur la branche principale