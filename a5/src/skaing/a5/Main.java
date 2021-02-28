package skaing.a5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main driver class for a5
 * @author Samuel Kaing
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Class that starts up application, sets the title and default page dimensions, and displays application
     * @param primaryStage Stage type that represents the first window to open up from the application
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
        Parent root = loader.load();
        DetailsController controller = loader.getController();
        Scene scene = new Scene(root, 700, 475);
        primaryStage.setTitle("Coin Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnHidden(e -> controller.shutdown());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
