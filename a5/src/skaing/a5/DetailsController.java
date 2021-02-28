package skaing.a5;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import skaing.a6.Coin;
import skaing.a6.UpdateCoinTimerTask;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DetailsController {
    @FXML
    Label labXRPValue;
    @FXML
    Label labSTELValue;

    @FXML
    HBox xrpHBox;
    @FXML
    HBox stelHBox;

    Coin xrpRipple, stellar;

    Timer rippleTimer, stellarTimer;

    /**
     * Class that initializes the prices of each cryptocurrency
     */
    public void initialize() {
        this.xrpRipple = new Coin("ripple");
        this.stellar = new Coin("stellar");

        labXRPValue.textProperty().bind(Bindings.format("$%-5.6f", xrpRipple.currentPriceProperty()));
        labSTELValue.textProperty().bind(Bindings.format("$%-5.6f", stellar.currentPriceProperty()));

        rippleTimer = new Timer();
        rippleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new UpdateCoinTimerTask(xrpRipple));
            }
        }, 0, 5000);

        stellarTimer = new Timer();
        stellarTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new UpdateCoinTimerTask(stellar));
            }
        }, 0, 5000);




    }

    /**
     * Constructor that automatically calls initialize()
     */
    public DetailsController() {
        System.out.println("constructor");
    }

    /**
     * Class that detects user click and takes them to the according line graph
     * @param mouseEvent object of MouseEvent that represents a user click
     * @throws IOException
     */
    public void onDetailButtonClicked(MouseEvent mouseEvent) throws IOException {
        shutdown();
        Parent root = FXMLLoader.load(getClass().getResource("Chart.fxml"));
        Stage primaryStage = (Stage) xrpHBox.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 750, 475));
    }


    public void shutdown() {
        System.out.println("ShutDown was called ... Stopping Timers");
        rippleTimer.cancel();
        stellarTimer.cancel();
    }

}
