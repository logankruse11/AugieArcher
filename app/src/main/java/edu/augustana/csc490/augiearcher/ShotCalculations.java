package edu.augustana.csc490.augiearcher;

import android.graphics.Point;

/**
 * Created by logankruse11 on 4/1/2015.
 */
public class ShotCalculations {

    private Point press;
    private Point release;

    public ShotCalculations (Point press, Point release){
        this.press=press;
        this.release=release;
    }

    public int xSpeed(){
        int xSpeed=press.x-release.x;
        return xSpeed;
    }

    public int ySpeed(){
        int ySpeed=press.y-release.y;
        return ySpeed;
    }
}
