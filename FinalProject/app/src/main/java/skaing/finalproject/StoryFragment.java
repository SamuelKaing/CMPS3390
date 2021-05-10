package skaing.finalproject;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class StoryFragment extends Fragment {
    private View view;
    private TextView text;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_story, container, false);

        text = view.findViewById(R.id.txtStory);

        // Sets buttons for choices and underlines text
        btnChoice1 = view.findViewById(R.id.btnChoice1);
        btnChoice1.setPaintFlags(btnChoice1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnChoice2 = view.findViewById(R.id.btnChoice2);
        btnChoice2.setPaintFlags(btnChoice2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnChoice3 = view.findViewById(R.id.btnChoice3);
        btnChoice3.setPaintFlags(btnChoice3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnChoice4 = view.findViewById(R.id.btnChoice4);
        btnChoice4.setPaintFlags(btnChoice4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        try {
            getJSON("positionCell");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * Parses data from story.json to set text and button text.  Makes my brain hurt.
     * Recalls itself when button is pressed to change position
     * @param position String that determines the next position of the player
     * @throws IOException for when getString("") is called
     * @throws JSONException for when getJSON("") is called
     */
    public void getJSON(String position) throws IOException, JSONException {
        btnChoice1.setText("");
        btnChoice2.setText("");
        btnChoice3.setText("");
        btnChoice4.setText("");

        String jsonString;

        InputStream inputStream = getActivity().getAssets().open("story.json");

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        jsonString = new String(buffer, StandardCharsets.UTF_8);
        JSONObject jsonObj = new JSONObject(jsonString);

        JSONArray jArray = jsonObj.getJSONArray(position);

            // Reads jArray and parses data
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject info = jArray.getJSONObject(i);
                JSONArray choice1 = info.getJSONArray("choice1");
                JSONArray choice2 = info.getJSONArray("choice2");
                JSONArray choice3 = info.getJSONArray("choice3");
                JSONArray choice4 = info.getJSONArray("choice4");

                text.setText(info.getString("text"));

                // If statements check to see if there is anything inside choice arrays
                // Method of parsing is the same for each choice array
                if (choice1 != null && choice1.length() > 0) {
                    // Loop to check if choices have any required items & changes position
                    for (int ii = 0; ii < choice1.length(); ii++) {
                        JSONObject choiceInfo = choice1.getJSONObject(ii);
                        //JSONArray required = choiceInfo.getJSONArray("required");

                        // Sets button text
                        btnChoice1.setText(choiceInfo.getString("btnText"));

                        // Changes position when button is clicked
                        btnChoice1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String positionNext = choiceInfo.getString("nextPosition");
                                    getJSON(positionNext);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                if (choice2 != null && choice2.length() > 0) {
                    for (int ii = 0; ii < choice2.length(); ii++) {
                        JSONObject choiceInfo = choice2.getJSONObject(ii);
                        //JSONArray required = choiceInfo.getJSONArray("required");
                        btnChoice2.setText(choiceInfo.getString("btnText"));

                        btnChoice2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String positionNext = choiceInfo.getString("nextPosition");
                                    getJSON(positionNext);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                if (choice3 != null && choice3.length() > 0) {
                    for (int ii = 0; ii < choice3.length(); ii++) {
                        JSONObject choiceInfo = choice3.getJSONObject(ii);
                        //JSONArray required = choiceInfo.getJSONArray("required");
                        btnChoice3.setText(choiceInfo.getString("btnText"));

                        btnChoice3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String positionNext = choiceInfo.getString("nextPosition");
                                    getJSON(positionNext);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                if (choice4 != null && choice4.length() > 0) {
                    for (int ii = 0; ii < choice4.length(); ii++) {
                        JSONObject choiceInfo = choice4.getJSONObject(ii);
                        //JSONArray required = choiceInfo.getJSONArray("required");
                        btnChoice4.setText(choiceInfo.getString("btnText"));

                        btnChoice4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String positionNext = choiceInfo.getString("nextPosition");
                                    getJSON(positionNext);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

                // Properly capitalizes button text
                btnChoice1.setTransformationMethod(null);
                btnChoice2.setTransformationMethod(null);
                btnChoice3.setTransformationMethod(null);
                btnChoice4.setTransformationMethod(null);

            }

            Log.d("HEADS UP: ", "MADE IT TO END OF CLASS");


    }

}