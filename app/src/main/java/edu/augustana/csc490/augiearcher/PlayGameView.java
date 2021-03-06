package edu.augustana.csc490.augiearcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by logankruse11 on 4/1/2015.
 */
public class PlayGameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "AugieArcher"; // for Log.w(TAG, ...)

    private GameThread gameThread; // runs the main game loop

    private Activity playGameActivity; // keep a reference to the main Activity
    private Paint backgroundPaint;
    private Paint scoreText;
    private ArrayList<ArrowObject> arrowArrayList;
    private TargetObject target;


    private boolean isGameOver = true;

    private int score;
    private int shotCount;
    private int missCount=0;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int targetX;
    private int targetYTop;
    private int targetYBot;
    private int screenWidth;
    private int screenHeight;


    public PlayGameView(Context context, AttributeSet atts) {
        super(context, atts);
        playGameActivity = (Activity) context;

        getHolder().addCallback(this);

        arrowArrayList = new ArrayList<ArrowObject>();


        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        scoreText = new Paint();
        scoreText.setTextSize(50);
        scoreText.setColor(Color.BLACK);
    }

    // called when the size changes (and first time, when view is created)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;
        targetX = w - 100;
        targetYTop = h / 2 - 175;
        targetYBot = h / 2 + 175;

        target=new TargetObject(targetX,h/2,5,targetYBot-targetYTop);

        startNewGame();
    }

    public void startNewGame() {
        if (isGameOver) {
            isGameOver = false;
            gameThread = new GameThread(getHolder());
            gameThread.start(); // start the main game loop going
        }
    }


    private void gameStep() {
        score=0;
        for (int i = 0; i < arrowArrayList.size(); i++) {
            ArrowObject arrowI=arrowArrayList.get(i);
            if (!target.isInTarget(arrowI)) {
                arrowI.moveArrow();
            }
            if(arrowI.getX()>=screenWidth+20||arrowI.getY()>=screenHeight) {
                arrowArrayList.remove(i);
                missCount++;
            }
            score+=arrowI.getArrowScore();
        }
    }

    public void updateView(Canvas canvas) {
        if (canvas != null) {
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            target.drawTarget(canvas);
            canvas.drawText("Score: "+score, 50,50,scoreText);
            canvas.drawText("Shots: "+shotCount+"/10",screenWidth-400,50,scoreText);
            for (int i = 0; i < arrowArrayList.size(); i++) {
                arrowArrayList.get(i).drawArrow(canvas);
            }
            if (shotCount<=9){
                canvas.drawText("Drag back and release to shoot!",50,screenHeight-60,scoreText);
            }else{
                canvas.drawText("Press back button to play again!",50,screenHeight-60,scoreText);
            }
        }
    }

    // stop the game; may be called by the MainGameFragment onPause
    public void stopGame() {
        if (gameThread != null)
            gameThread.setRunning(false);
    }

    // release resources; may be called by MainGameFragment onDestroy
    public void releaseResources() {
        // release any resources (e.g. SoundPool stuff)
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // called when the surface is destroyed
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // ensure that thread terminates properly
        boolean retry = true;
        gameThread.setRunning(false); // terminate gameThread

        while (retry) {
            try {
                gameThread.join(); // wait for gameThread to finish
                retry = false;
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(shotCount<=9) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                xStart = (int) e.getX();
                yStart = (int) e.getY();
            }
            if (e.getAction() == MotionEvent.ACTION_UP) {
                xEnd = (int) e.getX();
                yEnd = (int) e.getY();
                ArrowObject newArrow = new ArrowObject(50, screenHeight / 2, xStart, yStart, xEnd, yEnd);
                arrowArrayList.add(newArrow);
                shotCount++;
            }
        }
        return true;
    }

    // Thread subclass to run the main game loop
    private class GameThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        // initializes the surface holder
        public GameThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("GameThread");
        }

        // changes running state
        public void setRunning(boolean running) {
            threadIsRunning = running;
        }

        @Override
        public void run() {
            Canvas canvas = null;

            while (threadIsRunning) {
                try {
                    // get Canvas for exclusive drawing from this thread
                    canvas = surfaceHolder.lockCanvas(null);

                    // lock the surfaceHolder for drawing
                    synchronized (surfaceHolder) {
                        gameStep();         // update game state
                        updateView(canvas); // draw using the canvas
                    }
                    Thread.sleep(5); // if you want to slow down the action...
                } catch (InterruptedException ex) {
                    Log.e(TAG, ex.toString());
                } finally  // regardless if any errors happen...
                {
                    // make sure we unlock canvas so other threads can use it
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
