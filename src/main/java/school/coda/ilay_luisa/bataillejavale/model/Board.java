package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

// 🚨 Explication manquante : c'est quoi le board ? A quoi ça sert ?

/// Cette classe gère le board
public final class Board {
    /// Grille océan : celle où nos chats se retrouvent
    private final Cat[][] oceanGrid = new Cat[10][10];
    /// Grille radar : celle du rival
    private final int[][] radarGrid = new int[10][10];

    /// L'endroit où on met les chats sur le board
    public void placeCat(CatType type, int row, int col, boolean isHorizontal) {
        // 🤖🧹 Nettoyez derrière vos IA svp
        // La logique est nickel ici, elle va parfaitement marcher avec ton contrôleur
        int catSize = type.getSize();

        Cat newCat = new Cat(type, catSize);

        // Loop : la logique de row/col
        for (int i = 0; i < type.getSize(); i++) {
            if (isHorizontal) {
                oceanGrid[row][col + i] = newCat;
            } else {
                oceanGrid[row + i][col] = newCat;
            }
        }
    }

    public AttackResult attack(int row, int col) {
        // 🚨 Pourrait être encapsulé dans une méthode
        // Ex. Optional<Cat> target = targetCatInOcean(row, col);

        // Contrôler s'il y a un chat
        Cat target = oceanGrid[row][col];

        // S'il n'y a pas de chat "Raté"
        if (target == null) {
            radarGrid[row][col] = 1; // 1 = Tir dans l'eau
            return new AttackResult(false, false, null);
        }

        // S'il y a un chat "Touché!"
        AttackResult result = target.takeHit();
        radarGrid[row][col] = 2; // 2 = Touché

        return result;
    }

    public void copyFrom(Board otherBoard) {
        for (int r = 0; r < 10; r++) {
            // ⚠️ copie de tableau manuelle
            // Suivre le refactoring automatique de IntelliJ
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

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {

                if (getOceanGrid()[row][col] != null) {

                    if (getRadarGrid()[row][col] != 2) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}