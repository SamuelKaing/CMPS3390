package skaing.a10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Main driver class for a10
 * @author Samuel Kaing
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private ListView listView;
    private EditText input;
    private TabLayout tabs;
    private ArrayList<ListItem> items;
    private ArrayAdapter<ListItem> itemsAdapter;
    private String selectedCollection = "Todo";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, items);
        listView.setAdapter(itemsAdapter);
        setupLongClickHandler();

        input = findViewById(R.id.etInput);
        input.setOnEditorActionListener(this);

        tabs = findViewById(R.id.tabLayout);
        setupTabClickListener();

        updateList();

    }

    /**
     * Updates the list shown on screen when a tab is selected
     */
    private void updateList() {
        showToast("Getting List", Toast.LENGTH_SHORT);
        Database.getList(db, selectedCollection, items, itemsAdapter);
    }

    /**
     * Clears lists when switching to new tab
     */
    private void setupTabClickListener() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Clear items
                items.clear();

                //Set the selected collection
                selectedCollection = tab.getText().toString();

                //Load Items from collection
                updateList();

                //Notify data set changed
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * This is called when a list item is held long.  Deletes items from list.
     */
    private void setupLongClickHandler() {
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ListItem tmpItem = items.remove(position);
            itemsAdapter.notifyDataSetChanged();
            Database.removeItem(db, selectedCollection, items, itemsAdapter, tmpItem);
            showToast("Removed Item", Toast.LENGTH_SHORT);
            return false;
        });
    }

    /**
     * Displays "message" for "length" amount of time when item is removed from list
     * @param message String which is the message that is shown on screen
     * @param length int which is how long the message is displayed
     */
    private void showToast(String message, int length) {
        Toast toast = Toast.makeText(this, message, length);
        toast.setGravity(Gravity.CENTER, 0, -30);
        toast.show();
    }

    /**
     * When text is input in the EditText, this allows it to be sent
     * and added to items array and clears the textbox to be used again
     * @param v TextView which is the view that is being changed
     * @param actionId int which is the id of the action being done
     * @param event KeyEvent which is done by the user
     * @return boolean that is true
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(event == null || event.getAction() == KeyEvent.ACTION_UP) {                  // if statement gets rid of extra empty input
            ListItem tmpItem = new ListItem(input.getText().toString());
            items.add(tmpItem);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
            Database.add(db, selectedCollection, tmpItem);
            showToast("Item Added", Toast.LENGTH_SHORT);
        }
        return true;
    }
}