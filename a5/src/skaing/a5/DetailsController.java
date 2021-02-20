package skaing.a5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailsController {
    @FXML
    Label labXRPValue;
    @FXML
    Label labSTELValue;

    @FXML
    HBox xrpHBox;
    @FXML
    HBox stelHBox;

    public void initialize() {
        labXRPValue.setText("$0.5672");
        labSTELValue.setText("$0.5234");
        System.out.println("initialization");
    }

    public DetailsController() {
        System.out.println("constructor");
    }

    public void onDetailButtonClicked(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getSource() == xrpHBox) {
            System.out.println("Changed to XRP");

            Parent root = FXMLLoader.load(getClass().getResource("XRP.fxml"));
            Stage primaryStage = (Stage) xrpHBox.getScene().getWindow();
            primaryStage.setScene(new Scene(root, 750, 475));
        }
        if(mouseEvent.getSource() == stelHBox) {
            System.out.println("Changed to Stellar");
            Parent root = FXMLLoader.load(getClass().getResource("STEL.fxml"));
            Stage primaryStage = (Stage) xrpHBox.getScene().getWindow();
            primaryStage.setScene(new Scene(root, 750, 475));
        }
    }
}
