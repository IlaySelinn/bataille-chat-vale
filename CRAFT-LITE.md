# CRAFT LITE : Bataille Chat-Vale

## 1. Lancement
- **Binôme** : Luisa Couette & Ilay Selin Koçak
- **Commande exacte pour lancer les tests** : `./mvnw test ou ./mvn test`

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
- **Classes testées** : `TurnManagerTest.java` et `RandomFightTest.java`.
- **Liste des tests unitaires (E1)** :
  - *TurnManagerTest* :
    - `shouldStartAtTurnOne()` : vérifie que le jeu commence bien au tour 1.
    - `shouldSwitchPlayerAndIncrementTurnOnNextTurn()` : vérifie que le tour passe à 2 et que l'adversaire s'inverse.
    - `shouldReturnFalseIfPlayerHasAliveCats()` : vérifie que la partie continue (`false`) tant qu'un chat est en vie sur le plateau.
    - `shouldReturnTrueWhenAllCatsAreAsleep()` : vérifie que la partie se termine (`true`) quand tous les chats sont touchés/endormis.
  - *RandomFightTest* :
    - `chooseAttack_ShouldReturnCoordinatesWithinGridBounds()` : vérifie que les coordonnées retournées sont non nulles et comprises entre 0 et 9.
    - `chooseAttack_ShouldNeverReturnNegativeCoordinates()` : s'assure que les coordonnées générées ne sont jamais négatives (≥ 0).
    - `chooseAttack_ShouldNeverExceedMaximumGridIndex()` : s'assure que les coordonnées ne dépassent jamais la taille max de la grille (≤ 9).
    - `chooseAttack_ShouldReturnNewInstanceOnEachCall()` : valide qu'une nouvelle instance distincte de `CoordinatePoints` est retournée à chaque tir.
- **Tests paramétrés (E2)** :
  - `shouldNotBeGameOverWhenACatIsAlive(CatType catType)` : teste avec `@ParameterizedTest` et `@CsvSource` sur 3 types de chats (`TOM`, `MISTACHE`, `PUFI`) pour vérifier que la règle s'applique à toute la flotte féline.
  - `chooseAttack_shouldAlwaysStayWithinBounds_AcrossMultipleRuns(int runNumber)` : teste avec `@ParameterizedTest` et `@ValueSource(ints = {1, 2, 3, 4, 5})` pour s'assurer de la stabilité des coordonnées dans la grille [0-9] sur 5 exécutions successives.
- **Tests doubles utilisés (E3)** :
  - `shouldCheckGameOverWithMockPlayer()` : isole la dépendance vers `Player` et `Board` à l'aide de **Mockito** (`mock(Player.class)` et `mock(Board.class)`), pour tester `TurnManager` sans dépendre de l'implémentation concrète du plateau.
  - `chooseAttack_ShouldReturnDeterministicCoordinates_WhenRandomIsMocked()` : isole la dépendance aléatoire en mockant `java.util.Random` (`mock(Random.class)` et `thenReturn(3, 7)`), permettant de tester le choix d'attaque de `RandomFight` de façon 100% déterministe.

## 4. Refactoring réalisé

### Refactoring 1 : `TurnManager.java` (Smell n°1)
- **Technique utilisée** : **Extract Method** (extraction de méthode).
- **Tests qui le sécurisent** : Les tests de `TurnManagerTest` (tous au vert avant et après le refactoring).
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

### Refactoring 2 (Bonus) : `RandomFight.java` (Smell n°2)
- **Technique utilisée** : **Dependency Injection** (Injection de dépendance par constructeur).
- **Tests qui le sécurisent** : La suite `RandomFightTest` (bornes, non-nullité, instance distincte et test avec mock déterministe).
- **Extrait avant / après** :
  - *Avant* :
    ```java
    public class RandomFight {
        private final java.util.Random random = new java.util.Random();

        public CoordinatePoints chooseAttack() {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            return new CoordinatePoints(row, col);
        }
    }
    ```
  - *Après* :
    ```java
    public class RandomFight {
        private final Random random;

        public RandomFight(Random random) {
            this.random = random;
        }

        public RandomFight() {
            this(new Random());
        }

        public CoordinatePoints chooseAttack() {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            return new CoordinatePoints(row, col);
        }
    }
    ```
- **Commits concernés** :
  - Tests : `e14e339`, `e430597`, `4bec61c`, `975281e`, `66db800`, `fc4970d`
  - Refactoring : `303ee8f`

## 5. Ce qui reste à faire
- **Smells non traités** :
  - Remplacer les entiers magiques (0, 1, 2) de `Board.java` par un `enum CellState`.
- **Bugs découverts** : La taille de `MISTACHE` est de 3 cases (et non 2), nécessitant 3 tirs consécutifs pour l'endormir complètement. 
- Problèmes de fautes d'orthographe dans les tests, qui ne passaient donc pas. Il fallait corriger les fautes d'orthographe pour que les tests passent au vert.
- Utilisation de classe qui n'existe pas dans le code source (`CoordinatePoints`), qui a été remplacée par `Coordinate`.
- **Avec une heure de plus** : Nous ajouterions des tests unitaires sur `Board.placeCat()` pour tester et valider les collisions de chats et les placements hors-limites, ainsi qu'un Test Data Builder pour simplifier les setups.
