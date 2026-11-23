# CRAFT LITE : Bataille Chat-Vale

## 1. Lancement
- **Binôme** : Luisa Couette & Ilayda Selin Koçak
- **Commande exacte pour lancer les tests** : `./mvnw test`

## 2. Diagnostic (tag `lite-start`)
- **Métriques relevées** : 0 issue bloquante, classe la plus complexe : `Board.java`.

| # | Smell | Localisation | Pourquoi c'est un problème |
|---|---|---|---|
| 1 | Violation de la loi de Déméter & forte imbrication | `TurnManager.java` (`isGameOver()`) | `TurnManager` va fouiller dans les entrailles de `Player`, puis de `Board`, puis dans la matrice 2D pour vérifier l'état des chats avec deux boucles imbriquées. |
| 2 | Dépendance cachée / Non testable | `RandomFight.java` (`random = new Random()`) | Dépendance instanciée en dur avec `new`, empêchant d'isoler l'aléatoire pour des tests déterministes. |
| 3 | Magic Numbers & Obsession primitive | `Board.java` (lignes 44, 50) | Utilisation de 0, 1, 2 en dur pour représenter les états des cases (eau, touché) plutôt qu'un Enum ou des constantes. |

**Smell choisi** : Le smell n°1 (`TurnManager.isGameOver()`), car c'est une règle métier essentielle du jeu qui violait les principes de responsabilité unique et de lisibilité, et qui pouvait être facilement sécurisée par des tests unitaires.
Le smell n°2 car il empêche de tester correctement le comportement aléatoire du jeu.

## 3. Tests écrits
- **Classe testée** : `TurnManagerTest.java` (règles de jeu et fin de partie).
- **Liste des tests unitaires (E1)** :
  - `shouldStartAtTurnOne()` : vérifie que le jeu commence bien au tour 1.
  - `shouldSwitchPlayerAndIncrementTurnOnNextTurn()` : vérifie que le tour passe à 2 et que l'adversaire s'inverse.
  - `shouldReturnFalseIfPlayerHasAliveCats()` : vérifie que la partie continue (`false`) tant qu'un chat est en vie sur le plateau.
  - `shouldReturnTrueWhenAllCatsAreAsleep()` : vérifie que la partie se termine (`true`) quand tous les chats sont touchés/endormis.
- **Test paramétré (E2)** :
  - `shouldNotBeGameOverWhenACatIsAlive(CatType catType)` : teste avec `@ParameterizedTest` et `@CsvSource` sur 3 types de chats différents (`TOM`, `MISTACHE`, `PUFI`) vérifie que la règle s'applique à toute la flotte féline.
- **Test double utilisé (E3)** :
  - `shouldCheckGameOverWithMockPlayer()` : isole la dépendance vers `Player` et `Board` à l'aide de **Mockito** (`mock(Player.class)` et `mock(Board.class)`), pour tester `TurnManager` sans dépendre de l'implémentation concrète du plateau.

## 4. Refactoring réalisé
- **Technique utilisée** : **Extract Method** (extraction de méthode).
- **Tests qui le sécurisent** : Les 8 tests de `TurnManagerTest` (tous au vert avant et après le refactoring).
- **Extrait avant / après** :
  - *Avant* :
    ```java
    public boolean isGameOver(Player player) {
        for (Cat[] row : player.getBoard().getOceanGrid()) {
            for (Cat cat : row) {
                if (cat != null && !cat.isAsleep()) {
                    return false;
                }
            }
        }
        return true;
    }
    ```
  - *Après* :
    ```java
    public boolean isGameOver(Player player) {
        Cat[][] oceanGrid = player.getBoard().getOceanGrid();
        return areAllCatsAsleep(oceanGrid);
    }

    private boolean areAllCatsAsleep(Cat[][] oceanGrid) {
        for (Cat[] row : oceanGrid) {
            for (Cat cat : row) {
                if (cat != null && !cat.isAsleep()) {
                    return false;
                }
            }
        }
        return true;
    }
    ```
- **Commits concernés** :
  - Tests : `f175ab0`, `2fe39ca`, `111d750`, `eb7805b`, `e3e5d2c`
  - Refactoring : `00a0cb5`

## 5. Ce qui reste à faire
- **Smells non traités** :
  - Remplacer les entiers magiques (0, 1, 2) de `Board.java` par un `enum CellState`.
- **Bugs découverts** : La taille de `MISTACHE` est de 3 cases (et non 2), nécessitant 3 tirs consécutifs pour l'endormir complètement.
- **Avec une heure de plus** : Nous ajouterions des tests unitaires sur `Board.placeCat()` pour tester et valider les collisions de bateaux/chats et les placements hors-limites.
