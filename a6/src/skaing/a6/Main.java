package skaing.a6;

/**
 * Rest Tester
 * @author Samuel Kaing
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        //Write your code here
        Coin xrpRipple = new Coin("ripple");
        Coin stellar = new Coin("stellar");
        CoinGecko.updateCurrentPrice(xrpRipple);
        CoinGecko.updateCurrentPrice(stellar);

        CoinGecko.updatePriceHistory(xrpRipple, 7);

        System.out.println(xrpRipple);
        System.out.println(stellar);

    }

}
