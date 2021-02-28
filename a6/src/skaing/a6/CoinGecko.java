package skaing.a6;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoinGecko {

    /**
     * Creates an HttpClient object
     */
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * Updates the priceHistory of a coin
     * @param coin Coin that represents the coin whose priceHistory is being updated
     * @param days int that represents the amount of days to be updated
     */
    public static void updatePriceHistory(Coin coin, int days) {
        coin.getHistoricalValues().getData().clear();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.coingecko.com/api/v3/coins/"
                        + coin.getName() + "/market_chart?vs_currency=usd&days="
                        + days + "&interval=daily"))
                .setHeader("User-Agent", "Java 11 HttpClient skaing.a6")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(response.body());
            JSONArray priceArray = obj.getJSONArray("prices");
            for(int i = 0; i<priceArray.length(); i++) {
                JSONArray tempArray = priceArray.getJSONArray(i);
                double tmpValue = tempArray.getDouble(1);
                coin.addHistoricalValue(i - priceArray.length() + 1, tmpValue);
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the currentPrice of the coin
     * @param coin Coin that represents which coin's price is changing
     */
    public static void updateCurrentPrice(Coin coin) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=" + coin.getName() + "&vs_currencies=usd"))
                .setHeader("User-Agent", "Java 11 HttpClient skaing.a6")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            double value = jsonObject.getJSONObject(coin.getName()).getDouble("usd");
            coin.setCurrentPrice(value);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
