package skaing.a5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import skaing.a6.Coin;
import skaing.a6.CoinGecko;

import java.io.IOException;

public class ChartController {
    @FXML
    ImageView xrpBackArrow;

    @FXML
    LineChart<Number, Number> priceChart;

    @FXML
    ComboBox cbDateRange;

    @FXML
    ComboBox cbCoinSelector;


    Coin xrpRipple, stellar;

    /**
     * Class that adds points to create a line graph representing price over time
     */
    public void initialize() {
        priceChart.setAnimated(false);
        xrpRipple = new Coin("ripple");
        stellar = new Coin("stellar");

        CoinGecko.updatePriceHistory(xrpRipple, 365);
        CoinGecko.updatePriceHistory(stellar, 365);

        priceChart.getData().add(xrpRipple.getHistoricalValues());

    }

    /**
     * Class that allows user to go back to previous window from XRP line graph
     * @param mouseEvent object of MouseEvent that represents a user click
     * @throws IOException
     */
    public void onBackArrowClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
        Stage primaryStage = (Stage) xrpBackArrow.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 750, 475));
    }

    /**
     * Changes the value of days based on selection of ComboBox
     * @param actionEvent ActionEvent that represents a selection in ComboBox
     */
    public void onDateRangeChanged(ActionEvent actionEvent) {
        int days = 0;
        switch((String)cbDateRange.getValue()) {
            case "Year":
                days = 365;
                break;
            case "90 days":
                days = 90;
                break;
            case "60 days":
                days = 60;
                break;
            case "30 days":
                days = 30;
                break;
            case "Week":
                days = 7;
                break;
        }
        CoinGecko.updatePriceHistory(xrpRipple, days);
        CoinGecko.updatePriceHistory(stellar, days);
        updateChart();
    }

    /**
     * Updates chart historical values based on the date chosen
     */
    private void updateChart() {
        priceChart.getData().clear();
        switch((String)cbCoinSelector.getValue()) {
            case "XRPRipple":
                priceChart.getData().add(xrpRipple.getHistoricalValues());
                break;
            case "Stellar":
                priceChart.getData().add(stellar.getHistoricalValues());
                break;
            case "All":
                priceChart.getData().add(xrpRipple.getHistoricalValues());
                priceChart.getData().add(stellar.getHistoricalValues());
                break;
        }

    }

    /**
     * Updates chart when a different coin is selected
     * @param actionEvent ActionEvent that represents a different coin selection
     */
    public void onCoinSelectionChanged(ActionEvent actionEvent) {
        updateChart();
    }
}
