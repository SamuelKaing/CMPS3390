package skaing.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class GraveyardFragment extends Fragment implements GameActivity.accessGraveyard {
    private View view;
    private ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_graveyard, container, false);

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
     * Will write journalText to JSON file when player dies, adding it to the graveyard.
     * @param journalText String which is the text from the journalFragment
     */
    public void addToGraveyardJSON(String journalText) {

    }

    private void readGraveyardJSON() {
    }

}