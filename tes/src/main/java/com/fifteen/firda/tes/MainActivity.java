package com.fifteen.firda.tes;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView tex;
    private int w, h;
    private float x, y;
    private boolean start = false;
    private int count = 0;
    int result;
    private List<Button> l;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons[0]=findViewById(R.id.button2);buttons[5]=findViewById(R.id.button7);buttons[10]=findViewById(R.id.button12);
        buttons[1]=findViewById(R.id.button3);buttons[6]=findViewById(R.id.button8);buttons[11]=findViewById(R.id.button13);
        buttons[2]=findViewById(R.id.button4);buttons[7]=findViewById(R.id.button9);buttons[12]=findViewById(R.id.button14);
        buttons[3]=findViewById(R.id.button5);buttons[8]=findViewById(R.id.button10);buttons[13]=findViewById(R.id.button16);
        buttons[4]=findViewById(R.id.button6);buttons[9]=findViewById(R.id.button11);buttons[14]=findViewById(R.id.button18);
        
        for (int i = 0; i < 15; i++)
            nbuttons[i] = buttons[i];
        tex = findViewById(R.id.textView);
        l = Arrays.asList(buttons);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        w = size.x / 4;
        h = size.y / 4 - size.y / 28;
        contribution(false);
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Normal level");
        menu.add("Hard level");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if ("Normal level".equals(item.getTitle())) {
            contribution(false);
        }
        else if ("Hard level".equals(item.getTitle())) {
            contribution(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private void contribution(boolean hard) {
        count = 0;
        Collections.shuffle(l);
        result = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = i; j < 15; j++) {
                if (Integer.parseInt((String) l.get(i).getText()) > Integer.parseInt((String) l.get(j).getText())) {
                    result++;
                }
            }
        }
        if (!hard && result % 2 == 1) {
            contribution(hard);
        }
        else if (hard && result % 2 == 0) {
            contribution(hard);
        }
        for (int i = 0; i < 15; i++) {
            System.out.println("----------------------------------------------------");
            System.out.println(Integer.parseInt((String) l.get(i).getText())*2);
            l.get(i).setX(0);
            l.get(i).setY(0);
            l.get(i).setOnClickListener(this);
            l.get(i).setLayoutParams(new ConstraintLayout.LayoutParams(w, h));
            if (i < 4) {
                l.get(i).setX(i*w);
            }
            else if (i < 8) {
                l.get(i).setY(h);
                l.get(i).setX((i - 4)*w);
            }
            else if (i < 12) {
                l.get(i).setY(2*h);
                l.get(i).setX((i - 8)*w);
            }
            else {
                l.get(i).setY(3*h);
                l.get(i).setX((i - 12)*w);
            }
        }
        tex.setLayoutParams((new ConstraintLayout.LayoutParams(w, h)));
        tex.setX(3*w);
        tex.setY(3*h);
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    tex.setText(Integer.toString(count));
                    count += 1;
                }
            });
        }
    }

    private void check() {
        x = 0;
        y = 0;
        for (int i = 0; i < 15; i++) {
            if (i == 4 || i == 8 || i == 12){
                x = 0;
                y += h;
            }
            if (nbuttons[i].getX() != x || nbuttons[i].getY() != y){
                break;
            }
            x += w;
            if (i == 14) {
                Toast.makeText(this, "You have done it in " + tex.getText() + " seconds", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!start) {
            start = true;
            mTimer.schedule(mMyTimerTask, 0, 1000);
        }
        if (tex.getX() == v.getX() + w && tex.getY() == v.getY()) {
            tex.setX(tex.getX() - w); tex.setY(tex.getY());
            v.setX(v.getX() + w); v.setY(tex.getY());
        }
        else if (tex.getX() == v.getX() - w && tex.getY() == v.getY()) {
            tex.setX(tex.getX() + w); tex.setY(tex.getY());
            v.setX(v.getX() - w); v.setY(tex.getY());
        }
        else if (tex.getY() == v.getY() - h && tex.getX() == v.getX()) {
            tex.setX(tex.getX()); tex.setY(tex.getY() + h);
            v.setX(v.getX()); v.setY(tex.getY() - h);
        }
        else if (tex.getY() == v.getY() + h && tex.getX() == v.getX()) {
            tex.setX(tex.getX()); tex.setY(tex.getY() - h);
            v.setX(v.getX()); v.setY(tex.getY() + h);
        }
        check();
    }
}
