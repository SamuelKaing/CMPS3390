package skaing.a9;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Extends BaseObservable to allow it to be bound.  Holds name and value of coin.
 * @author Samuel Kaing
 * @version 1.0
 */
public class Coin extends BaseObservable{
    private String name;
    private double curValue;

    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

    /**
     * Constructor that sets the coin name
     * @param name String that is the coin's name
     */
    public Coin(String name) {
        this.name = name;
    }

    /**
     * name Getter function
     * @return String that is coin's name
     */
    public String getName() {
        return name;
    }

    /**
     * name Setter function
     * @param name String that is coin's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Bindable function that is curValue Getter function
     * @return double that is the coin's value in usd currency
     */
    @Bindable
    public String getCurValue() {
        return numberFormat.format(curValue);
    }

    /**
     * curValue setter function, notifies change
     * @param curValue double that is the coin's value
     */
    public void setCurValue(double curValue) {
        this.curValue = curValue;
        notifyPropertyChanged(BR.curValue);
    }
}
