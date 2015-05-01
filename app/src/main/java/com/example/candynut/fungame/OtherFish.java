package com.example.candynut.fungame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;
/**
 * Created by CandyNUT on 2015/4/30.
 */
public class OtherFish
{
    private OtherFish otherFish;
    private Bitmap otherfish;
    protected float oFish_x, oFish_y, oFish_w, oFish_h, oFishScale;
    private Random random = new Random();

    public OtherFish(Bitmap otherfish)
    {
        this.otherfish = otherfish;
        oFish_x = 200;
        oFish_y = 200;
        oFishScale = 3f;
        oFish_w = otherfish.getWidth();
        oFish_h = otherfish.getHeight();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.scale(oFishScale, oFishScale);
        canvas.drawBitmap(otherfish, oFish_x, oFish_y, paint);
    }

    public void otherFishMove()
    {
        oFish_y += 60;
    }

    public void generateOtherFish()
    {
        oFish_x = 200;//random.nextInt(3000);
        oFish_y = 200;
        otherFish = new OtherFish(otherfish);
    }
}
