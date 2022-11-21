package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MazePanel extends View {

    private Canvas canvas;
    private Bitmap map;
    private Paint paint;

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        map = Bitmap.createBitmap(919, 919, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        canvas = new Canvas(map);
        paint = new Paint();
    }

    public void drawManual(){
        map = Bitmap.createBitmap(919, 919, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        canvas = new Canvas(map);
        paint = new Paint();

        Canvas c = new Canvas(map);

        paint.setColor(Color.GRAY);

        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.BLACK);

        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        paint.setColor(Color.RED);

        c.drawCircle(c.getHeight()/2, c.getHeight()/2, 100, paint);
    }

    public void drawDriver(){

        map = Bitmap.createBitmap(827, 827, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        canvas = new Canvas(map);
        paint = new Paint();

        Canvas c = new Canvas(map);

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.GRAY);

        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.BLACK);

        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        paint.setColor(Color.GREEN);

        Path wall1 = new Path();
        wall1.reset(); // only needed when reusing this path for a new build
        wall1.moveTo(100, c.getHeight()); // used for first point
        wall1.lineTo(100, 400);
        wall1.lineTo(400, 100);
        wall1.lineTo(400, 300);
        wall1.close(); // there is a setLastPoint action but i found it not to work as expected

        canvas.drawPath(wall1, paint);

        paint.setColor(Color.YELLOW);

        Path wall2 = new Path();
        wall2.reset(); // only needed when reusing this path for a new build
        wall2.moveTo(c.getWidth()-100, c.getHeight()); // used for first point
        wall2.lineTo(c.getWidth()-100, 400);
        wall2.lineTo(c.getWidth()-300, 100);
        wall2.lineTo(500, 300);
        wall2.close(); // there is a setLastPoint action but i found it not to work as expected

        canvas.drawPath(wall2, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getContext() instanceof PlayManuallyActivity){
            drawManual();

        }else{
            drawDriver();
        }
        canvas.drawBitmap(map, 0,0, paint);
    }
}

