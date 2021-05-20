package skaing.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

public class GameActivity extends AppCompatActivity implements StoryFragment.playerDeath {
    public static Fragment storyFragment, graveyardFragment;
    public static LinkedList<String> inventory = new LinkedList<String>();
    private JournalFragment journalFragment;
    private accessGraveyard listener = new GraveyardFragment();
    private final static String FILE_NAME = "journal.txt";

    /**
     * Interface that will allow communication between GameActivity and GraveyardFragment class
     */
    public interface accessGraveyard {
        void addToGraveyardJSON(String journalText, Context context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        clearJournalText(this);

        // Creates new fragments of each fragment class
        Fragment mapFragment = new MapFragment();
        journalFragment = new JournalFragment();
        graveyardFragment = new GraveyardFragment();
        storyFragment = new StoryFragment();

        // Sets UI buttons
        ImageButton btnJournal = findViewById(R.id.btnJournal);
        ImageButton btnMap = findViewById(R.id.btnMap);
        ImageButton btnGraveyard = findViewById(R.id.btnGraveyard);

        // Sets default fragment as Story Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, storyFragment)
                    .commit();
        }

        // Switches Fragment when Map Button is clicked
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.flFragment, mapFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Switches Fragment when Journal Button is clicked
        btnJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.flFragment, journalFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Switches Fragment when Graveyard Button is clicked
        btnGraveyard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.flFragment, graveyardFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public static LinkedList<String> getInventory() {
        return inventory;
    }

    /**
     * Checks inventory for item when called
     *
     * @param item String that is being check for in Linked List
     * @return boolean that is based on if the item is found or not
     */
    public static boolean checkInventory(String item) {
        boolean itemFound = false;
        Iterator<String> iterator = inventory.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                itemFound = true;
            }
        }
        return itemFound;
    }

    public static void addToInventory(String item) {
        inventory.add(item);
    }

    /**
     * Removes item from inventory Linked List
     *
     * @param item String that is being removed from list
     */
    public static void removeFromInventory(String item) {
        inventory.remove(item);
    }

    public static void clearInventory() {
        inventory.clear();
    }

    /**
     * Saves text and button text once choice is made and adds them to a text file
     * @param text    String that is the text being added to the file
     * @param context Context used to open the file
     */
    public static void saveToJournal(String text, Context context) {
        String spacer = "\n\n";
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput("journal.txt", Context.MODE_PRIVATE | Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.write(spacer.getBytes());
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
     * This will read String FILE_NAME text file to display on Journal Fragment
     * @param context Context used to open the file
     */
    public static String readFile(Context context) {
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return readText;
    }

    /**
     * This will clear String FILE_NAME text file when game is started
     */
    private void clearJournalText(Context context) {
        String empty = "";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(empty.getBytes());
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
     * Overwritten onStop() to act as a GAME OVER
     * Will empty players inventory. Journal will empty automatically
     */
    @Override
    protected void onStop() {
        super.onStop();
        clearInventory();
    }

    @Override
    public void prepRestart() {

        // Get journal text and store
        String storedText = readFile(this);

        // Clear journal text
        clearJournalText(this);

        // Clear player inventory
        clearInventory();

        // Calls method in GraveyardFragment class to store string in JSON file
        listener.addToGraveyardJSON(storedText, this);
    }

}