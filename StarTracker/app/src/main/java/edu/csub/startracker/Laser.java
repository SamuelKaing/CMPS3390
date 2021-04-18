package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Laser implements GameObject {

    private float x, y;
    private Bitmap laser;
    private float dpi;
    private Paint paint = new Paint();
    private float health = 100f;
    private final int width, height;

    /**
     * Sets laser image and dpi
     * @param res Resources which get the dpi
     */
    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.mipmap.bullet);
        width = laser.getWidth();
        height = laser.getHeight();
        dpi = res.getDisplayMetrics().densityDpi;
    }

    /**
     * Checks if bullet is onscreen
     * @return boolean that represents if bullet is onscreen
     */
    public boolean isOnScreen() {
        return !(y < getHeight());
    }

    /**
     * Updates laser y position as it moves
     */
    public void update() {
        y -= 0.1f * dpi;
    }

    /**
     * Draws laser
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(laser, this.x, this.y, this.paint);
    }

    /**
     * Gets mid x position of laser
     * @return float which is the mid x position of the laser
     */
    public float getMidX() {
        return laser.getWidth() / 2f;
    }

    /**
     * Getter for laser's height
     * @return float which is the height of the laser
     */
    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    @Override
    public float addHealth(float repairAmount) {
        return health += repairAmount;
    }

    /**
     * Getter for x position
     * @return float which is the x position of the laser
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Setter for x position
     * @param x float which is the x position of the laser
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Getter for y position
     * @return float which is the y position of the laser
     */
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Setter for y position
     * @param y float which is the y position of the laser
     */
    public void setY(float y) {
        this.y = y;
    }
}
