package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;

public class ResultController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    @FXML
    private Label detailLabel;


    public void initData(boolean isPlayerWinner, String lastCatSunk) {
        if (isPlayerWinner) {
            titleLabel.setText("🎉 FÉLICITATIONS ! 🎉");
            subtitleLabel.setText("TU AS GAGNÉ !");
            detailLabel.setText("Tu as coulé toute la flotte féline de l'ordinateur !");
            titleLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");
            subtitleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        } else {
            titleLabel.setText("☠️ DÉFAITE... ☠️");
            subtitleLabel.setText("L'ORDINATEUR A GAGNÉ.");
            detailLabel.setText("Ton pauvre chat " + lastCatSunk + " a été le dernier à sombrer dans les profondeurs...");
            titleLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            subtitleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        }
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/menu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1500, 850));
        stage.show();
    }
}