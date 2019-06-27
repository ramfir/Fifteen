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

import java.util.Timer;
import java.util.TimerTask;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons = new Button[15];
    private Button[] nbuttons = new Button[15];
    private TextView TimerTextView;
    private int buttonWidth, buttonHeight;
    private boolean start = false;
    private int count = 0;
    private List<Button> l;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTextView = findViewById(R.id.textView);
        buttons[0]=findViewById(R.id.button2);buttons[5]=findViewById(R.id.button7);buttons[10]=findViewById(R.id.button12);
        buttons[1]=findViewById(R.id.button3);buttons[6]=findViewById(R.id.button8);buttons[11]=findViewById(R.id.button13);
        buttons[2]=findViewById(R.id.button4);buttons[7]=findViewById(R.id.button9);buttons[12]=findViewById(R.id.button14);
        buttons[3]=findViewById(R.id.button5);buttons[8]=findViewById(R.id.button10);buttons[13]=findViewById(R.id.button16);
        buttons[4]=findViewById(R.id.button6);buttons[9]=findViewById(R.id.button11);buttons[14]=findViewById(R.id.button18);
        for (int i = 0; i < 15; i++)
            nbuttons[i] = buttons[i];
        l = Arrays.asList(buttons);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        buttonWidth = size.x / 4;
        buttonHeight = size.y / 4 - size.y / 28;
        //contribution(false);
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);
        startGame(false);
    }

    public void startGame(boolean hardness) {
        count = 0;
        for (int i = 0; i < 15; i++) {
            buttons[i].setEnabled(true);
        }
        //mTimer.purge();
        //mMyTimerTask = new MyTimerTask();
        start = true;
        //mTimer.schedule(mMyTimerTask, 0, 1000);
        contribution(hardness);
    }

    public  void finishGame() {
        start = false;
        //mTimer.cancel();
        for (int i = 0; i < 15; i++) {
            buttons[i].setEnabled(false);
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
        //count = 0;
        //Log.d(TAG, "UUU");

        Collections.shuffle(l);
        int result = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = i; j < 15; j++) {
                if (Integer.parseInt((String) l.get(i).getText()) > Integer.parseInt((String) l.get(j).getText())) {
                    result++;
                }
            }
        }
        if (!hard && result % 2 == 1) {
            Log.d(TAG, "!hard && result % 2 == 1");
            return contribution(hard);
            //Log.d(TAG, "AFTER !hard && result % 2 == 1");
        }
        else if (hard && result % 2 == 0) {
            Log.d(TAG, "hard && result % 2 == 0");
            return contribution(hard);
            //Log.d(TAG, "AFTER hard && result % 2 == 0");
        }
        Log.d(TAG, "AFTERr");

        for (int i = 0; i < 15; i++) {
            Log.d(TAG, "----------------------------------------------------");
            Log.d(TAG, (String) l.get(i).getText());
            //System.out.println("----------------------------------------------------");
            //System.out.println(Integer.parseInt((String) l.get(i).getText())*2);
            l.get(i).setX(0);
            l.get(i).setY(0);
            l.get(i).setOnClickListener(this);
            l.get(i).setLayoutParams(new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight));
            if (i < 4) {
                l.get(i).setX(i*buttonWidth);
            }
            else if (i < 8) {
                l.get(i).setY(buttonHeight);
                l.get(i).setX((i - 4)*buttonWidth);
            }
            else if (i < 12) {
                l.get(i).setY(2*buttonHeight);
                l.get(i).setX((i - 8)*buttonWidth);
            }
            else {
                l.get(i).setY(3*buttonHeight);
                l.get(i).setX((i - 12)*buttonWidth);
            }
        }
        TimerTextView.setLayoutParams((new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight)));
        TimerTextView.setX(3*buttonWidth);
        TimerTextView.setY(3*buttonHeight);
        return true;
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (start) {
                        TimerTextView.setText(Integer.toString(count));
                        count += 1;
                    }
                }
            });
        }
    }

    private void check() {
        float x = 0;
        float y = 0;
        for (int i = 0; i < 15; i++) {
            if (i == 4 || i == 8 || i == 12){
                x = 0;
                y += buttonHeight;
            }
            if (nbuttons[i].getX() != x || nbuttons[i].getY() != y){
                break;
            }
            x += buttonWidth;
            if (i == 14) {
                Toast.makeText(this, "You have done it in " + TimerTextView.getText() + " seconds", Toast.LENGTH_LONG).show();
                //nbuttons[i].sete
                //mTimer.cancel();
                finishGame();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!start) {
            start = true;
            mTimer.schedule(mMyTimerTask, 0, 1000);
        }
        if (TimerTextView.getX() == v.getX() + buttonWidth && TimerTextView.getY() == v.getY()) {
            TimerTextView.setX(TimerTextView.getX() - buttonWidth); TimerTextView.setY(TimerTextView.getY());
            v.setX(v.getX() + buttonWidth); v.setY(TimerTextView.getY());
        }
        else if (TimerTextView.getX() == v.getX() - buttonWidth && TimerTextView.getY() == v.getY()) {
            TimerTextView.setX(TimerTextView.getX() + buttonWidth); TimerTextView.setY(TimerTextView.getY());
            v.setX(v.getX() - buttonWidth); v.setY(TimerTextView.getY());
        }
        else if (TimerTextView.getY() == v.getY() - buttonHeight && TimerTextView.getX() == v.getX()) {
            TimerTextView.setX(TimerTextView.getX()); TimerTextView.setY(TimerTextView.getY() + buttonHeight);
            v.setX(v.getX()); v.setY(TimerTextView.getY() - buttonHeight);
        }
        else if (TimerTextView.getY() == v.getY() + buttonHeight && TimerTextView.getX() == v.getX()) {
            TimerTextView.setX(TimerTextView.getX()); TimerTextView.setY(TimerTextView.getY() - buttonHeight);
            v.setX(v.getX()); v.setY(TimerTextView.getY() + buttonHeight);
        }
        check();
    }
}
