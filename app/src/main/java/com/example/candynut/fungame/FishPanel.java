package com.example.candynut.fungame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * Created by Yigang on 2015/4/29.
 */
public class FishPanel extends SurfaceView implements SurfaceHolder.Callback, KeyEvent.Callback, Runnable
{
    private SurfaceHolder sfh;
    private Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
    private Bitmap myfish = BitmapFactory.decodeResource(getResources(), R.drawable.myfish);
    private MyFish myFish;
    private Recti rect;
    private Canvas canvas;
    private Paint paint;
    private Thread thread;
    private long startTime, endTime;
    private String info, over, w1, w2;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private float lastX, lastY, lastZ;
    public static int level, score, miss;
    public static boolean flag, gameOver;

    public FishPanel(Context context)
    {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        over = "Gave Over";
        w1 = "I'm full, Mommy!!";
        w2 = "I'm hungry, Daddy!!";
        score = 0;
        level = 1;
        miss = 0;
        gameOver = false;
        rect.speed = 60;
        myFish = new MyFish(myfish);
        rect = new Recti();
        canvas = new Canvas();
        paint = new Paint();
        setFocusable(true);

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                lastX = event.values[0];
                lastY = event.values[1];
                lastZ = event.values[2];

                myFish.fish_x -= lastX*10;
                myFish.fish_y += lastY*10;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        flag = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        flag = false;
    }

    public void myDraw()
    {
        canvas = sfh.lockCanvas();
        if(canvas != null)
        {
            //Scale and draw the background map
            final float bgScaleX = 0.7f;
            final float bgScaleY = 0.7f;
            canvas.scale(bgScaleX, bgScaleY);
            canvas.drawBitmap(bg, 0, 0, paint);

            //Draw the information for the game
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setColor(Color.BLACK);
            canvas.drawText(info, 50, 50, paint1);
            canvas.drawLine(0, 60, 10000, 60, paint1);

            //Draw the player and food
            myFish.draw(canvas, paint);
            rect.draw(canvas, paint);

            //Set the GameOver message format
            Paint paint2 = new Paint();
            paint2.setTextSize(600);
            paint2.setColor(Color.BLACK);

            //Give GameOver message and end the game
            if(gameOver == true)
            {
                canvas.drawText(over, 1000, 6000, paint2);
            }

            //Set the Warning message format
            Paint paint3 = new Paint();
            paint3.setColor(Color.RED);
            paint3.setTextSize(400);

            //Set the Warning message format
            Paint paint4 = new Paint();
            paint4.setColor(Color.RED);
            paint4.setTextSize(4000);

            //Give message when the size is too big, then avoid the food
            if(MyFish.fishScale >= 0.19)
            {
                canvas.drawText(w1, 1000, 1000, paint3);
            }
            //Give message when the size is too small, then eat more food
            else if(MyFish.fishScale <= 0.04)
            {
                canvas.drawText(w2,1000, 3000, paint4);
            }
        }
        sfh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run()
    {
        while(flag)
        {
            startTime = System.currentTimeMillis();
            info = "Sore: " + score + "; " + "Speed: " + rect.speed + "; " + "Level: " + level + "; Size: " + String.format("%.1f", MyFish.fishScale * 10);
            rect.rectMove();
            if(rect.rect_y > 11000 || rect.rect_x > 8000)
            {
                rect.generateRect();
            }
            if(isCollision(myFish.fish_x, myFish.fish_y, myFish.fish_w, myFish.fish_h, rect.rect_x, rect.rect_y, rect.rect_w, rect.rect_h))
            {
                MyFish.fishScale += 0.01;
                if(MyFish.fishScale > 0.2f)
                {
                    gameOver = true;
                    flag = false;
                }
                score++;
                if(score % 3 == 0)
                {
                    rect.speed += 5;
                }
                if(score > 5 && score <= 10)
                {
                    level = 2;
                }
                else if(score > 10 && score <= 15)
                {
                    level = 3;
                }
                rect.generateRect();
            }
            endTime = System.currentTimeMillis();
            try{
                if(endTime - startTime < 50){
                    Thread.sleep(50 - (endTime - startTime));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myDraw();

        }
        if (score > gamePrefs.getInt("currenthighscore",0)){
        SharedPreferences.Editor scoreEdit = gamePrefs.edit();
        scoreEdit.commit();}
        //if(flag == false)
            //{
             //if (score > gamePrefs.getInt("currenthighscore",0)){
                 //SharedPreferences.Editor scoreEdit = gamePrefs.edit();
                 //scoreEdit.putInt("currenthighscore",score);
                 //scoreEdit.commit();
             //}
            //}
    }

    public boolean isCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2)
    {
        if(x1 >= x2 && x1 >= x2 + w2)
        {
            return false;
        }
        else if(x1 <= x2 && x1 + w1 <= x2)
        {
            return false;
        }
        else if(y1 >= y2 && y1 >= y2 + h2)
        {
            return false;
        }
        else if(y1 <= y2 && y1 + h1 <= y2)
        {
            return false;
        }
        miss = 0;
        return true;
    }

 /*
    #####################################################################################################
    #####Uncomment this part of code in order to test your code when you cannot use an Android phone#####
    #####################################################################################################
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_W && myFish.fish_y >= 50)
        {
            myFish.fish_y -= 60;
        }
        else if(keyCode == KeyEvent.KEYCODE_S && myFish.fish_y + myFish.fish_h <= 110400)
        {
            myFish.fish_y += 60;
        }
        else if(keyCode == KeyEvent.KEYCODE_A && myFish.fish_x >= 50)
        {
            myFish.fish_x -= 60;
        }
        else if(keyCode == KeyEvent.KEYCODE_D && myFish.fish_x + myFish.fish_w <= 8500)
        {
            myFish.fish_x += 60;
        }
        else if(keyCode == KeyEvent.KEYCODE_SPACE)
        {
            flag = true;
            thread.start();
        }
        return super.onKeyDown(keyCode, event);
    }
*/private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "HighScoreFile";

}