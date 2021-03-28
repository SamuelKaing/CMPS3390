package skaing.a10;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ListItem implements Serializable {
    private long dttm;
    private String item;

    public ListItem(String item) {
        this.item = item;
        dttm = System.nanoTime();
    }

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
