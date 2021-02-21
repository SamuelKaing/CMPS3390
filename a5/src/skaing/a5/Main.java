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
        Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
        primaryStage.setTitle("Coin Tracker");
        primaryStage.setScene(new Scene(root, 750, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
