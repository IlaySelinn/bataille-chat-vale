package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

/// Cette classe gère le board
public final class Board {
    /// Grille océan : celle où nos chats se retrouvent
    private final Cat[][] oceanGrid = new Cat[10][10];
    /// Grille radar : celle du rival
    private final int[][] radarGrid = new int[10][10];

    public Board() {
        // MODIFICATION ICI : On a retiré l'appel à setupFixFleet()
        // Le plateau est maintenant 100% vide au démarrage,
        // prêt à recevoir les chats depuis le placement.
    }

    public boolean allCatsAsleep() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Cat cat = oceanGrid[row][col];
                if (cat != null && !cat.isAsleep()) return false;
            }
        }
        return true;
    }

    /// L'endroit où on met les chats sur le board
    public void placeCat(CatType type, int row, int col, boolean isHorizontal) {
        // La logique est nickel ici, elle va parfaitement marcher avec ton contrôleur
        int catSize = type.getSize();

        /// Créer l'objet "cat"
        Cat newCat = new Cat(type, catSize);

        /// Loop : la logique de row/col
        for (int i = 0; i < type.getSize(); i++) {
            if (isHorizontal) {
                /// Horizontal
                oceanGrid[row][col + i] = newCat;
            } else {
                /// Vertical
                oceanGrid[row + i][col] = newCat;
            }
        }
    }

    public AttackResult attack(int row, int col) {
        /// Contrôler s'il y a un chat
        Cat target = oceanGrid[row][col];

        /// S'il n'y a pas de chat "Raté"
        if (target == null) {
            radarGrid[row][col] = 1; // 1 = Tir dans l'eau
            return new AttackResult(false, false, null);
        }

        /// S'il y a un chat "Touché!"
        AttackResult result = target.takeHit();
        radarGrid[row][col] = 2; // 2 = Touché

        return result;
    }

    public Cat[][] getOceanGrid() {
        return oceanGrid;
    }

    public int[][] getRadarGrid() {
        return radarGrid;
    }

    /* * J'ai supprimé la méthode setupFixFleet() car il y en a plus besoin
     * pour le joueur humain.
     * (Astuce : On pourra la remettre plus tard dans une classe "Bot"
     * si on veut créer un adversaire automatique facile !)
     */
}