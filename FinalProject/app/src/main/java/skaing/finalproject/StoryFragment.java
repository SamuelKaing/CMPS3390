package skaing.finalproject;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * This fragment class is the main fragment. It will handle game progression and
 * update the screen as decision are made.
 */
public class StoryFragment extends Fragment {
    private View view;
    private TextView text;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    private static String position = "positionCell";
    private static Bundle savedState = null;
    private static String savedPosition;
    private static String reqItem1, reqItem2, reqItem3, reqItem4, noItemPosition1, noItemPosition2,
            noItemPosition3, noItemPosition4;
    private playerDeath listener;

    /**
     * Interface that will allow communication between StoryFragment class and GameActivity
     */
    public interface playerDeath {
        void Restart();
    }

    /**
     * Override onCreateView() method.
     * Sets view, text, buttons, and calls getJSON() to begin story.
     *
     * @param inflater           LayoutInflater used to inflate view
     * @param container          ViewGroup (not used)
     * @param savedInstanceState Bundle. (onSaveInstanceState method not called for some reason.
     *                           Had to manually create alternate method (savedPositionState()).
     * @return View which is the storyFragment's view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_story, container, false);

        // Checks savedState bundle to make sure player position is not lost on fragment change
        if (savedState != null) {
            position = savedState.getString("savedPosition");
        } else {
            savedPosition = position;
        }

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

        // Starts story
        try {
            getJSON(position);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * Parses data from story.json to set text and button text.
     * Sets savedPosition, which is used to save the state when fragment is changed.
     * Recalls itself when button is pressed to progress story.
     * Begins the process of restarting the game if player dies.
     *
     * @param position String that determines the next position of the player
     * @throws IOException   for when getString("") is called
     * @throws JSONException for when getJSON("") is called
     */
    public void getJSON(String position) throws IOException, JSONException {
        // Resets strings of text, buttons, reqItems, and noItemPositions for recursive calls
        resetStrings();

        InputStream inputStream = getActivity().getAssets().open("story.json");

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        String jsonString = new String(buffer, StandardCharsets.UTF_8);
        JSONObject jsonObj = new JSONObject(jsonString);

        JSONArray jArray = jsonObj.getJSONArray(position);

        // Reads jArray and parses data
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject info = jArray.getJSONObject(i);
            JSONArray choice1 = info.getJSONArray("choice1");
            JSONArray choice2 = info.getJSONArray("choice2");
            JSONArray choice3 = info.getJSONArray("choice3");
            JSONArray choice4 = info.getJSONArray("choice4");

            // Sets the text of the story fragment
            text.setText(info.getString("text"));

            // If statements check to see if there is anything inside choice JSONArrays
            // Method of parsing is the same for each choice array
            if (choice1 != null && choice1.length() > 0) {

                // Loop to check if choices have any required items & changes position
                for (int ii = 0; ii < choice1.length(); ii++) {
                    JSONObject choiceInfo = choice1.getJSONObject(ii);
                    JSONArray requirements = choiceInfo.getJSONArray("required");

                    // That's right, another array and for loop to check if there's anything inside requirements JSONArray (i.e reqItem and noItemPosition)
                    // This is to check if there are any required items for the choice.
                    // It will set the position the player will go to next if they do not have the required item in noItemPosition.
                    if (requirements != null && requirements.length() > 0) {
                        for (int iii = 0; iii < requirements.length(); iii++) {
                            JSONObject reqInfo = requirements.getJSONObject(iii);

                            reqItem1 = reqInfo.getString("item");
                            noItemPosition1 = reqInfo.getString("nextPosition");
                        }
                    }

                    // Sets button text
                    btnChoice1.setText(choiceInfo.getString("btnText"));

                    // Changes position when button is clicked
                    btnChoice1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String positionNext;

                            // Only on button 1, this check to see if player died and if the game should restart
                            // It first makes sure to store the last text onscreen to journal
                            // Then it resets the savedPosition to the beginning
                            // Last it calls Restart(), which is located in GameActivity.
                            if (btnChoice1.getText().equals("GAME OVER")) {
                                try {
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                savedPosition = "positionCell";
                                listener.Restart();
                                try {
                                    getJSON(savedPosition);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {

                                // If player has the required item, remove item from inventory and set positions
                                // Has the same function if there is no required item
                                // Function getJSON() is recalled here
                                if (GameActivity.checkInventory(reqItem1) || reqItem1.equals("")) {
                                    try {
                                        // Removes required item once used
                                        if (!reqItem1.equals("")) {
                                            GameActivity.removeFromInventory(reqItem1);
                                        }

                                        // Adds item to inventory if there is a pick-up
                                        if (!choiceInfo.getString("pickUp").equals("")) {
                                            GameActivity.addToInventory(choiceInfo.getString("pickUp"));
                                        }

                                        // Sets next position and saves it to savedPosition
                                        positionNext = choiceInfo.getString("nextPosition");
                                        savedPosition = positionNext;

                                        // Saves text and btnText to journal when choice is selected
                                        GameActivity.saveToJournal(info.getString("text"), getActivity());
                                        GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());

                                        // Recalls getJSON() to next position when button is clicked
                                        getJSON(positionNext);
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                // If they do not have the required item, change positionNext to noItemPosition and recall getJSON() with that
                                else {
                                    positionNext = noItemPosition1;
                                    savedPosition = positionNext;
                                    try {
                                        GameActivity.saveToJournal(info.getString("text"), getActivity());
                                        GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                        getJSON(positionNext);
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                }
            }
            if (choice2 != null && choice2.length() > 0) {
                for (int ii = 0; ii < choice2.length(); ii++) {
                    JSONObject choiceInfo = choice2.getJSONObject(ii);
                    JSONArray requirements = choiceInfo.getJSONArray("required");

                    if (requirements != null && requirements.length() > 0) {
                        for (int iii = 0; iii < requirements.length(); iii++) {
                            JSONObject reqInfo = requirements.getJSONObject(iii);

                            reqItem2 = reqInfo.getString("item");
                            noItemPosition2 = reqInfo.getString("nextPosition");
                        }
                    }

                    btnChoice2.setText(choiceInfo.getString("btnText"));

                    btnChoice2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String positionNext;
                            if (GameActivity.checkInventory(reqItem2) || reqItem2.equals("")) {
                                try {
                                    if (!reqItem2.equals("")) {
                                        GameActivity.removeFromInventory(reqItem2);
                                    }
                                    if (!choiceInfo.getString("pickUp").equals("")) {
                                        GameActivity.addToInventory(choiceInfo.getString("pickUp"));
                                    }
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    positionNext = choiceInfo.getString("nextPosition");
                                    savedPosition = positionNext;
                                    getJSON(positionNext);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                positionNext = noItemPosition2;
                                savedPosition = positionNext;
                                try {
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    getJSON(positionNext);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
            if (choice3 != null && choice3.length() > 0) {
                for (int ii = 0; ii < choice3.length(); ii++) {
                    JSONObject choiceInfo = choice3.getJSONObject(ii);

                    JSONArray requirements = choiceInfo.getJSONArray("required");
                    if (requirements != null && requirements.length() > 0) {
                        for (int iii = 0; iii < requirements.length(); iii++) {
                            JSONObject reqInfo = requirements.getJSONObject(iii);

                            reqItem3 = reqInfo.getString("item");
                            noItemPosition3 = reqInfo.getString("noItem");
                        }
                    }

                    //JSONArray required = choiceInfo.getJSONArray("required");
                    btnChoice3.setText(choiceInfo.getString("btnText"));

                    btnChoice3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String positionNext;
                            if (GameActivity.checkInventory(reqItem3) || reqItem3.equals("")) {
                                try {
                                    if (!reqItem3.equals("")) {
                                        GameActivity.removeFromInventory(reqItem3);
                                    }
                                    if (!choiceInfo.getString("pickUp").equals("")) {
                                        GameActivity.addToInventory(choiceInfo.getString("pickUp"));
                                    }
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    positionNext = choiceInfo.getString("nextPosition");
                                    savedPosition = positionNext;
                                    getJSON(positionNext);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                positionNext = noItemPosition3;
                                savedPosition = positionNext;
                                try {
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    getJSON(positionNext);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
            if (choice4 != null && choice4.length() > 0) {
                for (int ii = 0; ii < choice4.length(); ii++) {
                    JSONObject choiceInfo = choice4.getJSONObject(ii);

                    JSONArray requirements = choiceInfo.getJSONArray("required");
                    if (requirements != null && requirements.length() > 0) {
                        for (int iii = 0; iii < requirements.length(); iii++) {
                            JSONObject reqInfo = requirements.getJSONObject(iii);

                            reqItem4 = reqInfo.getString("item");
                            noItemPosition4 = reqInfo.getString("noItem");
                        }
                    }

                    //JSONArray required = choiceInfo.getJSONArray("required");
                    btnChoice4.setText(choiceInfo.getString("btnText"));

                    btnChoice4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String positionNext;
                            if (GameActivity.checkInventory(reqItem4) || reqItem4.equals("")) {
                                try {
                                    if (!reqItem4.equals("")) {
                                        GameActivity.removeFromInventory(reqItem4);
                                    }
                                    if (!choiceInfo.getString("pickUp").equals("")) {
                                        GameActivity.addToInventory(choiceInfo.getString("pickUp"));
                                    }
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    positionNext = choiceInfo.getString("nextPosition");
                                    savedPosition = positionNext;
                                    getJSON(positionNext);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                positionNext = noItemPosition4;
                                savedPosition = positionNext;
                                try {
                                    GameActivity.saveToJournal(info.getString("text"), getActivity());
                                    GameActivity.saveToJournal(choiceInfo.getString("btnText"), getActivity());
                                    getJSON(positionNext);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
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
    }

    /**
     * Resets strings to empty after each recurrence in getJSON()
     */
    public void resetStrings() {
        btnChoice1.setText("");
        btnChoice2.setText("");
        btnChoice3.setText("");
        btnChoice4.setText("");
        reqItem1 = "";
        reqItem2 = "";
        reqItem3 = "";
        reqItem4 = "";
        noItemPosition1 = "";
        noItemPosition2 = "";
        noItemPosition3 = "";
        noItemPosition4 = "";
    }

    /**
     * Makes sure to set Interface listener when fragment is created
     * @param context Context used to set listener
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof playerDeath) {
            listener = (playerDeath) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement playerDeath");
        }
    }

    /**
     * Sets listener to null when fragment is destroyed
     */
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Override onStop() method.
     * Instantiates savedState as a new bundle to be used
     * in savePositionState() method.
     */
    @Override
    public void onStop() {
        super.onStop();
        savedState = new Bundle();
        savePositionState(savedState);
    }

    /**
     * Saves variable nextPosition when fragment is changed.
     * Allows position to be set to nextPosition when created again,
     * essentially saving the state of the storyFragment.
     *
     * @param outState Bundle used to store nextPosition for later use
     */
    public void savePositionState(Bundle outState) {
        outState.putString("savedPosition", savedPosition);
    }
}