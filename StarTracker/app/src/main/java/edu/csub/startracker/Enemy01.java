package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy01 implements GameObject {
    private final float dpi;
    private float x, y, ySpeed;
    private final float width, height;
    private float health = 100f;
    private final Bitmap enemy, enemy_left, enemy_right;
    private Bitmap curImage;
    private int screenWidth, screenHeight;
    private Paint paint = new Paint();

    /**
     * Sets enemy images, curImage, width, height, dpi, screen dimensions, and speed of enemy ship
     * @param res Resource used to get images
     * @param x float that is the x position
     * @param y float that is the y position
     */
    public Enemy01(Resources res, float x, float y) {
        this.x = x;
        this.y = y;

        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy01);
        enemy_left = BitmapFactory.decodeResource(res, R.mipmap.enemy01_left);
        enemy_right = BitmapFactory.decodeResource(res, R.mipmap.enemy01_right);
        curImage = enemy;
        width = curImage.getWidth();
        height = curImage.getHeight();

        dpi = res.getDisplayMetrics().densityDpi;
        screenHeight = res.getDisplayMetrics().heightPixels;
        screenWidth = res.getDisplayMetrics().widthPixels;

        ySpeed = 0.02f * dpi;

    }

    /**
     * Updates the sway of enemy as it goes down
     */
    @Override
    public void update() {
        float xOff = (float) (0.01f * screenWidth * Math.sin(y / (0.05f * screenHeight)));
        x += xOff;
        curImage = xOff > 0 ? enemy_left: enemy_right;
        if(Math.abs(xOff) < 1.25) curImage = enemy;


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
        return health > 0f;
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
