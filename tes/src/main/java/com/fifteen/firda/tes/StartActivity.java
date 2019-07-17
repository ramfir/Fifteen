package com.fifteen.firda.tes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    Button startButton;
    Buttons buttons;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startButton = findViewById(R.id.start);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            buttons = (Buttons) intent.getSerializableExtra("BUTTONS");
            count = intent.getIntExtra("COUNT", 0);
        }
    }

    public void start(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void continueLastAttempt(View view) {
        if (buttons == null) {
            Toast.makeText(this, "There was not any attempts", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("BUTTONS", buttons);
            intent.putExtra("COUNT", count);
            startActivity(intent);
        }
    }
}
