package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import school.coda.ilay_luisa.bataillejavale.model.Player;

import java.io.IOException;

public class ResultController {

    @FXML
    private Label resultLabel;

    public void setWinner(Player winner) {
        resultLabel.setText("Le joueur " + winner.getName() + " a gagné !");
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/menu.fxml")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }
}

/// simetrik yap!

