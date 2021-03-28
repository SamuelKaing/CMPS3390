package skaing.a10;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Database class that adds and removes item to the Firestore database
 * @author Samuel Kaing
 * @version 1.0
 */
public class Database {

    /**
     * Adds the "item" into the collection "selectedCollection" within the db
     * @param db FirebaseFirestore which holds data on all items from each tab
     * @param selectedCollection String which is the tab that is being added to
     * @param item ListItem which is the item being added to the list
     */
    public static void add(FirebaseFirestore db, String selectedCollection, ListItem item) {
        Map<String, Object> listItem = new HashMap<>();
        listItem.put("item", item);

        db.collection(selectedCollection)
                .add(listItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DATABASE", "Item Added: " + documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to add item: " + Arrays.toString(e.getStackTrace()));
                    }
                });

    }

    /**
     * Gets lists when switching tabs and sorts by dttm
     * @param db FirebaseFirestore which is the database that holds the lists
     * @param selectedCollection String which is the tab whose list is being gotten
     * @param items ArrayList which is the item that is being gotten
     * @param itemsAdapter ArrayAdapter which is being used to notify the change
     */
    public static void getList(FirebaseFirestore db, String selectedCollection
            , ArrayList<ListItem> items, ArrayAdapter<ListItem> itemsAdapter) {

        db.collection(selectedCollection)
                .orderBy("item.dttm")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                long dttm = doc.getLong("item.dttm");
                                String item = doc.getString("item.item");
                                items.add(new ListItem(dttm, item));
                            }
                            itemsAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to get list: " + Arrays.toString(e.getStackTrace()));
                    }
                });

    }

    // Removes item from Firestore database

    /**
     * Removes item from the Firestore database
     * @param db FirebaseFirestore which is the database from which the item is being removed
     * @param selectedCollection String which is the tab from which the item is being removed
     * @param items ArrayList which is the item being removed
     * @param itemAdapter ArrayAdapter which is the means of notifying the change
     * @param removedItem ListItem which is the item being removed
     */
    public static void removeItem(FirebaseFirestore db, String selectedCollection
            , ArrayList<ListItem> items, ArrayAdapter<ListItem> itemAdapter, ListItem removedItem) {
        db.collection(selectedCollection).whereEqualTo("item.dttm", removedItem.getDttm())
                .whereEqualTo("item.item", removedItem.getItem())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots) {
                            db.collection(selectedCollection).document(doc.getId()).delete();
                        }
                    }
                });
    }

}
