package com.fifteen.firda.tes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buttons implements Serializable {
    private List<Integer> buttons;

    public Buttons() {
        buttons = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            buttons.add(i+1);
        }
    }
    public static void foo(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        context.startActivity(intent);

    }
    public Integer get(int i) {
        return buttons.get(i);
    }

    public boolean shuffleButtons(boolean hard) {
        Collections.shuffle(buttons);
        int result = 0;
        for (int i = 0; i < 16; i++) {
            if (buttons.get(i) != 16) {
                //Log.d("TAG", buttons.get(i).toString());
                for (int j = 0; j < i; j++) {
                    if (buttons.get(j) != 16) {
                        if (buttons.get(j) > buttons.get(i)) {
                            result++;
                        }
                    }
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
            return shuffleButtons(hard);
        }
        else if (hard && result % 2 == 0) {
            return shuffleButtons(hard);
        }
        //if (!hard && result%2==0)
          //  Log.d("TAG", "URAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return true;
    }

    public void moveButtons(int index) {
        if (index + 1 < buttons.size() && buttons.get(index+1) == 16) {
            buttons.set(index+1, buttons.get(index));
            buttons.set(index, 16);
        } else if (index - 1 >= 0 && buttons.get(index-1).toString().equals("16")) {
            buttons.set(index-1, buttons.get(index));
            buttons.set(index, 16);
        } else if (index + 4 < buttons.size() && buttons.get(index+4).toString().equals("16")) {
            buttons.set(index+4, buttons.get(index));
            buttons.set(index, 16);
        } else if (index - 4 >= 0 && buttons.get(index-4).toString().equals("16")) {
            buttons.set(index-4, buttons.get(index));
            buttons.set(index, 16);
        }

    }
}
