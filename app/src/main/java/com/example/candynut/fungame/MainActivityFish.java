package com.example.candynut.fungame;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.content.Intent;
import android.content.SharedPreferences;


public class MainActivityFish extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(new FishPanel(this));
        //SharedPreferences.Editor scoreEdit = gamePrefs.edit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_fish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //public void sendtohighscores(){
        //Intent intent = new Intent(this,HighScore.class);
        //startActivity(intent);
    //}

    protected void onDestroy() {gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        if (FishPanel.score > gamePrefs.getInt("currenthighscore", 0)) {
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            scoreEdit.putInt("currenthighscore", FishPanel.score);
            scoreEdit.commit();
        }}
        private SharedPreferences gamePrefs;
        public static final String GAME_PREFS = "HighScoreFile";

    }

