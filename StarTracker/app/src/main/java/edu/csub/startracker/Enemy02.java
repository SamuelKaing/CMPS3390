package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Enemy02 implements GameObject {

    private float x, y, ySpeed;
    private float health = 100;
    private final Bitmap enemy;
    private final Bitmap enemy_fast;
    private Bitmap curImage;
    private final int screenWidth, screenHeight, dpi;
    private final int width, height;
    private Paint paint = new Paint();
    private int frameTick, launchTick;

    /**
     * Sets enemy images, curImage, width, height, dpi, screen dimensions, and speed of enemy ship
     * @param res Resource used to get images
     * @param x float that is the x position
     * @param y float that is the y position
     */
    public Enemy02(Resources res, float x, float y) {
        dpi = res.getDisplayMetrics().densityDpi;
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;

        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy02);
        enemy_fast = BitmapFactory.decodeResource(res, R.mipmap.enemy02_fast);
        curImage = enemy;

        width = curImage.getWidth();
        height = curImage.getHeight();

        this.x = x;
        this.y = y;

        this.ySpeed = 0.01f * dpi;

        launchTick = new Random().nextInt(120-30) + 30;
    }

    /**
     * Updates the speed of enemy as it goes down
     */
    @Override
    public void update() {
        // Start slow wait some time
        frameTick++;

        if(frameTick == launchTick) {
            // switch images and go fast
            curImage = enemy_fast;
            ySpeed *= 4f;
        }

        // move on the y
        y += ySpeed;

    }

    /**
     * Draws image of the enemy
     * @param canvas Canvas used to draw ship
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(curImage, x, y, paint);
    }

    /**
     * Getter for x
     * @return float that is the x position
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Getter for y
     * @return float that is the y position
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Getter for width
     * @return float that is the width of the enemy
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Getter for height
     * @return float that is the height of the enemy
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Returns true or false depending if the enemy's health is above 0
     * @return boolean that is if the enemy is alive or not
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Gets enemy's health
     * @return float that is the enemy's health
     */
    @Override
    public float getHealth() {
        return health;
    }

    /**
     * Subtracts enemy's health based on damage taken
     * @param damage float that is the amount of damage taken
     * @return float that is the enemy's health after damage is applied
     */
    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    /**
     * Adds health to enemy's health based on repairAmount
     * @param repairAmount float that is the amount healed by the enemy
     * @return float that is the enemy's health after being healed
     */
    @Override
    public float addHealth(float repairAmount) {
        return health += repairAmount;
    }
}
