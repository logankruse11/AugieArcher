package edu.augustana.csc490.augiearcher;

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
        this.xSpeed = (xPress - xRelease) / 40;
        this.ySpeed = (yPress - yRelease) / 40;
    }

    public void moveArrow() {
        this.x = x + xSpeed;
        this.y = y + ySpeed + gravityCounter / 50;
        gravityCounter += 5;
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
}
