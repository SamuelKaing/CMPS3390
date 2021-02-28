package skaing.a6;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.chart.XYChart;

public class Coin {
    private String name;
    private final DoubleProperty currentPrice;
    private XYChart.Series<Number, Number> historicalValues;

    /**
     * Constructor that sets historicalValues, currentPrice, and name
     * @param name String representing the coin name
     */
    public Coin(String name) {
        historicalValues = new XYChart.Series<>();
        historicalValues.setName(name);
        currentPrice = new SimpleDoubleProperty();
        this.name = name;
    }

    /**
     * Getter for historicalValues
     * @return
     */
    public XYChart.Series<Number, Number> getHistoricalValues() {
        return historicalValues;
    }

    /**
     * Setter for historicalValues
     * @param historicalValues
     */
    public void setHistoricalValues(XYChart.Series<Number, Number> historicalValues) {
        this.historicalValues = historicalValues;
    }

    /**
     * Adds historical values to a new XYChart for however many days selected
     * @param day int that represents the days before the current date
     * @param value double that represents the historical value of the day
     */
    public void addHistoricalValue(int day, double value) {
        historicalValues.getData().add(new XYChart.Data<>(day, value));
    }

    /**
     * Getter for name
     * @return String representing the name of coin
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name of coin
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for current price
     * @return double representing the current price of the coin
     */
    public double getCurrentPrice() {
        return currentPrice.get();
    }

    /**
     *
     * @return
     */
    public DoubleProperty currentPriceProperty() {
        return currentPrice;
    }

    /**
     * Setter for current price
     * @param currentPrice double that represents the current price
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice.set(currentPrice);
    }

    /**
     * Overwrite toString class to display price of coin neatly
     * @return String that shows price of coin neatly
     */
    @Override
    public String toString() {
        return String.format("%20s: $%-10.2f", this.name, this.currentPrice.getValue());
    }
}
