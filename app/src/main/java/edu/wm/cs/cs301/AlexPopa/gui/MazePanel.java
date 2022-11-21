package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MazePanel extends View {

    private Canvas canvas;
    private Bitmap map;
    private Paint paint;

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        map = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        canvas = new Canvas(map);
        //canvas = new Canvas();
        paint = new Paint();
        drawManual();
    }

    public MazePanel(Context context){
        super(context);
        setFocusable(true);

        map = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        canvas = new Canvas(map);
        //canvas = new Canvas();
        //paint = new Paint();
        //paint.setColor(Color.RED);
        //drawManual(canvas, paint);
    }

    public void drawManual(){
        Paint paint = new Paint();

        //Bitmap b = Bitmap.createBitmap(canvas.getWidth(), canvas.getWidth(), Bitmap.Config.ALPHA_8);
        Canvas c = new Canvas(map);

        //c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.GRAY);

        c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, paint);

        paint.setColor(Color.BLACK);

        c.drawRect(0, c.getHeight()/2, c.getWidth(), c.getHeight(), paint);

        paint.setColor(Color.RED);

        c.drawCircle(c.getHeight()/2, c.getHeight()/2, 100, paint);

        //c.drawCircle(300,300, 200, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(map, 0,0, paint);
    }
}

    /*public void drawManual(Canvas can, Paint p){
        /*can.drawColor(Color.WHITE);
        p.setColor(Color.BLACK);
        can.drawRect(0, 1000,2000,50, paint);
        can.drawRect(0, 2000,2000,500, paint);
        p.setColor(Color.RED);
        can.drawCircle(550, 900, 300, paint);
        canvas.drawBitmap(map, 0, 0, p);
        can.drawColor(Color.GREEN);

        Bitmap b = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
        Canvas c = new Canvas(b);
        c.drawRect(0, 0, 200, 200, paint);

        paint.setTextSize(40);
        paint.setTextScaleX(1.f);
        paint.setAlpha(0);
        paint.setAntiAlias(true);
        c.drawText("Your text", 30, 40, paint);
        paint.setColor(Color.RED);

        can.drawBitmap(b, 10,10, paint);
    }

    public void onDraw(Canvas can, Paint p){
        //setLayoutParams(new ConstraintLayout.LayoutParams(1200, 2000));
        /*can.drawColor(Color.WHITE);
        p.setColor(Color.BLACK);
        can.drawRect(0, 1000,2000,50, paint);
        can.drawRect(0, 2000,2000,500, paint);
        p.setColor(Color.RED);
        can.drawCircle(550, 900, 300, paint);
        canvas.drawBitmap(map, 0, 0, p);
        canvas.drawColor(Color.GREEN);

        Bitmap b = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
        Canvas c = new Canvas(b);
        c.drawRect(0, 0, 200, 200, paint);

        paint.setTextSize(40);
        paint.setTextScaleX(1.f);
        paint.setAlpha(0);
        paint.setAntiAlias(true);
        c.drawText("Your text", 30, 40, paint);
        paint.setColor(Color.RED);

        canvas.drawBitmap(b, 10,10, paint);
    }*/

