package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.view.BoardView;

public class BattleController {

    @FXML
    private BoardView playerGrid;   // La grille avec tes chats

    @FXML
    private BoardView radarGrid;    // La grille pour tirer sur l'IA

    @FXML
    private Label turnLabel;

    @FXML
    private TextArea historyLabel;

    private Game game;

    /**
     * Méthode appelée par PlacementController pour transmettre le plateau.
     */
    public void initData(Game initializedGame) {
        this.game = initializedGame;

        // 1. On dessine la flotte du joueur
        setupPlayerGrid();

        // 2. On prépare le radar pour écouter les clics de la souris
        setupRadarGrid();

        updateTurnLabel();
        historyLabel.setText("La bataille commence ! Détectez la flotte féline ennemie sur le radar.");
    }

    private void setupPlayerGrid() {
        // On parcourt la grille du joueur pour afficher ses chats
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                // Si la case n'est pas vide, c'est qu'il y a un chat
                if (game.getPlayer().getBoard().getOceanGrid()[r][c] != null) {
                    playerGrid.markHit(r, c, Color.LIGHTGRAY);
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

        // Vérifier que le clic est bien dans la zone de l'océan (pas sur les lettres/chiffres)
        if (r >= 0 && r < 10 && c >= 0 && c < 10) {

            // --- 1. ATTAQUE DU JOUEUR ---
            AttackResult playerResult = game.attack(r, c);

            // On dessine sur le radar (Rouge = Touché, Bleu = Dans l'eau)
            if (playerResult.isHit()) {
                radarGrid.markHit(r, c, Color.RED);
            } else {
                radarGrid.markHit(r, c, Color.LIGHTBLUE);
            }

            // Mettre à jour l'historique (avec conversion de la colonne en Lettre pour faire joli)
            String colLetter = String.valueOf((char) ('A' + c));
            historyLabel.appendText("\n\nTour " + game.getTurnNumber() + " - VOUS : Tir en " + colLetter + (r + 1) + " -> " + playerResult.message());


            // --- 2. RIPOSTE DE L'IA ---
            AttackResult iaResult = game.iaTurn();
            historyLabel.appendText("\nTour " + game.getTurnNumber() + " - IA : " + iaResult.message());

            // On met à jour la grille du joueur pour voir où l'IA a tiré
            updatePlayerGridAfterIA();

            // On met à jour le numéro du tour
            updateTurnLabel();
        }
    }

    private void updatePlayerGridAfterIA() {
        /*
         * Puisque iaTurn() ne nous renvoie pas les coordonnées exactes du tir,
         * on scanne la grille Radar du joueur. C'est elle qui enregistre où l'IA a frappé.
         * Hypothèse logique : 1 = plouf (eau), 2 = touché.
         * (Modifie les chiffres 1 et 2 si tu as codé ton tableau d'entiers différemment dans Board.java)
         */
        int[][] receivedShots = game.getPlayer().getBoard().getRadarGrid();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (receivedShots[r][c] == 1) { // L'IA a tiré dans l'eau
                    playerGrid.markHit(r, c, Color.LIGHTBLUE);
                } else if (receivedShots[r][c] == 2) { // L'IA a touché un de tes chats
                    playerGrid.markHit(r, c, Color.RED);
                }
            }
        }
    }

    private void updateTurnLabel() {
        // Le TurnManager gère les tours, on l'affiche simplement
        turnLabel.setText("Tour n°" + (game.getTurnNumber() / 2 + 1));
        // Note : Je divise par 2 car ton Game ajoute +1 pour le joueur et +1 pour l'IA.
    }
}