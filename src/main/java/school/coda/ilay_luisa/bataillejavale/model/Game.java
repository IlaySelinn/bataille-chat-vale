package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.fight.RandomFight;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.rules.TurnManager;

public class Game {

    private final Player player;
    private final Player ia;

    private final RandomFight iaAI = new RandomFight();
    private final TurnManager turnManager;

    public Game(String playerName) {
        player = new Player(playerName);
        ia = new Player("IA");
        this.turnManager = new TurnManager(player, ia);
    }

    public AttackResult attack(int row, int col) {

        Player opponent = turnManager.getOpponent();

        AttackResult result = opponent.getBoard().attack(row, col);

        turnManager.nextTurn();

        return result;
    }

    public AttackResult iaTurn() {

        CoordinatePoints shot = iaAI.chooseAttack();

        AttackResult result = player.getBoard().attack(
                shot.getRow(),
                shot.getCol()
        );

        turnManager.nextTurn();

        return result;
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public int getTurnNumber() {
        return turnManager.getTurnNumber();
    }

    public Player getPlayer() {
        return player;
    }

    public Player getIA() {
        return ia;
    }
}