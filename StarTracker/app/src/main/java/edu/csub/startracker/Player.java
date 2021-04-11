package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private float x, y, prevX, prevY;
    private final Bitmap playerImg;
    private final Bitmap playerLeft;
    private final Bitmap playerRight;
    private Bitmap curImage;
    private Paint paint = new Paint();
    private final float dpi;
    private int frameTicks = 0, shotTicks = 0;
    private final Resources res;

    ArrayList<Laser> lasers = new ArrayList<>();

    // Puts sets images for different player movements, sets default player position
    public Player(Resources res) {
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
        playerLeft = BitmapFactory.decodeResource(res, R.mipmap.player_left);
        playerRight = BitmapFactory.decodeResource(res, R.mipmap.player_right);

        curImage = playerImg;

        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;

        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);


    }

    // Moves ship where player is touching with touchX and touchY, also displays ship turning image if moved
    public void update(int touchX, int touchY) {
        if(touchX > 0 && touchY > 0) {
            this.x = touchX - (playerImg.getWidth() / 2f);
            this.y = touchY - (playerImg.getHeight() * 2f);
        }

        if(Math.abs(x - prevX) < 0.04 * dpi) {
            frameTicks++;
        } else {
            frameTicks = 0;
        }

        if(this.x < prevX -0.04 * dpi) {
            curImage = playerLeft;
        } else if (this.x > prevX + 0.04 * dpi) {
            curImage = playerRight;
        } else if (frameTicks > 6) {
            curImage = playerImg;
        }

        prevX = x;
        prevY = y;

        //Increase shotTicks
        shotTicks++;

        // see if we need to shoot
        if(shotTicks >= 15) {
            // shoot here
            Laser tmp = new Laser(this.res);
            tmp.setX(x + (playerImg.getWidth() / 2f) - tmp.getMidX());
            tmp.setY(y - tmp.getHeight() / 2f);
            lasers.add(tmp);

            // reset shotTicks
            shotTicks = 0;
        }

        // remove lasers offscreen
        for(Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext();) {
            Laser laser = iterator.next();
            if(!laser.isOnScreen()) {
                iterator.remove();
            }
        }

        // update all lasers
        for(Laser laser :lasers) {
            laser.update();
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(curImage, this.x, this.y, this.paint);

        // draw all lasers
        for(Laser laser : lasers) {
            laser.draw(canvas);
        }
    }
}
