package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        Canvas c = new Canvas(map);

        paint.setColor(Color.GRAY);

        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.BLACK);

        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        paint.setColor(Color.RED);

        c.drawCircle(c.getHeight()/2, c.getHeight()/2, 100, paint);
    }

    public void drawDriver(){
        Canvas c = new Canvas(map);

        paint.setColor(Color.GRAY);

        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.BLACK);

        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);
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

