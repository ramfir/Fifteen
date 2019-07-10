package com.fifteen.firda.tes;

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

    private List<Integer> buttons;
    private TextView TimerTextView;
    private int count = 0;
    private List<Button> listButtons;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private static final String TAG = "MainActivity";

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
        buttons = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            buttons.add(new Integer(i+1));
        }
        startGame(false);
    }

    public void startGame(boolean hard) {
        count = 0;
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(true);
        }
        contribution(hard);
    }

    public  void finishGame() {
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;
        mMyTimerTask = null;
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(false);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Normal level");
        menu.add("Hard level");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if ("Normal level".equals(item.getTitle())) {
            startGame(false);
        }
        else if ("Hard level".equals(item.getTitle())) {
            startGame(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean contribution(boolean hard) {
        if (hard)
            Log.d(TAG, "HHHHHHHH");
        Collections.shuffle(buttons);
        int result = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < i; j++) {
                if (buttons.get(j) > buttons.get(i)) {
                    result++;
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            if (buttons.get(i) == 16) {
                result += 1 + i/4;
                break;
            }
        }
        if (!hard && result % 2 == 1) {
            return contribution(hard);
        }
        else if (hard && result % 2 == 0) {
            return contribution(hard);
        }

        for (int i = 0; i < 16; i++)
            listButtons.get(i).setText(buttons.get(i).toString());
        for (int i = 0; i < 16; i++) {
            if (listButtons.get(i).getText().toString().equals("16")) {
                listButtons.get(i).setEnabled(false);
                break;
            }
        }
        return true;
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
        float x = 0;
        float y = 0;
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
        if (index + 1 < listButtons.size() && listButtons.get(index+1).getText().toString().equals("16")) {
            listButtons.get(index+1).setText(listButtons.get(index).getText());
            listButtons.get(index+1).setEnabled(true);
            listButtons.get(index).setText("16");
            listButtons.get(index).setEnabled(false);
        } else if (index - 1 >= 0 && listButtons.get(index-1).getText().toString().equals("16")) {
            listButtons.get(index-1).setEnabled(true);
            listButtons.get(index-1).setText(listButtons.get(index).getText());
            listButtons.get(index).setText("16");
            listButtons.get(index).setEnabled(false);
        } else if (index + 4 < listButtons.size() && listButtons.get(index+4).getText().toString().equals("16")) {
            listButtons.get(index+4).setEnabled(true);
            listButtons.get(index+4).setText(listButtons.get(index).getText());
            listButtons.get(index).setText("16");
            listButtons.get(index).setEnabled(false);
        } else if (index - 4 >= 0 && listButtons.get(index-4).getText().toString().equals("16")) {
            listButtons.get(index-4).setEnabled(true);
            listButtons.get(index-4).setText(listButtons.get(index).getText());
            listButtons.get(index).setText("16");
            listButtons.get(index).setEnabled(false);
        }
        check();
    }
}
