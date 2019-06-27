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

    //private Button[] buttons = new Button[15];
    private ArrayList<Button> nbuttons;
    private TextView TimerTextView;
    private int buttonWidth, buttonHeight;
    private boolean start = false;
    private int count = 0;
    private ArrayList<Button> listButtons = new ArrayList<>();
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTextView = findViewById(R.id.textView);
        listButtons.add((Button)findViewById(R.id.button2));
        listButtons.add((Button)findViewById(R.id.button3));
        listButtons.add((Button)findViewById(R.id.button4));
        listButtons.add((Button)findViewById(R.id.button5));
        listButtons.add((Button)findViewById(R.id.button6));
        listButtons.add((Button)findViewById(R.id.button7));
        listButtons.add((Button)findViewById(R.id.button8));
        listButtons.add((Button)findViewById(R.id.button9));
        listButtons.add((Button)findViewById(R.id.button10));
        listButtons.add((Button)findViewById(R.id.button11));
        listButtons.add((Button)findViewById(R.id.button12));
        listButtons.add((Button)findViewById(R.id.button13));
        listButtons.add((Button)findViewById(R.id.button14));
        listButtons.add((Button)findViewById(R.id.button16));
        listButtons.add((Button)findViewById(R.id.button18));


        Button bb = findViewById(R.id.button3);
        Log.d(TAG,  Float.toString(bb.getX()));
        Log.d(TAG,  Float.toString(bb.getY()));
        Log.d(TAG,  "!!!!!!!!!!!!!!!!!!!!");
        //Collections.copy(nbuttons, listButtons);
        nbuttons = new ArrayList<>(listButtons);
        /*for (int i = 0; i < 15; i++) {
            nbuttons[i] = listButtons.get(i);
            Log.d(TAG,  Float.toString(nbuttons[i].getX()));
            Log.d(TAG,  Float.toString(nbuttons[i].getY()));
            Log.d(TAG,  "-----------------------------");
        }*/

        //l = Arrays.asList(buttons);
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

    public void startGame(boolean hard) {
        count = 0;
        for (int i = 0; i < 15; i++) {
            listButtons.get(i).setEnabled(true);
        }
        //mTimer.purge();
        //mMyTimerTask = new MyTimerTask();
        start = true;
        //mTimer.schedule(mMyTimerTask, 0, 1000);
        contribution(hard);
    }

    public  void finishGame() {
        start = false;
        //mTimer.cancel();
        for (int i = 0; i < 15; i++) {
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
        //count = 0;
        //Log.d(TAG, "UUU");

        Collections.shuffle(listButtons);

        int result = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = i; j < 15; j++) {
                if (Integer.parseInt((String) listButtons.get(i).getText()) > Integer.parseInt((String) listButtons.get(j).getText())) {
                    result++;
                }
            }
        }
        if (!hard && result % 2 == 1) {
            //Log.d(TAG, "!hard && result % 2 == 1");
            return contribution(hard);
            //Log.d(TAG, "AFTER !hard && result % 2 == 1");
        }
        else if (hard && result % 2 == 0) {
            //Log.d(TAG, "hard && result % 2 == 0");
            return contribution(hard);
            //Log.d(TAG, "AFTER hard && result % 2 == 0");
        }
        Log.d(TAG, "AFTERr");

        for (int i = 0; i < 15; i++) {
            //Log.d(TAG, "----------------------------------------------------");
            //Log.d(TAG, (String) listButtons.get(i).getText());
            //System.out.println("----------------------------------------------------");
            //System.out.println(Integer.parseInt((String) l.get(i).getText())*2);
            listButtons.get(i).setX(0);
            listButtons.get(i).setY(0);
            listButtons.get(i).setOnClickListener(this);
            listButtons.get(i).setLayoutParams(new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight));
            if (i < 4) {
                listButtons.get(i).setX(i*buttonWidth);
            }
            else if (i < 8) {
                listButtons.get(i).setY(buttonHeight);
                listButtons.get(i).setX((i - 4)*buttonWidth);
            }
            else if (i < 12) {
                listButtons.get(i).setY(2*buttonHeight);
                listButtons.get(i).setX((i - 8)*buttonWidth);
            }
            else {
                listButtons.get(i).setY(3*buttonHeight);
                listButtons.get(i).setX((i - 12)*buttonWidth);
            }
        }
        for (int i = 0; i < 15; i++) {
            //Log.d(TAG,  Float.toString(nbuttons[i].getX()));
            //Log.d(TAG,  Float.toString(nbuttons[i].getY()));
            Log.d(TAG,  "-----------------------------");
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
            if (nbuttons.get(i).getX() != x || nbuttons.get(i).getY() != y){
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
        /*if (!start) {
            start = true;
            mTimer.schedule(mMyTimerTask, 0, 1000);
        }*/
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
