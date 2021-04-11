package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Background {
    private Bitmap background;
    private int screenX, screenY;

    private Paint paint = new Paint();
    private float dpi;

    private float x = 0f;
    private float y = 0f;

    /**
     * Sets screenX, screenY, and background. Builds background object
     * @param screenX int which is the X position of background
     * @param screenY int which is the Y position of background
     * @param res Resources which is used to set dpi
     */
    public Background(int screenX, int screenY, Resources res) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.background = BitmapFactory.decodeResource(res, R.mipmap.background);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
        this.dpi = res.getDisplayMetrics().densityDpi;
    }

    /**
     * Getter for X
     * @return float which is the x position
     */
    public float getX() {
        return x;
    }

    /**
     * Setter for X
     * @param x float which is the x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Getter for Y
     * @return float which is the y position
     */
    public float getY() {
        return y;
    }

    /**
     * Setter for Y
     * @param y float which is the x position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Scrolls background and resets Y position when it moves offscreen
     */
    public void update() {
        this.y += 0.006f * dpi;

        if(this.y > screenY) {
            this.y = -screenY;
        }
    }

    /**
     * Draws background
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.background, this.x, this.y, paint);
    }
}
