package school.coda.ilay_luisa.bataillejavale.fight;

import school.coda.ilay_luisa.bataillejavale.model.CoordinatePoints;
import java.util.Random;


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

        return  new CoordinatePoints(row, col);
    }

}
