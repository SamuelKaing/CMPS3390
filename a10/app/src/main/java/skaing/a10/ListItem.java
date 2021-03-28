package skaing.a10;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * ListItem class that names items and sets the time they were added to a list
 * @author Samuel Kaing
 * @version 1.0
 */
public class ListItem implements Serializable {
    private long dttm;
    private String item;

    /**
     * Sets item and the dttm
     * @param item String which is the item's name
     */
    public ListItem(String item) {
        this.item = item;
        dttm = System.nanoTime();
    }

    /**
     * Sets the item and dttm
     * @param dttm long which is the time the item was added
     * @param item String which is the item's name
     */
    public ListItem(long dttm, String item) {
        this.dttm = dttm;
        this.item = item;
    }

    @NonNull
    @Override
    public String toString() {
        return item;
    }

    public long getDttm() {
        return dttm;
    }

    public String getItem() {
        return item;
    }
}
