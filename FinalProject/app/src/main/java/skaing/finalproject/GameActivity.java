package skaing.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.io.InputStream;

public class GameActivity extends AppCompatActivity {
    private ImageButton btnGraveyard;
    public static Fragment storyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Player player = new Player();

        // Creates new fragments of each fragment class
        Fragment mapFragment = new MapFragment();
        Fragment journalFragment = new JournalFragment();
        Fragment graveyardFragment = new GraveyardFragment();
        storyFragment = new StoryFragment();

        // Sets UI buttons
        ImageButton btnJournal = findViewById(R.id.btnJournal);
        ImageButton btnMap = findViewById(R.id.btnMap);
        btnGraveyard = findViewById(R.id.btnGraveyard);

        // Sets default fragment as Story Fragment
        if(savedInstanceState == null) {
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

    public static void gameOver() {
    }

}