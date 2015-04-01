package edu.augustana.csc490.augiearcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by logankruse11 on 4/1/2015.
 */
public class PlayGameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "AugieArcher"; // for Log.w(TAG, ...)

    private GameThread gameThread; // runs the main game loop

    private Activity playGameActivity; // keep a reference to the main Activity
    private Paint myPaint;
    private Paint backgroundPaint;

    private int x;
    private int y;
    private int screenWidth;
    private int screenHeight;


    public PlayGameView(Context context, AttributeSet atts)
    {
        super(context, atts);
        playGameActivity = (Activity) context;

        getHolder().addCallback(this);

        myPaint = new Paint();
        myPaint.setColor(Color.BLUE);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.CYAN);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // ensure that thread terminates properly
        boolean retry = true;
        gameThread.setRunning(false); // terminate gameThread

        while (retry)
        {
            try
            {
                gameThread.join(); // wait for gameThread to finish
                retry = false;
            }
            catch (InterruptedException e)
            {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }

    public void stopGame()
    {
        if (gameThread != null)
            gameThread.setRunning(false);
    }

    public void releaseResources()
    {
        // release any resources (e.g. SoundPool stuff)
    }

    private void gameStep()
    {
        x++;
    }

    public void updateView(Canvas canvas)
    {
        if (canvas != null) {
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            canvas.drawCircle(x, y, 20, myPaint);
        }
    }


    private class GameThread extends Thread
    {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        // initializes the surface holder
        public GameThread(SurfaceHolder holder)
        {
            surfaceHolder = holder;
            setName("GameThread");
        }

        // changes running state
        public void setRunning(boolean running)
        {
            threadIsRunning = running;
        }

        @Override
        public void run()
        {
            Canvas canvas = null;

            while (threadIsRunning)
            {
                try
                {
                    // get Canvas for exclusive drawing from this thread
                    canvas = surfaceHolder.lockCanvas(null);

                    // lock the surfaceHolder for drawing
                    synchronized(surfaceHolder)
                    {
                        gameStep();         // update game state
                        updateView(canvas); // draw using the canvas
                    }
                    Thread.sleep(10); // if you want to slow down the action...
                } catch (InterruptedException ex) {
                    Log.e(TAG,ex.toString());
                }
                finally  // regardless if any errors happen...
                {
                    // make sure we unlock canvas so other threads can use it
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
