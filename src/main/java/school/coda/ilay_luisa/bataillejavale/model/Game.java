package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.fight.RandomFight;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.rules.TurnManager;
import school.coda.ilay_luisa.bataillejavale.sound.SoundCat;

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

        if (result.sunk()) {
            // Si le chat est coulé (endormi)
            SoundCat.playSound("src/sound/miaou_aigue.wav");
        }
        else if (result.hits()) {
            // Si le chat est touché (mais pas coulé)
            SoundCat.playSound("src/sound/miaou.wav");
        }
        else {
            // S'il n'est ni coulé ni touché, c'est raté (Plouf !)
            SoundCat.playSound("src/sound/ronron.wav");
        }

        turnManager.nextTurn();

        return result;
    }

    public AttackResult iaTurn() {
        AttackResult result = ia.playAITurn(player, iaAI, turnManager);

        if (result.sunk()) {
            // Si le chat est coulé (endormi)
            SoundCat.playSound("src/sound/miaou_aigue.wav");
        }
        else if (result.hits()) {
            // Si le chat est touché (mais pas coulé)
            SoundCat.playSound("src/sound/miaou.wav");
        }
        else {
            // S'il n'est ni coulé ni touché, c'est raté (Plouf !)
            SoundCat.playSound("src/sound/ronron.wav");
        }
        return result;
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