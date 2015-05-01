package com.example.candynut.fungame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;

/**
 * Created by Yigang on 2015/4/30.
 */
public class MyFish extends Activity implements KeyEvent.Callback
{
    private Bitmap myfish;
    protected float fish_x, fish_y, fish_w, fish_h;
    public static float fishScale;

    public MyFish(Bitmap myfish)
    {
        this.myfish = myfish;
        fish_x = 1700;
        fish_y = 6000;
        fishScale = 0.1f;
        fish_w = myfish.getWidth();
        fish_h = myfish.getHeight();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.scale(fishScale, fishScale);
        paint = new Paint();
        paint.setAlpha(200);
        canvas.drawBitmap(myfish, fish_x, fish_y, paint);
    }
}
