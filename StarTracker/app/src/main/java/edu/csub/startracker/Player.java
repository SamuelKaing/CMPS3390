package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

public class Player implements GameObject {
    private float x, y, prevX, prevY;
    private final Bitmap playerImg;
    private final Bitmap playerLeft;
    private final Bitmap playerRight;
    private Bitmap curImage;
    private Paint paint = new Paint();
    private final float dpi;
    private int frameTicks = 0, shotTicks = 0;
    private final Resources res;
    private final int width, height;

    ArrayList<Laser> lasers = new ArrayList<>();
    private float health = 100f;

    /**
     * Sets images for different player movements and default player position
     * @param res Resources used to get images and set dpi
     */
    public Player(Resources res) {
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
        playerLeft = BitmapFactory.decodeResource(res, R.mipmap.player_left);
        playerRight = BitmapFactory.decodeResource(res, R.mipmap.player_right);

        curImage = playerImg;
        width = curImage.getWidth();
        height = curImage.getHeight();

        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;

        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);


    }

    /**
     * Updates player position based on the x and y of where they touched
     * @param touchX int that is the x position touched
     * @param touchY int that is the y position touched
     */
    public void updateTouch(int touchX, int touchY) {
        if(touchX > 0 && touchY > 0) {
            this.x = touchX - (playerImg.getWidth() / 2f);
            this.y = touchY - (playerImg.getHeight() * 2f);
        }
    }

    /**
     * Moves ship where player is touching with touchX and touchY, also displays ship turning image if moved
     * @param touchX int which is the x position the player touched
     * @param touchY int which is the y position the player touched
     */
    @Override
    public void update() {
        if(health <= 0) return;
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
        if(shotTicks >= 11) {
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
            if(!laser.isOnScreen() || !laser.isAlive()) {
                iterator.remove();
            }
        }

        // update all lasers
        for(Laser laser :lasers) {
            laser.update();
        }
    }

    /**
     * Draws Player
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        if(health <= 0) return;
        canvas.drawBitmap(curImage, this.x, this.y, this.paint);

        // draw all lasers
        for(Laser laser : lasers) {
            laser.draw(canvas);
        }
    }

    /**
     * Getter for x
     * @return float that is x position
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Getter for y
     * @return float that is y position
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Getter for width
     * @return float that is the width
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Getter for height
     * @return float that is height
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Returns true or false depending if the player's health is above 0
     * @return boolean that is if the player is alive or not
     */
    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    /**
     * Getter for health
     * @return float that is the player's health
     */
    @Override
    public float getHealth() {
        return health;
    }

    /**
     * Subtracts player health based on damage taken
     * @param damage float that is the amount of damage taken
     * @return float that is the player's health after damage is applied
     */
    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    /**
     * Adds health to player's health based on repairAmount
     * @param repairAmount float that is the amount healed by the player
     * @return float that is the player's health after being healed
     */
    @Override
    public float addHealth(float repairAmount) {
        return health += repairAmount;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }
}
