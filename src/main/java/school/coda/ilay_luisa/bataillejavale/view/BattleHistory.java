package school.coda.ilay_luisa.bataillejavale.view;

import javafx.scene.control.TextArea;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

public class BattleHistory extends TextArea {

    public void announceBattleStart() {
        super.setText("La bataille commence ! Détectez la flotte féline ennemie sur le radar.");
    }

    public void logPlayerMiss() {
        appendText("Miaouuu, on a déjà tiré ici ! Choisissez une autre cible.");
    }
    public void blankLine() {
        appendText("\n");
    }

    public void logIaShot(AttackResult attackResult, int turnNumber) {
        appendText("Tour " + turnNumber + " - IA : " + attackResult.message());
    }

    public void logPlayerShot(AttackResult attackResult, int turnNumber, String colLetter, int rowNumber) {
        appendText("Tour " + turnNumber + " - VOUS : Tir en " + colLetter + rowNumber + " -> " + attackResult.message());
    }
}
