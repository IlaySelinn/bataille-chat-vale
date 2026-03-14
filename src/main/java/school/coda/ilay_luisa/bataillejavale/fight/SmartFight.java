package school.coda.ilay_luisa.bataillejavale.fight;

import school.coda.ilay_luisa.bataillejavale.model.CoordinatePoints;
import java.util.ArrayList;
import java.util.List;

public class SmartFight extends RandomFight {

    // La liste des cases à attaquer en priorité
    private final List<CoordinatePoints> targets = new ArrayList<>();

    @Override
    public CoordinatePoints chooseAttack() {
        // 1. Si on a des cibles en mémoire (on est en mode "chasse")
        if (!targets.isEmpty()) {
            return targets.remove(0); // On prend la première cible et on l'enlève de la liste
        }


        return super.chooseAttack();
    }

    public void registerHit(int row, int col, boolean isSunk) {
        if (isSunk) {
            // Si le chat est coulé, on arrête de chasser dans cette zone
            targets.clear();
        } else {
            // Si on a touché
            if (row > 0) targets.add(new CoordinatePoints(row - 1, col));
            if (row < 9) targets.add(new CoordinatePoints(row + 1, col));
            if (col > 0) targets.add(new CoordinatePoints(row, col - 1));
            if (col < 9) targets.add(new CoordinatePoints(row, col + 1));
        }
    }
}