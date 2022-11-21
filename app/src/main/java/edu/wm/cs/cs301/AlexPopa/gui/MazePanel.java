package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * author @ALEX POPA
 */
public class MazePanel extends View {

    private Canvas canvas;
    private Bitmap map;
    private Paint paint;

    /**
     * Constructor that allows the MazePanel to be represented on the xml file
     */
    public MazePanel(Context context, AttributeSet attrs) {
        //call the super method constructor
        super(context, attrs);
        //create a bitmap that is 350 dp x 350 dp and a canvas and paint to go with it
        map = Bitmap.createBitmap(919, 919, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(map);
        paint = new Paint();
    }

    /**
     * draws the Panel if the current screen is PlayManuallyActivity
     */
    public void drawManual(){
        //make sure bitmap is 350 dp x 350 dp
        map = Bitmap.createBitmap(919, 919, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(map);

        //create a temporary canvas
        Canvas c = new Canvas(map);

        //set color to gray
        paint.setColor(Color.GRAY);

        //draw gray rectangle on top of the screen
        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        //set color to black
        paint.setColor(Color.BLACK);

        //draw black rectangle on bottom of the screen
        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        //set color to red
        paint.setColor(Color.RED);

        //draw red circle on middle of the screen
        c.drawCircle(c.getHeight()/2, c.getHeight()/2, 100, paint);
    }

    /**
     * draws the Panel if the current screen is PlayAnimationActivity
     */
    public void drawDriver(){
        //create a bitmap that is 315 dp x 315 dp and a canvas to go with it
        map = Bitmap.createBitmap(827, 827, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(map);

        //create a temporary canvas
        Canvas c = new Canvas(map);

        //make the paint color gray, and make sure it knows to fill in the shapes
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);

        //draw gray rectangle on top of the screen
        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        //set color to black
        paint.setColor(Color.BLACK);

        //draw black rectangle on bottom of the screen
        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        //set color to green
        paint.setColor(Color.GREEN);

        //make a path object which will draw a polygon
        Path wall1 = new Path();
        wall1.reset();
        //starting point
        wall1.moveTo(100, c.getHeight());
        wall1.lineTo(100, 400);
        wall1.lineTo(400, 100);
        wall1.lineTo(400, 300);
        //close the shape
        wall1.close();

        //draw the polygon
        canvas.drawPath(wall1, paint);

        //set color to yellow
        paint.setColor(Color.YELLOW);

        //make another path object which will draw a polygon
        Path wall2 = new Path();
        wall2.reset();
        //starting point
        wall2.moveTo(c.getWidth()-100, c.getHeight());
        wall2.lineTo(c.getWidth()-100, 400);
        wall2.lineTo(c.getWidth()-300, 100);
        wall2.lineTo(500, 300);
        //close the shape
        wall2.close();

        //draw the polygon
        canvas.drawPath(wall2, paint);
    }

    /**
     * Automatically called to draw shapes on the MazePanel view
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //if this method is called when the user is going through the maze
        if(getContext() instanceof PlayManuallyActivity){
            drawManual();

        }else{
        //if the method is called when a robot driver is going through the maze
            drawDriver();
        }
        //draw the bitmap
        canvas.drawBitmap(map, 0,0, paint);
    }
}

