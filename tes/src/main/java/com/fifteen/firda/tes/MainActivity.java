package com.fifteen.firda.tes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Cards mCards;
    private TextView TimerTextView;
    private int count = 0;
    private List<Button> listButtons;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private static final String TAG = "MainActivity";
    private boolean level;
    //ArrayList<Drawable> drawables;
    boolean hints;
    Image selectedImage;
    int selectedImageId;

    static final int GALLERY_REQUEST = 1;
    Bitmap bMap;
    public static final String CARDS_KEY = "mCards";
    public static final String EXTRA_CARDS = "mCardsExtra";
    public static final String COUNT_KEY = "count";
    public static final String EXTRA_COUNT = "countExtra";
    public static final String DRAWABLE_KEY = "drawables";
    public static final String EXTRA_DRAWABLE = "drawablesExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

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

        /*drawables = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            drawables.add(listButtons.get(i).getBackground());
        }*/
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
                    try {
                        bMap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        selectedImage = new Image(bMap);
        Bitmap[] bitmapsArray = selectedImage.getPieces();
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), bitmapsArray[i]));
        }
        /*for (int i = 0; i < 16; i++) {
            drawables.set(i, listButtons.get(i).getBackground());
        }*/
        updateButtons();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(CARDS_KEY, mCards);
        //savedInstanceState.putParcelable(DRAWABLE_KEY, selectedImage.getImage());
        savedInstanceState.putInt(COUNT_KEY, count);
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
                mCards = (Cards) intent.getSerializableExtra(EXTRA_CARDS);
                count = intent.getIntExtra(EXTRA_COUNT, 0);
                //selectedImage = new Image((Bitmap)intent.getParcelableExtra(EXTRA_DRAWABLE));
                updateButtons();
            } else {
                count = 0;
                mCards = new Cards();
                selectedImage = new Image(BitmapFactory.decodeResource(getResources(), R.drawable.pp));
                selectedImageId = R.drawable.pp;
                contribution(hard);
            }
        } else {
            count = savedInstanceState.getInt(COUNT_KEY);
            mCards = (Cards) savedInstanceState.getSerializable(CARDS_KEY);
            selectedImage = new Image(BitmapFactory.decodeResource(getResources(), R.drawable.pp));
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
        //menu.add("Show hints");
        //menu.add("Hide hints");
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
        } /*else if ("Show hints".equals(item.getTitle())) {
            hints = true;
            changeHints(14);
        } else if ("Hide hints".equals(item.getTitle())) {
            hints = false;
            changeHints(0);
        }*/ else if ("Upload image".equals(item.getTitle())) {
            uploadImage();
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSwitchClicked(View view) {
        boolean on = ((Switch)view).isChecked();
        if (on) {
            hints = true;
            changeHints(14);
        } else {
            hints = false;
            changeHints(0);
        }
    }
    public void changeHints(int size) {
        for (int i = 0; i < 16; i++) {
            if (!listButtons.get(i).getText().toString().equals("16")) listButtons.get(i).setTextSize(size);
        }
    }

    public void back() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra(EXTRA_CARDS, mCards);
        Bitmap selbit = selectedImage.getImage();
        intent.putExtra(EXTRA_DRAWABLE, selbit);

        intent.putExtra(EXTRA_COUNT, count);
        startActivity(intent);
    }
    private void contribution(boolean hard) {

        mCards.shuffleCards(hard);
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
        Button btn = (Button)v;
        int index = listButtons.indexOf(btn);
        mCards.moveCard(index);
        updateButtons();
        check();
    }

    private void updateButtons() {
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setText(mCards.get(i).toString());
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), selectedImage.getPieces()[mCards.get(i)-1]));/* drawables.get()*/;

            listButtons.get(i).setEnabled(true);
            listButtons.get(i).setTextColor(getResources().getColor(R.color.background_light));
            if (!hints) listButtons.get(i).setTextSize(0);
            if (mCards.get(i) == 16)  {
                listButtons.get(i).setTextColor(getResources().getColor(R.color.holo_blue_bright));
                listButtons.get(i).setEnabled(false);
                listButtons.get(i).setTextSize(14);
            }
        }
    }
}

