package edu.augustana.csc490.augiearcher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by logankruse11 on 4/7/2015.
 */
public class ArrowObject {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int gravityCounter = 0;
    private int arrowScore=0;

    public ArrowObject(int xStart, int yStart, int xPress, int yPress, int xRelease, int yRelease) {
        this.x = xStart;
        this.y = yStart;
        this.xSpeed = (xPress - xRelease) / 50;
        this.ySpeed = (yPress - yRelease) / 50;
    }

    public void moveArrow() {
        this.x = x + xSpeed;
        this.y = y + ySpeed + gravityCounter / 50;
        gravityCounter += 7;
    }

    public void setArrowScore(int score){
        this.arrowScore=score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArrowScore() {return arrowScore; }

    public void drawArrow(Canvas canvas){
        Paint arrow=new Paint();
        arrow.setColor(Color.MAGENTA);
        arrow.setStrokeWidth(3);
        canvas.drawLine(x, y, x - 40, y, arrow);
        canvas.drawLine(x, y, x - 10, y - 10, arrow);
        canvas.drawLine(x, y, x - 10, y + 10, arrow);
    }
}
