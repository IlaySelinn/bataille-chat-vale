package school.coda.ilay_luisa.bataillejavale.fight;

import school.coda.ilay_luisa.bataillejavale.model.CoordinatePoints;

public class RandomFight {
    private final java.util.Random random = new java.util.Random();

    public CoordinatePoints chooseAttack() {
        int row = random.nextInt(10);
        int col = random.nextInt(10);

        return new  CoordinatePoints(row, col);
    }
}
