package com.example.candynut.fungame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.content.Intent;

import com.example.candynut.fungame.R;

public class MainHighScore extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_high_score);
        TextView t= new TextView(this);
        t=(TextView)findViewById(R.id.highscore);
        Intent highscoreactivity = getIntent();
        String message = highscoreactivity.getStringExtra(MainActivity.EXTRA_MESSAGE);
        t.setText(message);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_high_score, menu);
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



}private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "HighScoreFile";
    }