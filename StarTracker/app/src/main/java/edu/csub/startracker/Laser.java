package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Laser {

    private float x, y;
    private Bitmap laser;
    private float dpi;
    private Paint paint = new Paint();

    /**
     * Sets laser image and dpi
     * @param res Resources which get the dpi
     */
    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.mipmap.bullet);
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
    public float getHeight() {
        return laser.getHeight();
    }

    /**
     * Getter for x position
     * @return float which is the x position of the laser
     */
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

    /**
     * Setter for y position
     * @param y float which is the y position of the laser
     */
    public void setY(float y) {
        this.y = y;
    }
}
