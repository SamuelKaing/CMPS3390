package edu.csub.startracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private Thread thread;
    private int touchX, touchY;

    private final Player player;

    private EnemySpawner spanwer;

    /**
     * GameView which shows
     * @param context Context
     * @param screenX int which is X position of background
     * @param screenY int which is Y position of background
     */
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        Resources res = getResources();

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.setY(screenY);

        player = new Player(res);
        spanwer = new EnemySpawner(res);

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
        spanwer.update();

    }

    /**
     * Draws background and player
     */
    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            background1.draw(canvas);
            background2.draw(canvas);
            player.draw(canvas);
            spanwer.draw(canvas);

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
