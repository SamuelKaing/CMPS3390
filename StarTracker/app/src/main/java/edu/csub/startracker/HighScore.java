package edu.csub.startracker;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class HighScore {
    private static final HighScore INSTANCE = new HighScore();
    private int curScore = 0;
    private int highScore = 0;
    private String name = "Player 1";
    private FirebaseFirestore db;

    private HighScore() {
        db = FirebaseFirestore.getInstance();
    }

    public static HighScore getInstance() {
        return INSTANCE;
    }

    /**
     * Adds score when laser hits, updates highScore if it is beaten
     * @param score int which is the points to add to the score
     */
    public void addScore(int score) {
        curScore += score;
        if(curScore > highScore) {
            highScore = curScore;
        }
    }

    /**
     * Getter for curScore
     * @return int curScore
     */
    public int getCurScore() {
        return curScore;
    }

    /**
     * Getter for highScore
     * @return int highScore
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Resets curScore to 0
     */
    public void resetCurScore() {
        curScore = 0;
    }

    /**
     * Sets playerName
     * @param playerName String that represents player
     */
    public void setPlayerName(String playerName) {
        name = playerName;
    }

    /**
     * Getter for player name
     * @return String playerName
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * Gets highScore from Firestore Database
     * @param howMany int which is how many top scores to get
     * @param highscores ListView
     * @param context Context
     */
    public void getHighScores(int howMany, ListView highscores, Context context) {
        ArrayList<String> topScores = new ArrayList<>();

        db.collection("HighScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(howMany)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                String tmpString = String.format("%s: %s", doc.getId(),
                                        doc.get("score"));
                                topScores.add(tmpString);
                                Log.d("SCORE", tmpString);
                            }
                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1, topScores);
                            highscores.setAdapter(itemsAdapter);
                        }
                    }
                });
    }

    /**
     * Posts highScore to database
     */
    public void postHighScore() {
        Map<String, Integer> hs = new HashMap<>();
        hs.put("score", highScore);

        db.collection("HighScore").document(name)
                .set(hs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("data", name + "'s score was set");
                    }
                });
    }

}
