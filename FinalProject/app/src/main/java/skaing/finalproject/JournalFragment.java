package skaing.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Fragment class that will display player decisions up until their
 * current position
 */
public class JournalFragment extends Fragment {
    private View view;
    private ImageButton btnBack;
    private TextView journalText;
    private ScrollView svJournal;
    private Bundle savedState = null;
    private String savedJournal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_journal, container, false);
        journalText = view.findViewById(R.id.tvJournal);
        svJournal = view.findViewById(R.id.svJournal);

        // Checks to see if it is the first time fragment is opened
        // Recovers savedState if it is not
        if(savedState != null) {
            journalText.setText(savedState.getString("savedJournal"));
        } else {
            journalText.setText("");
        }

        // Back button that switches back to story fragment when pressed
        btnBack = view.findViewById(R.id.btnBackJournal);
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

        // Reads journal.txt file and sets journalText to it
        journalText.setText(GameActivity.readFile(getActivity()));

        // Saving journalText to make sure it doesn't disappear when fragment is changed
        savedJournal = journalText.toString();

        return view;
    }

    /**
     * Override onStop() method.
     * Instantiates savedState as a new bundle to be used
     * in saveJournalState() method.
     */
    @Override
    public void onStop() {
        super.onStop();
        savedState = new Bundle();
        saveJournalState(savedState);
    }

    /**
     * Saves variable written text when fragment is changed.
     * Allows position to be set to nextPosition when created again,
     * essentially saving the state of the storyFragment.
     *
     * @param outState Bundle used to store nextPosition for later use
     */
    public void saveJournalState(Bundle outState) {
        outState.putString("savedJournal", savedJournal);
    }

}