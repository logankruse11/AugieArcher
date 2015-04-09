package edu.augustana.csc490.augiearcher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by logankruse11 on 4/8/2015.
 */
public class TargetObject {
    private int centerX;
    private int centerY;
    private int sizeRadius;
    private int ringSpacing;
    private int[] radii;
    private int[] pointValue;

    public TargetObject(int centerX, int centerY, int ringCount, int diameter){
        this.centerX=centerX;
        this.centerY=centerY;
        sizeRadius=diameter/2;
        radii=new int[ringCount];
        pointValue=new int[ringCount];
        ringCount=5;
        ringSpacing=sizeRadius/ringCount;
        for(int i=0; i<ringCount; i++){
            radii[i]=(int) (((i+1.0)*sizeRadius)/ringCount);
            pointValue[i]=2*(ringCount-i);
        }
    }

    public boolean isInTarget(ArrowObject a){
        if(a.getX()>=centerX && a.getX()<=centerX+20 && a.getY()<=centerY+sizeRadius && a.getY() >= centerY-sizeRadius){
            getScoreForPoint(a);
            return true;
        }else{
            return false;
        }
    }

    public int getCenterX(){
        return centerX;
    }

    public int getCenterY(){
        return centerY;
    }

    public void getScoreForPoint(ArrowObject a){
        int score=Math.abs(centerY-a.getY())/ringSpacing;
        a.setArrowScore(pointValue[score]);
    }

    public void drawTarget(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(centerX-radii[4]/2,centerY-radii[4],centerX+radii[4]/2,centerY+radii[4]),paint);
        paint.setColor(Color.GREEN);
        canvas.drawOval(new RectF(centerX-radii[3]/2,centerY-radii[3],centerX+radii[3]/2,centerY+radii[3]),paint);
        paint.setColor(Color.BLUE);
        canvas.drawOval(new RectF(centerX-radii[2]/2,centerY-radii[2],centerX+radii[2]/2,centerY+radii[2]),paint);
        paint.setColor(Color.YELLOW);
        canvas.drawOval(new RectF(centerX-radii[1]/2,centerY-radii[1],centerX+radii[1]/2,centerY+radii[1]),paint);
        paint.setColor(Color.RED);
        canvas.drawOval(new RectF(centerX-radii[0]/2,centerY-radii[0],centerX+radii[0]/2,centerY+radii[0]),paint);

        /*for(int i=radii.length-1; i>=0;i--){
            if(i==3){
                paint.setColor(Color.BLACK);
                canvas.drawOval(new RectF(centerX-radii[i]/2,centerY-radii[i],centerX+radii[i]/2,centerY+radii[i]),paint);
            }else if(i==4){
                paint.setColor(Color.GREEN);
                canvas.drawOval(new RectF(centerX-radii[i]/2,centerY-radii[i],centerX+radii[i]/2,centerY+radii[i]),paint);
            }else if(i==2){
                paint.setColor(Color.BLUE);
                canvas.drawOval(new RectF(centerX-radii[i]/2,centerY-radii[i],centerX+radii[i]/2,centerY+radii[i]),paint);
            }else if(i==1){
                paint.setColor(Color.YELLOW);
                canvas.drawOval(new RectF(centerX-radii[i]/2,centerY-radii[i],centerX+radii[i]/2,centerY+radii[i]),paint);
            }else {
                paint.setColor(Color.RED);
                canvas.drawOval(new RectF(centerX - radii[i] / 2, centerY - radii[i], centerX + radii[i] / 2, centerY + radii[i]), paint);
            }
        }*/
    }
}
