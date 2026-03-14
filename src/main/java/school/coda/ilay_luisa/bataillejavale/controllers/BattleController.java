package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import school.coda.ilay_luisa.bataillejavale.model.Cat;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.view.BoardView;
import school.coda.ilay_luisa.bataillejavale.model.Player;

import java.io.IOException;

import static school.coda.ilay_luisa.bataillejavale.model.CatType.*;


public class BattleController
{
    private Image imageTom, imagePufi, imageMistache, imageUkulele, imageGumus;
    private Game game;

    ///  le tableau
    private boolean[][] alreadyShot = new boolean[10][10];

    @FXML
    private BoardView playerGrid;

    @FXML
    private BoardView radarGrid;

    @FXML
    private Label turnLabel;

    @FXML
    private TextArea historyLabel;


   public void initialize()
    {
        imageTom = loadSafeImage("/image/tom.png");
        imagePufi = loadSafeImage("/image/pufi.png");
        imageMistache = loadSafeImage("/image/mistache.png");
        imageUkulele = loadSafeImage("/image/ukulele.png");
        imageGumus = loadSafeImage("/image/gumus.png");
    }

    private Image loadSafeImage(String path)
    {
        java.net.URL url = getClass().getResource(path);
        if (url == null)
        {
            System.err.println("Pas de photo: " + path);
            return null;
        }
        return new Image(url.toString(), 45, 45, true, true);
    }

    public void initData(Game initializedGame) {
        this.game = initializedGame;

        // 1. On dessine la flotte du joueur
        setupPlayerGrid();

        // 2. On prépare le radar pour écouter les clics de la souris
        setupRadarGrid();

        updateTurnLabel();
        historyLabel.setText("La bataille commence ! Détectez la flotte féline ennemie sur le radar.");
    }

    private void setupPlayerGrid()
    {
               for (int r = 0; r < 10; r++)
               {
                    for (int c = 0; c < 10; c++)
                    {
                        Cat cat = game.getPlayer().getBoard().getOceanGrid()[r][c];


                        if (cat!= null)
                {
                    Image toDraw = null;
                    switch (cat.getType())
                    {
                        case TOM: toDraw =  imageTom; break;
                        case PUFI: toDraw = imagePufi; break;
                        case MISTACHE: toDraw = imageMistache; break;
                        case UKULELE: toDraw = imageUkulele; break;
                        case GÜMÜŞ: toDraw = imageGumus; break;
                    }
                    if (toDraw != null)
                    {
                        playerGrid.drawCatImage(r,c, toDraw);
                    }
                    else
                    {
                        playerGrid.markHit(r,c,Color.LIGHTGRAY);
                    }
                }
            }
        }
    }

    private void setupRadarGrid() {
        // On attache l'événement du clic sur la grille radar
        radarGrid.setOnMouseClicked(this::handleRadarClick);
    }

    private void handleRadarClick(MouseEvent event) {
        // Transformer les coordonnées (X,Y) de la souris en ligne/colonne (0 à 9)
        int r = radarGrid.getRow(event.getY());
        int c = radarGrid.getCol(event.getX());

        // Vérifier que le clic est bien dans la zone de l'océan
        if (r >= 0 && r < 10 && c >= 0 && c < 10) {

            if (alreadyShot[r][c]) {
                historyLabel.appendText("\n Miaouuu, on a déjà tiré ici ! Choisissez une autre cible.");
                return;
            }

            // Si c'est un nouveau tir, on le mémorise pour la prochaine fois
            alreadyShot[r][c] = true;

            // ATTAQUE DU JOUEUR
            AttackResult playerResult = game.attack(r, c);

            // On dessine sur le radar (Rouge = Touché, Bleu = Dans l'eau)
            if (playerResult.isHit()) {
                radarGrid.markHit(r, c, Color.RED);
            } else {
                radarGrid.markHit(r, c, Color.LIGHTBLUE);
            }

            // Mettre à jour l'historique
            String colLetter = String.valueOf((char) ('A' + c));
            historyLabel.appendText("\n\nTour " + game.getTurnNumber() + " - VOUS : Tir en " + colLetter + (r + 1) + " -> " + playerResult.message());

            AttackResult iaResult = game.iaTurn();
            historyLabel.appendText("\nTour " + game.getTurnNumber() + " - IA : " + iaResult.message());

            // On met à jour la grille du joueur pour voir où l'IA a tiré
            updatePlayerGridAfterIA();

            // On met à jour le numéro du tour
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
        turnLabel.setText("Tour n°" + (game.getTurnNumber() / 2 + 1));

    }

    private void checkEndGame() {
        if (game.getIA().getBoard().areAllCatsSunk()) {
            endGame(true, "Inconnu");
            return;
        }

        // On vérifie si l'IA a gagné
        if (game.getPlayer().getBoard().areAllCatsSunk()) {
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