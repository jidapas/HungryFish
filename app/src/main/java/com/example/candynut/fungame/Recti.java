package com.example.candynut.fungame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;
import android.content.Intent;

/**
 * Created by Yigang on 2015/4/30.
 */
public class Recti
{
    private Recti rect;
    protected float rect_x, rect_y, rect_w, rect_h;
    public static int speed;
    private Random random = new Random();

    public Recti()
    {
        rect_x = 200;
        rect_y = 200;
        rect_w = 100;
        rect_h = 100;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect_x, rect_y, rect_x + rect_w, rect_y + rect_h, paint);
    }

    public void rectMove()
    {
        if(FishPanel.level == 1)
            rect_y += speed*10;
        else if(FishPanel.level == 2)
            rect_x += speed*10;
        else if(FishPanel.level == 3)
        {
            if(FishPanel.score % 2 == 1)
            {
                rect_y += speed*10;
            }
            else if(FishPanel.score % 2 == 0)
            {
                rect_x += speed*10;
            }
        }
    }

    public void generateRect()
    {
        if(FishPanel.level == 1)
        {
            rect_x = 100 + random.nextInt(6000);
            rect_y = 100;
            rect_w = 100 + random.nextInt(1000);
            rect_h = rect_w;

        }
        else if(FishPanel.level == 2)
        {
            rect_x = 100;
            rect_y = 100 + random.nextInt(6000);
            rect_w = 100 + random.nextInt(1000);
            rect_h = rect_w;
        }
        else if(FishPanel.level == 3)
        {
            if(FishPanel.score % 2 == 1)
            {
                rect_x = 100;
                rect_y = 100 + random.nextInt(6000);
                rect_w = 100 + random.nextInt(1000);
                rect_h = rect_w;
            }
            else if(FishPanel.score % 2 == 0)
            {
                rect_x = 100 + random.nextInt(6000);
                rect_y = 100;
                rect_w = 100 + random.nextInt(1000);
                rect_h = rect_w;
            }
        }
        rect = new Recti();
        FishPanel.miss++;
        if(FishPanel.miss > 3)
        {
            MyFish.fishScale -= 0.01;
            if(MyFish.fishScale <= 0.03)
            {


                FishPanel.gameOver = true;
                FishPanel.flag = false;
            }
        }
    }
}
