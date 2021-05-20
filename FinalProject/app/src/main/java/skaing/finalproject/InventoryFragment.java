package skaing.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {
    private View view;
    private ImageButton btnBack;
    private ListView inventoryList;
    private ArrayList<String> savedInventory;
    private Bundle savedState = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inventory, container, false);

        if (savedState != null) {
            GameActivity.setInventory(savedState.getStringArrayList("savedInventory"));
        }

        inventoryList = view.findViewById(R.id.lvInventory);
        btnBack = view.findViewById(R.id.btnBackInventory);
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

        // Creates adapter to display list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, GameActivity.getInventory());

        inventoryList.setAdapter(arrayAdapter);

        savedInventory = GameActivity.getInventory();

        return view;
    }

    /**
     * Override onStop() method.
     * Instantiates savedState as a new bundle to be used
     * in saveInventoryState() method.
     */
    @Override
    public void onStop() {
        super.onStop();
        savedState = new Bundle();
        saveInventoryState(savedState);
    }

    /**
     * Saves ArrayList for inventory when app is closed
     *
     * @param outState Bundle used to store nextPosition for later use
     */
    public void saveInventoryState(Bundle outState) {
        outState.putStringArrayList("savedInventory", savedInventory);
    }

}