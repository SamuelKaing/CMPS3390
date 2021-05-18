package skaing.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;

public class JournalFragment extends Fragment {
    private View view;
    private ImageButton btnBack;
    private TextView journalText;
    private Bundle savedState = null;
    private String savedJournal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_journal, container, false);
        journalText = view.findViewById(R.id.tvJournal);

        if(savedState != null) {
            journalText.setText(savedState.getString("savedJournal"));
        } else {
            journalText.setText("");
        }

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

        GameActivity.readFile(journalText, getActivity());
        savedJournal = journalText.toString();

        return view;
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
     * Saves variable written text when fragment is changed.
     * Allows position to be set to nextPosition when created again,
     * essentially saving the state of the storyFragment.
     *
     * @param outState Bundle used to store nextPosition for later use
     */
    public void savePositionState(Bundle outState) {
        outState.putString("savedJournal", savedJournal);
    }

}