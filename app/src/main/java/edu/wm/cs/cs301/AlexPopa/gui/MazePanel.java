package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MazePanel extends View {

    private Canvas canvas;
    private Bitmap map;
    private Paint paint;

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        canvas = new Canvas();
        paint = new Paint();
        drawManual();
    }

    public MazePanel(Context context){
        super(context);
        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.RED);
        drawManual();
    }

    public void drawManual(){
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 1000,2000,50, paint);
        canvas.drawRect(0, 2000,2000,500, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(550, 900, 300, paint);
    }

    @Override
    public void onDraw(Canvas canvas){
        //map = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    }
}
