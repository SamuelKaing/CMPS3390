package skaing.a9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import skaing.a9.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment stellarFragment, rippleFragment;
    private Coin stellar, ripple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        stellar = new Coin("stellar");
        ripple = new Coin("ripple");
        getCurrentValue(stellar);
        getCurrentValue(ripple);
        stellarFragment = new DetailsFragment(stellar);
        rippleFragment = new DetailsFragment(ripple);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setStellar(stellar);
        activityMainBinding.setRipple(ripple);
    }

    /**
     * Http request gets current price of coin
     * @param coin Coin whose current price is being retrieved
     */
    private void getCurrentValue(Coin coin) {
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd"
                , coin.getName());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject json = response.getJSONObject(coin.getName());
                    double tmpPrice = json.getDouble("usd");
                    coin.setCurValue(tmpPrice);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Opens graph depending on which view is selected
     * @param view View that represents which coin is selected
     */
    public void onTableRowClick(View view) {
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        if(view.getId() == R.id.trStellar) {
            fragmentTransaction.replace(R.id.flFragment, stellarFragment);
            fragmentTransaction.commit();
        } else if(view.getId() == R.id.trRipple) {
            fragmentTransaction.replace(R.id.flFragment, rippleFragment);
            fragmentTransaction.commit();
        }
    }
}