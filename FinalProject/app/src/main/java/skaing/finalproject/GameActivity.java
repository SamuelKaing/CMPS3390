package skaing.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class GameActivity extends AppCompatActivity {
    private ImageButton btnGraveyard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton btnJournal = findViewById(R.id.btnJournal);
        ImageButton btnMap = findViewById(R.id.btnMap);

        // Creates new fragment of mapFragment Java class
        Fragment mapFragment = new MapFragment();

        btnJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.flFragment, mapFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}