package school.coda.ilay_luisa.bataillejavale.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                MainMenu.class.getResource("/school/coda/ilay_luisa/bataillejavale/views/menu.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Bataille Navale des Chats");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}