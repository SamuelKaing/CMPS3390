package skaing.a9;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Samuel Kaing
 * @version 1.0
 */
public class DetailsFragment extends Fragment {
    private final Context context;
    Coin coin;
    TextView chartLabel;
    private LineData data;
    LineChart lineChart;
    int color = 0;

    /**
     * Constructor sets context and coin and sets color depending on coin
     * @param context Context
     * @param coin Coin that represents the cryptocoin
     */
    public DetailsFragment(Context context, Coin coin) {
        this.coin = coin;
        this.context = context;

        if(coin.getName() == "stellar") {
            color = ContextCompat.getColor(context, R.color.stellar);
        } else if (coin.getName() == "ripple") {
            color = ContextCompat.getColor(context, R.color.ripple);
        }

        getHistoricalData();
    }

    /**
     * Updates chart
     */
    private void updateChart() {
        if(lineChart != null) {
            lineChart.setData(data);
            lineChart.invalidate();
            lineChart.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    /**
     * Gets chart and sets it, gets line chart and animates it
     * @param savedInstanceState Bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chartLabel = getView().findViewById(R.id.txtChartLabel);
        chartLabel.setText(coin.getName().toUpperCase());
        lineChart = getView().findViewById(R.id.lcPrice);
        lineChart.animateY(100);
        lineChart.animateX(2000);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        updateChart();
    }

    /**
     * Gets last 90 days of pricing
     */
    private void getHistoricalData() {
        String url = String.format("https://api.coingecko.com/api/v3/coins/%s/market_chart?vs_currency=usd&days=90&interval=daily"
                , coin.getName());
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray prices = response.getJSONArray("prices");

                    ArrayList<Entry> yValues = new ArrayList<>();
                    for(int i=0; i<prices.length(); i++) {
                        float val = (float)prices.getJSONArray(i).getDouble(1);
                        yValues.add(new Entry(i, val));
                    }

                    LineDataSet dataSet = new LineDataSet(yValues, "Daily Price");
                    dataSet.setColor(color);
                    dataSet.setLineWidth(3);
                    dataSet.setDrawCircles(false);
                    dataSet.setDrawValues(false);

                    data = new LineData(dataSet);
                    updateChart();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}