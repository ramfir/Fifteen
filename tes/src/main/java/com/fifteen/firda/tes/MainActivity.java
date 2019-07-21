package com.fifteen.firda.tes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
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
    List<Drawable> drawables;
    boolean hints;


    static final int GALLERY_REQUEST = 1;
    Bitmap bMap;
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
        //uploadImage();
        //Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.pp);
        /*Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 240, 240, true);
        Bitmap[] bitmapsArray = new Bitmap[16];
        bitmapsArray[0] = Bitmap.createBitmap(bMapScaled, 0, 0, 60, 60);
        bitmapsArray[1] = Bitmap.createBitmap(bMapScaled, 60, 0, 60, 60);
        bitmapsArray[2] = Bitmap.createBitmap(bMapScaled, 120, 0, 60, 60);
        bitmapsArray[3] = Bitmap.createBitmap(bMapScaled, 180, 0, 60, 60);
        bitmapsArray[4] = Bitmap.createBitmap(bMapScaled, 0, 60, 60, 60);
        bitmapsArray[5] = Bitmap.createBitmap(bMapScaled, 60, 60, 60, 60);
        bitmapsArray[6] = Bitmap.createBitmap(bMapScaled, 120, 60, 60, 60);
        bitmapsArray[7] = Bitmap.createBitmap(bMapScaled, 180, 60, 60, 60);
        bitmapsArray[8] = Bitmap.createBitmap(bMapScaled, 0, 120, 60, 60);
        bitmapsArray[9] = Bitmap.createBitmap(bMapScaled, 60, 120, 60, 60);
        bitmapsArray[10] = Bitmap.createBitmap(bMapScaled, 120, 120, 60, 60);
        bitmapsArray[11] = Bitmap.createBitmap(bMapScaled, 180, 120, 60, 60);
        bitmapsArray[12] = Bitmap.createBitmap(bMapScaled, 0, 180, 60, 60);
        bitmapsArray[13] = Bitmap.createBitmap(bMapScaled, 60, 180, 60, 60);
        bitmapsArray[14] = Bitmap.createBitmap(bMapScaled, 120, 180, 60, 60);
        bitmapsArray[15] = Bitmap.createBitmap(bMapScaled, 180, 180, 60, 60);
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), bitmapsArray[i]));
        }*/
        drawables = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            drawables.add(listButtons.get(i).getBackground());
        }
        for (int i = 0; i < 16; i++)
            listButtons.get(i).setOnClickListener(this);
        startGame(false, savedInstanceState);
    }
    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){

                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageView.setImageURI(selectedImage);
                    try {
                        bMap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }/*
                    imageView.setImageBitmap(bitmap);
                    Bitmap btmp = Bitmap.createBitmap(bitmap, 30, 30, bitmap.getWidth()/8, bitmap.getHeight()/8);
                    imageView2.setImageBitmap(bitmap);*/
                }
        }
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 240, 240, true);
        Bitmap[] bitmapsArray = new Bitmap[16];
        bitmapsArray[0] = Bitmap.createBitmap(bMapScaled, 0, 0, 60, 60);
        bitmapsArray[1] = Bitmap.createBitmap(bMapScaled, 60, 0, 60, 60);
        bitmapsArray[2] = Bitmap.createBitmap(bMapScaled, 120, 0, 60, 60);
        bitmapsArray[3] = Bitmap.createBitmap(bMapScaled, 180, 0, 60, 60);
        bitmapsArray[4] = Bitmap.createBitmap(bMapScaled, 0, 60, 60, 60);
        bitmapsArray[5] = Bitmap.createBitmap(bMapScaled, 60, 60, 60, 60);
        bitmapsArray[6] = Bitmap.createBitmap(bMapScaled, 120, 60, 60, 60);
        bitmapsArray[7] = Bitmap.createBitmap(bMapScaled, 180, 60, 60, 60);
        bitmapsArray[8] = Bitmap.createBitmap(bMapScaled, 0, 120, 60, 60);
        bitmapsArray[9] = Bitmap.createBitmap(bMapScaled, 60, 120, 60, 60);
        bitmapsArray[10] = Bitmap.createBitmap(bMapScaled, 120, 120, 60, 60);
        bitmapsArray[11] = Bitmap.createBitmap(bMapScaled, 180, 120, 60, 60);
        bitmapsArray[12] = Bitmap.createBitmap(bMapScaled, 0, 180, 60, 60);
        bitmapsArray[13] = Bitmap.createBitmap(bMapScaled, 60, 180, 60, 60);
        bitmapsArray[14] = Bitmap.createBitmap(bMapScaled, 120, 180, 60, 60);
        bitmapsArray[15] = Bitmap.createBitmap(bMapScaled, 180, 180, 60, 60);
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), bitmapsArray[i]));
        }
        //drawables = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            drawables.set(i, listButtons.get(i).getBackground());
        }

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
        menu.add("Show hints");
        menu.add("Hide hints");
        menu.add("Upload image");
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
        } else if ("Show hints".equals(item.getTitle())) {
            hints = true;
            changeHints(14);
        } else if ("Hide hints".equals(item.getTitle())) {
            hints = false;
            changeHints(0);
        } else if ("Upload image".equals(item.getTitle())) {
            uploadImage();
        }
        return super.onOptionsItemSelected(item);
    }
    public void changeHints(int size) {
        for (int i = 0; i < 16; i++) {
            if (!listButtons.get(i).getText().toString().equals("16")) listButtons.get(i).setTextSize(size);
        }
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
                    int hours = count / 3600;
                    int minutes = (count% 3600) / 60;
                    int secs = count % 60;
                    String time = String.format(Locale.getDefault(),
                            "%02d:%02d:%02d", hours, minutes, secs);
                    TimerTextView.setText(time);
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
        Buttons.foo(this);
        Button btn = (Button)v;
        int index = listButtons.indexOf(btn);
        buttons.moveButtons(index);
        updateButtons();
        check();
    }

    private void updateButtons() {
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setText(buttons.get(i).toString());
            listButtons.get(i).setBackground(drawables.get(buttons.get(i)-1));

            listButtons.get(i).setEnabled(true);
            listButtons.get(i).setTextColor(getResources().getColor(R.color.background_light));
            if (!hints) listButtons.get(i).setTextSize(0);
            if (buttons.get(i) == 16)  {
                listButtons.get(i).setTextColor(getResources().getColor(R.color.holo_blue_bright));
                listButtons.get(i).setEnabled(false);
                listButtons.get(i).setTextSize(14);
            }
        }
    }
}

