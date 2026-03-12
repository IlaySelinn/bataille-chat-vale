package school.coda.ilay_luisa.bataillejavale.controllers;

import com.almasb.fxgl.pathfinding.astar.AStarGridView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.view.BoardViev;

public class BattleController {

    @FXML
    private BoardViev playerGrid;   // Grille océan du joueur
    @FXML
    private BoardViev radarGrid;    // Grille radar
    @FXML
    private Label turnLabel;
    @FXML
    private TextArea historyLabel;

    private final Game game = new Game("Player");

    private final int gridSize = 10;

    @FXML
    public void initialize() {
        setupPlayerGrid();
        setupRadarGrid();
        updateTurnLabel();
    }

    private void setupPlayerGrid() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);
                // Ici, on peut afficher les chats du joueur
                if (game.getPlayer().getBoard().getOceanGrid()[row][col] != null) {
                    cell.setStyle("-fx-background-color: gray;"); // Chat présent
                }
              ///  playerGrid.add(cell, col, row);
            }
        }
    }

    private void setupRadarGrid() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = col;

                cell.setOnAction(event -> {
                    handlePlayerAttack(r, c, cell);
                });

              ///  radarGrid.add(cell, col, row);
            }
        }
    }

    private void handlePlayerAttack(int row, int col, Button cell) {
        AttackResult result = game.attack(row, col);

        if (result.isHit()) {
            cell.setStyle("-fx-background-color: red;");
        } else {
            cell.setStyle("-fx-background-color: lightblue;");
        }

        historyLabel.setText(historyLabel.getText() + "\n" + "Tour " + game.getTurnNumber() + ": " + result.message());

        // Tour de l'IA
        AttackResult iaResult = game.iaTurn();
        updatePlayerGrid(iaResult);
        updateTurnLabel();
    }

    private void updatePlayerGrid(AttackResult iaResult) {
        // Ici, tu peux mettre à jour les touches de l'IA sur la grille océan
        // Exemple simple : colorer une case aléatoire
        // Pour l'instant, tu peux l'implémenter plus tard avec des coordonnées exactes
    }

    private void updateTurnLabel() {
        turnLabel.setText("Tour n°" + game.getTurnNumber());
    }
}