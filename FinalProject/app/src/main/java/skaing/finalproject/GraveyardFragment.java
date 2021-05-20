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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This fragment class is mainly called when selected or when the player dies.
 * It will display previous tries by writing the text in the journal fragment to a JSON
 * to read later.
 */
public class GraveyardFragment extends Fragment implements GameActivity.accessGraveyard {
    private View view;
    private ImageButton btnBack;
    private Button btnClearGraveyard;
    private ExpandableTextView etvGraveStone;
    private TextView tvGraveStoneText;
    private ImageButton ibCollapse;
    private final static String FILE_NAME = "graveyard.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_graveyard, container, false);

        btnClearGraveyard = view.findViewById(R.id.btnClearGraveyard);
        btnClearGraveyard.setPaintFlags(btnClearGraveyard.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        etvGraveStone = view.findViewById(R.id.expandable_text_view);

        etvGraveStone.setText("steinm veon ovne i ivne o iesnv o isne voisenv oisen vosien vseivn seinvs eivno sienvosien osienv" +
                "dfldkf alskdf lskdjf l;aksdjf l;kdjf;lskdjfl;ksdj flkdj flaskdjf lkdjf l;skdfj lskdfj ;alskdjf lksdjf l;ksdjf lsdkjf lskdjf lsakdjf sadkfj sa;dl" +
                "asldkjf laskdfj ;aslkdfj a;lsdkjf ;laskdjf l;askdjf l;skadjf lksdjf sdkjf ;lasdkfjls;akdf END");


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


        //etvGraveStone.setText(GameActivity.readFile(getActivity(), "graveyard.json"));

        return view;
    }

    /**
     * Will write text from journal fragment (journalText) to JSON file when player dies,
     * adding it to the graveyard.
     * @param journalText String which is the text from the journalFragment
     */
    public void addToGraveyardJSON(String journalText, Context context) {
        JSONObject journal = new JSONObject();
        try {
            journal.put("tombstone", journalText);
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
     * Reads graveyard.json file and adds it to the ExpandableListView (Graveyard)
     */
    private void readGraveyardJSON(Context context) {
        FileInputStream fis = null;
        String readText = null;

        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            readText = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears graveyard when button is pressed. Helps the player avoid clutter.
     */
    private void clearGraveyard() {

    }


}