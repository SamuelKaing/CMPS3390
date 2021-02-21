package skaing.a5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class STELController {
    @FXML
    ImageView stelBackArrow;

    /**
     * Class that allows user to go back to previous page from Stellar line graph
     * @param mouseEvent object of MouseEvent that represents a user click
     * @throws IOException
     */
    public void onBackArrowClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
        Stage primaryStage = (Stage) stelBackArrow.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 750, 475));
    }
}
