package skaing.a6;

public class Main {

    public static void main(String[] args) {
        //Write your code here
        Coin stellar = new Coin("stellar");
        Coin xrpRipple = new Coin("ripple");
        CoinGecko.updateCurrentPrice(stellar);
        CoinGecko.updateCurrentPrice(xrpRipple);
    }

}
