package edu.augustana.csc490.augiearcher;

import android.graphics.Point;

/**
 * Created by logankruse11 on 4/1/2015.
 */
public class ShotObject {

    private Point press;
    private Point release;

    public ShotObject(int xStart, int yStart, int xEnd, int yEnd){
        press=new Point(xStart,yStart);
        release=new Point(xEnd,yEnd);
    }

    public int xSpeed(){
        int xSpeed=(press.x-release.x)/100;
        //System.out.println(xSpeed+"     "+(press.x-release.x));
        return xSpeed;
    }

    public int ySpeed(){
        int ySpeed=(press.y-release.y)/100;
        return ySpeed;
    }
}
