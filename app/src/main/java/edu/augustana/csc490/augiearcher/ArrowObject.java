package edu.augustana.csc490.augiearcher;

import android.graphics.Point;

/**
 * Created by logankruse11 on 4/7/2015.
 */
public class ArrowObject {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int gravityCounter = 0;
    private boolean moveAble = true;

    public ArrowObject(int xStart, int yStart, int xPress, int yPress, int xRelease, int yRelease) {
        this.x = xStart;
        this.y = yStart;
        this.xSpeed = (xPress - xRelease) / 40;
        this.ySpeed = (yPress - yRelease) / 40;
    }

    public void moveArrow(int targetX, int targetYTop, int targetYBot) {
        this.x = x + xSpeed;
        this.y = y + ySpeed + gravityCounter / 50;
        gravityCounter += 5;
        if (x >= targetX && y >= targetYTop && y <= targetYBot) {
            this.moveAble = false;
        }
    }

    public boolean isMoveAble() {
        return moveAble;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
