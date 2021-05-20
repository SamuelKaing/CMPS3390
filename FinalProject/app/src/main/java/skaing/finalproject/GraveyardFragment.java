package skaing.finalproject;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This fragment class is mainly called when selected or when the player dies.
 * It will display previous tries by writing the text in the journal fragment to a JSON
 * to read later.
 */
public class GraveyardFragment extends Fragment implements GameActivity.accessGraveyard {
    private View view;
    private ImageButton btnBack;
    private Button btnClearGraveyard;
    private static final String FILE_NAME = "graveyard.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_graveyard, container, false);

        btnClearGraveyard = view.findViewById(R.id.btnClearGraveyard);
        btnClearGraveyard.setPaintFlags(btnClearGraveyard.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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

        readGraveyardJSON();

        return view;
    }

    /**
     * Will write text from journal fragment (journalText) to JSON file when player dies,
     * adding it to the graveyard.
     * @param journalText String which is the text from the journalFragment
     */
    public void addToGraveyardJSON(String journalText) {
        JSONObject journal = new JSONObject();

        try {
            journal.put("tombstone", journalText);
            FileWriter file = new FileWriter(FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads graveyard.json file and adds it to the ExpandableListView (Graveyard)
     */
    private void readGraveyardJSON() {
    }

    /**
     * Clears graveyard when button is pressed. Helps the player avoid clutter.
     */
    private void clearGraveyard() {

    }


}