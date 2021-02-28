package skaing.a6;

public class UpdateCoinTimerTask implements Runnable{
    private Coin coin;

    /**
     * Updates coin timer task
     * @param coin Coin that represents the coin whose timer task is being updated
     */
    public UpdateCoinTimerTask(Coin coin) {
        this.coin = coin;
    }

    /**
     * Overwrite of run that displays price change neatly if and when price is changed
     */
    @Override
    public void run() {
        System.out.println("checking for update on " + coin.getName());
        double curValue = this.coin.getCurrentPrice();
        CoinGecko.updateCurrentPrice(this.coin);
        if(curValue != this.coin.getCurrentPrice()) {
            System.out.println("---------------- Price Changed " + coin.getName() + " from "
            + curValue + " --> " + this.coin.getCurrentPrice() + "----------------");
        }

    }
}
