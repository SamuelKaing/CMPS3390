package skaing.finalproject;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This fragment class is mainly called when selected or when the player dies.
 * It will display previous tries by writing the text in the journal fragment to a JSON
 * to read later.
 */
public class GraveyardFragment extends Fragment implements GameActivity.accessGraveyard {
    private View view;
    private ImageButton btnBack;
    private TextView tvGraveyard;
    private TextView tvTitle;
    private final static String FILE_NAME = "graveyard.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_graveyard, container, false);

        // Just a title for the fragment
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvGraveyard = view.findViewById(R.id.tvGraveyard);

        // Back button that switches back to story fragment when pressed
        btnBack = view.findViewById(R.id.btnBackGraveyard);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, GameActivity.storyFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        try {
            tvGraveyard.setText(readGraveyardJSON("tombstone", getActivity()));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * Will write text from journal fragment (journalText) to JSON file when player dies,
     * adding it to the graveyard.
     * @param journalText String which is the text from the journalFragment
     * @param context Context used to open the file
     */
    public void addToGraveyardJSON(String journalText, Context context) {
        JSONArray jsonArray = new JSONArray();
        JSONObject journal = new JSONObject();
        int t = 0;
        try {
            journal.put("tombstone", journalText);
            jsonArray.put(journal);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            fos.write(journal.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reads graveyard.json file and sets tvGraveyard's text to what is read
     * @return String that is read from json file
     */
    private String readGraveyardJSON(String tombstone, Context context) throws JSONException, IOException {
        FileInputStream fis = null;

            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            Log.d("OBJECT: ", jsonObject.toString());

            return jsonObject.getString("tombstone");
    }
}