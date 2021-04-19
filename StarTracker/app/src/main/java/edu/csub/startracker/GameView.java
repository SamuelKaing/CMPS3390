package edu.csub.startracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private Thread thread;
    private int touchX, touchY;
    private ArrayList<Laser> lasers;
    private ArrayList<GameObject> enemies;
    private GameActivity gameActivity;

    private final Player player;

    private EnemySpawner spawner;
    private final float screenWidth, screenHeight;
    private Paint textPaint = new Paint();

    /**
     * GameView which shows
     * @param context Context
     * @param screenX int which is X position of background
     * @param screenY int which is Y position of background
     */
    public GameView(GameActivity context, int screenX, int screenY) {
        super(context);

        Resources res = getResources();
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(screenWidth * 0.1f);

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.setY(screenY);

        player = new Player(res);
        spawner = new EnemySpawner(res);

        lasers = player.getLasers();
        enemies = spawner.getEnemies();

        gameActivity = context;

    }

    /**
     * Updates touchX and touchY when player touches screen
     * @param event MotionEvent representing playing moving
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int)event.getX();
        touchY = (int)event.getY();

        return true;
    }

    /**
     * Main run function when game is played
     */
    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    /**
     * Updates background as it scrolls and player position on touch
     */
    private void update() {
        background1.update();
        background2.update();
        player.updateTouch(touchX, touchY);
        player.update();
        spawner.update();
        checkAllCollision();
        checkEnemiesOffScreen();
    }

    /**
     * Makes ships that go off screen past the player cause a game over
     */
    private void checkEnemiesOffScreen() {
        for(GameObject go : enemies) {
            if(go.getY() > screenHeight) {
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    /**
     *Checks collisions of enemy ships with lasers as well as enemy ships with the player ship
     */
    private void checkAllCollision() {
        for(Laser laser : lasers) {
            for(GameObject go : enemies) {
                if(checkCollision(laser, go)) {
                    laser.takeDamage(100);
                    go.takeDamage(25);
                }
            }
        }

        for(GameObject go : enemies) {
            if(checkCollision(player, go)) {
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    /**
     * Checks collision with enemies and lasers
     * @param g1 GameObject that is the laser
     * @param g2 GameObject that is the enemy ship
     * @return boolean that returns if a ship has been hit or not
     */
    private boolean checkCollision(GameObject g1, GameObject g2) {
        return g1.getX() < g2.getX() + g2.getWidth() &&
                g1.getX() + g1.getWidth() > g2.getX() &&
                g1.getY() < g2.getY() + g2.getHeight() &&
                g1.getY() + g1.getHeight() > g2.getY();
    }

    /**
     * Draws background and player
     */
    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            background1.draw(canvas);
            background2.draw(canvas);

            if(!player.isAlive()) {
                canvas.drawText("GAME OVER", screenWidth / 4f, screenHeight / 2f, textPaint);
            }
            player.draw(canvas);
            spawner.draw(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Controls framerate
     */
    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets isPlaying to false when paused
     */
    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets isPlaying to true when resumed
     */
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
}
