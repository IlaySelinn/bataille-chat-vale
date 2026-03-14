package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.fight.RandomFight;
import school.coda.ilay_luisa.bataillejavale.fight.SmartFight;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.rules.TurnManager;

public class Player {
    private final String name;
    private final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public AttackResult playAITurn(Player opponent, RandomFight iaAI, TurnManager turnManager) {
        CoordinatePoints shot;

        // On génère un tir tant que la case a déjà été touchée (différent de 0)
        do {
            shot = iaAI.chooseAttack();
        } while (opponent.getBoard().getRadarGrid()[shot.row()][shot.col()] != 0);

        // On attaque l'adversaire
        AttackResult result = opponent.getBoard().attack(
                shot.row(),
                shot.col()
        );

        // ia va savoir quoi faire de son tir

        if (iaAI instanceof SmartFight) {
            SmartFight smartAI = (SmartFight) iaAI;
            // Si le chat est touché ou coulé, on enregistre les coordonnées
            if (result.hits() || result.sunk()) {
                smartAI.registerHit(shot.row(), shot.col(), result.sunk());
            }
        }

        // On passe au tour suivant
        turnManager.nextTurn();

        return result;
    }
}