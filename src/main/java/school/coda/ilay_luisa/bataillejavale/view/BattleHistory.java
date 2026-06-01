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

    public void logPlayerShot(AttackResult attackResult, int turnNumber, int colIndex, int rowIndex) {
        appendText("Tour " + turnNumber + " - VOUS : Tir en " + columnIndexAsLetter(colIndex) + rowIndexAsNumber(rowIndex) + " -> " + attackResult.message());
    }

    /**
     *
     * @param rowIndex begins at 0
     * @return row number to be rendered on board
     */
    private static int rowIndexAsNumber(int rowIndex) {
        return rowIndex + 1;
    }

    /**
     * @param columnIndex begins at 0
     * @return letter matching the column to be rendered on board
     */
    private static String columnIndexAsLetter(int columnIndex) {
        return String.valueOf((char) ('A' + columnIndex));
    }
}
