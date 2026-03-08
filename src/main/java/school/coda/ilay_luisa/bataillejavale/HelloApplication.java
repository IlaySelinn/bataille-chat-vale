package school.coda.ilay_luisa.bataillejavale;


import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Bataille Chat-vale!");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}