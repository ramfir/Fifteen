package com.fifteen.firda.tes;

import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Buttons buttons;
    private TextView TimerTextView;
    private int count = 0;
    private List<Button> listButtons;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private static final String TAG = "MainActivity";
    private boolean level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTextView = findViewById(R.id.time);
        listButtons = new ArrayList<>();
        listButtons.add((Button)findViewById(R.id.one));
        listButtons.add((Button)findViewById(R.id.two));
        listButtons.add((Button)findViewById(R.id.three));
        listButtons.add((Button)findViewById(R.id.four));
        listButtons.add((Button)findViewById(R.id.five));
        listButtons.add((Button)findViewById(R.id.six));
        listButtons.add((Button)findViewById(R.id.seven));
        listButtons.add((Button)findViewById(R.id.eight));
        listButtons.add((Button)findViewById(R.id.nine));
        listButtons.add((Button)findViewById(R.id.ten));
        listButtons.add((Button)findViewById(R.id.eleven));
        listButtons.add((Button)findViewById(R.id.twelve));
        listButtons.add((Button)findViewById(R.id.thirteen));
        listButtons.add((Button)findViewById(R.id.fourteen));
        listButtons.add((Button)findViewById(R.id.fifteen));
        listButtons.add((Button)findViewById(R.id.sixteen));
        for (int i = 0; i < 16; i++)
            listButtons.get(i).setOnClickListener(this);
        startGame(false, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("TAG", buttons);
        savedInstanceState.putInt("COUNT", count);
    }

    private void startGame(boolean hard, Bundle savedInstanceState) {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(true);
        }
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null && !level) {
                buttons = (Buttons) intent.getSerializableExtra("BUTTONS");
                count = intent.getIntExtra("COUNT", 0);
                updateButtons();
            } else {
                count = 0;
                buttons = new Buttons();
                contribution(hard);
            }
        } else {
            count = savedInstanceState.getInt("COUNT");
            buttons = (Buttons) savedInstanceState.getSerializable("TAG");
            updateButtons();
        }

    }

    public  void finishGame() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(false);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Normal level");
        menu.add("Hard level");
        menu.add("Back");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if ("Normal level".equals(item.getTitle())) {
            level = true;
            startGame(false, null);
        }
        else if ("Hard level".equals(item.getTitle())) {
            level = true;
            startGame(true, null);
        } else if ("Back".equals(item.getTitle())) {
            back();
        }
        return super.onOptionsItemSelected(item);
    }

    public void back() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("BUTTONS", buttons);
        intent.putExtra("COUNT", count);
        startActivity(intent);
    }
    private void contribution(boolean hard) {

        buttons.shuffleButtons(hard);
        updateButtons();
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    TimerTextView.setText(Integer.toString(count));
                    count += 1;
                }
            });
        }
    }

    private void check() {
        for (int i = 0; i < 16; i++) {
            if (Integer.parseInt(listButtons.get(i).getText().toString()) != i + 1)
                break;
            if (i == 15) {
                Toast.makeText(this, "You have done it in " + TimerTextView.getText() + " seconds", Toast.LENGTH_LONG).show();
                finishGame();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button)v;
        int index = listButtons.indexOf(btn);
        buttons.moveButtons(index);
        updateButtons();
        check();
    }

    private void updateButtons() {
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setText(buttons.get(i).toString());
            listButtons.get(i).setEnabled(true);
            if (buttons.get(i) == 16) listButtons.get(i).setEnabled(false);
        }
    }
}

