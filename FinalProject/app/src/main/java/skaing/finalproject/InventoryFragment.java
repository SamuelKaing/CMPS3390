package skaing.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Fragment class that will display the player's inventory when switched to
 */
public class InventoryFragment extends Fragment {
    private View view;
    private ImageButton btnBack;
    private ListView inventoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inventory, container, false);

        inventoryList = view.findViewById(R.id.lvInventory);
        btnBack = view.findViewById(R.id.btnBackInventory);

        // Sets back button to switch to story fragment
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, GameActivity.getInventory());
        inventoryList.setAdapter(arrayAdapter);

        return view;
    }

}