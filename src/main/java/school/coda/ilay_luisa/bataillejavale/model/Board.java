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

    public void copyFrom(Board otherBoard) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                this.oceanGrid[r][c] = otherBoard.getOceanGrid()[r][c];
            }
        }
    }

    public Cat[][] getOceanGrid() {
        return oceanGrid;
    }

    public int[][] getRadarGrid() {
        return radarGrid;
    }

    public boolean areAllCatsSunk() {

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                if (getOceanGrid()[r][c] != null) {

                    if (getRadarGrid()[r][c] != 2) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}