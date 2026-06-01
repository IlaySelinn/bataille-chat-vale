package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import school.coda.ilay_luisa.bataillejavale.model.Cat;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.view.BoardView;
import school.coda.ilay_luisa.bataillejavale.view.BattleHistory;

import java.io.IOException;

public class BattleController {
    private Image imageTom, imagePufi, imageMistache, imageUkulele, imageGumus;
    private Game game;

    private boolean[][] alreadyShot = new boolean[10][10];

    @FXML
    private BoardView playerGrid;

    @FXML
    private BoardView radarGrid;

    @FXML
    private Label turnLabel;

    @FXML
    private BattleHistory battleHistory;


    public void initialize() {
        imageTom = loadSafeImage("/image/tom.png");
        imagePufi = loadSafeImage("/image/pufi.png");
        imageMistache = loadSafeImage("/image/mistache.png");
        imageUkulele = loadSafeImage("/image/ukulele.png");
        imageGumus = loadSafeImage("/image/gumus.png");
    }

    private Image loadSafeImage(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Pas de photo: " + path);
            return null;
        }
        return new Image(url.toString(), 45, 45, true, true);
    }

    public void initData(Game initializedGame) {
        this.game = initializedGame;

        // 🚨 Commentaire pourrait être la javadoc de la méthode appelée
        // 1. On dessine la flotte du joueur
        setupPlayerGrid();

        // 🚨 Commentaire pourrait être la javadoc de la méthode appelée
        // 2. On prépare le radar pour écouter les clics de la souris
        setupRadarGrid();

        updateTurnLabel();
        battleHistory.announceBattleStart();
    }

    private void setupPlayerGrid() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Cat cat = game.getPlayer().getBoard().getOceanGrid()[r][c];


                if (cat != null) {
                    Image toDraw = switch (cat.getType()) {
                        case TOM -> imageTom;
                        case PUFI -> imagePufi;
                        case MISTACHE -> imageMistache;
                        case UKULELE -> imageUkulele;
                        case GÜMÜŞ -> imageGumus;
                    };
                    if (toDraw != null) {
                        playerGrid.drawCatImage(r, c, toDraw);
                    } else {
                        playerGrid.markHit(r, c, Color.LIGHTGRAY);
                    }
                }
            }
        }
    }

    private void setupRadarGrid() {
        // 🚨 Commentaire inutile, le code est suffisamment explicite
        // On attache l'événement du clic sur la grille radar
        radarGrid.setOnMouseClicked(this::handleRadarClick);
    }

    private void handleRadarClick(MouseEvent event) {
        // Transformer les coordonnées (X,Y) de la souris en ligne/colonne (0 à 9)
        int row = radarGrid.getRow(event.getY());
        int col = radarGrid.getCol(event.getX());

        // Vérifier que le clic est bien dans la zone de l'océan
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {

            if (alreadyShot[row][col]) {
                battleHistory.blankLine();
                battleHistory.logPlayerMiss();
                return;
            }

            // 🚨 Commentaire inutile, le code est suffisamment explicite
            // Si col'est un nouveau tir, on le mémorise pour la prochaine fois
            alreadyShot[row][col] = true;

            // ATTAQUE DU JOUEUR
            AttackResult playerResult = game.attack(row, col);

            // On dessine sur le radar (Rouge = Touché, Bleu = Dans l'eau)
            if (playerResult.isHit()) {
                radarGrid.markHit(row, col, Color.RED);
            } else {
                radarGrid.markHit(row, col, Color.LIGHTBLUE);
            }

            battleHistory.blankLine();
            battleHistory.blankLine();
            int turnNumber = game.getTurnNumber();
            battleHistory.logPlayerShot(playerResult, turnNumber, col, row);

            AttackResult iaResult = game.iaTurn();
            battleHistory.blankLine();
            battleHistory.logIaShot(iaResult, turnNumber);

            updatePlayerGridAfterIA();

            updateTurnLabel();

            checkEndGame();
        }
    }

    private void updatePlayerGridAfterIA() {

        int[][] receivedShots = game.getPlayer().getBoard().getRadarGrid();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (receivedShots[r][c] == 1) { // L'IA a tiré dans l'eau
                    playerGrid.markHit(r, c, Color.LIGHTBLUE);
                } else if (receivedShots[r][c] == 2) { // L'IA a touché un des chats
                    playerGrid.markHit(r, c, Color.RED);
                }
            }
        }
    }

    private void updateTurnLabel() {
        // Le TurnManager gère les tours, on l'affiche simplement
        // ⚠️ Encapsulation : Est-ce que  game.getTurnNumber() pourrait retourner directement la bonne valeur sans avoir à / 2 + 1 ?
        turnLabel.setText("Tour n°" + (game.getTurnNumber() / 2 + 1));

    }

    private void checkEndGame() {
        if (game.hasIaLost()) {
            endGame(true, "Inconnu");
            return;
        }

        // On vérifie si l'IA a gagné
        if (game.hasPlayerLost()) {
            String lastCat = "Dernier Chat";
            endGame(false, lastCat);
        }
    }

    private void endGame(boolean playerWon, String lastCatSunk) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/result.fxml"));
            Parent root = loader.load();

            // On passe les infos au ResultController
            ResultController resultController = loader.getController();
            resultController.initData(playerWon, lastCatSunk);

            // On change la scène
            Stage stage = (Stage) radarGrid.getScene().getWindow();
            stage.setScene(new Scene(root, 1500, 850));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'écran de fin.");
        }
    }
}