package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

/// Cette classe gère le board
public final class Board {
    ///  Grille océan : celle de nos chats se retrouve
    private final Cat[][] oceanGrid = new Cat[10][10];
    /// Grille radar :  celle de rival
    private final int[][] radarGrid = new int[10][10];

    public Board() {
        setupFixFleet();
    }

    ///  l'endroit où on met les chats sur le board


    // Nyan a1 -> a-2
    // placer nyan en 0 0
    //oceanGrid [0][0] = new Cat("Ukulele", 3);
    private void setupFixFleet() {
        placeCat(CatType.TOM, 0, 0, true); //Porte-avion : Tom (5 cases)
        placeCat(CatType.PUFI, 2, 0, false);// Cuirassé : Pufi (4 cases)
        placeCat(CatType.MISTACHE, 4, 4, true);// Destroyer : Mistache (3 cases)
        placeCat(CatType.UKULELE, 6, 1, false);// Sous-marin : Ukulele (3 cases)
        placeCat(CatType.GÜMÜŞ, 9, 8, true); // PATROUILLEUR: Gümüş (2 cases)
    }
    public void placeCat(CatType type, int row, int col, boolean isHorizontal) {
        String catName = type.name();
        int catSize = type.getSize();

        /// Créer l'objet "cat"
        Cat newCat = new Cat(type, catSize);

        ///Loop : la logique de row/col
        for (int i = 0; i < type.getSize(); i++)
        {
            if (isHorizontal)
            {
                ///Horizontal
                oceanGrid[row][col + i ] =  newCat;
            }
            else
                /// Vértical
                oceanGrid[row +i ][col] = newCat;
        }
    }
    public AttackResult attack(int row, int col) {
        Cat target = oceanGrid[row] [col];
        if (target == null) {
            return new AttackResult(false, false, null);
        }
        return target.takeHit();
    }
}
