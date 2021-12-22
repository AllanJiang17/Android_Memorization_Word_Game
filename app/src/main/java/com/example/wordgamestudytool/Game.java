package com.example.wordgamestudytool;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game extends AppCompatActivity {
    private int counter = 0;
    private int maxPresCounter = 10;
    private String[] options = {"R", "O", "T", "C", "H", "A", "S", "P", "F", "E"};
    private String answer = "OCTOTHORPE";
    TextView textScreen, textQuestion;

    public Game() {
    }

    public void initiateGame(){
        setContentView(R.layout.game);
        options = shuffle(options);

//        for (String a : options) {
//            addView(((LinearLayout) findViewById(R.id.letterRoot)), a, ((EditText) findViewById(R.id.answers)));
//        }

        for (int i = options.length -1 ; i >= 0 ; i--)
        {
            String key = options[i];
            if(i>4)
                addView(((LinearLayout) findViewById(R.id.letterRoot)), key, ((EditText) findViewById(R.id.answers)));
            else
                addView(((LinearLayout) findViewById(R.id.letterRoot1)), key, ((EditText) findViewById(R.id.answers)));
        }
    }

    private String[] shuffle(String[] list) {
        Random rnd = new Random();
        for (int i = list.length - 1; i > 0; i--) {
            int pivot = rnd.nextInt(i + 1);
            String s = list[pivot];
            list[pivot] = list[i];
            list[i] = s;
        }
        return list;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutSize = new LinearLayout.LayoutParams(
                10,20
        );

        linearLayoutSize.rightMargin = 20;
        final TextView textView = new TextView(this);


        textView.setLayoutParams(linearLayoutSize);
        textView.setBackground(this.getResources().getDrawable(R.drawable.img));
        textView.setTextColor(this.getResources().getColor(R.color.purple_500));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        //textView.setFocusable(true);
        textView.setTextSize(25);

        textQuestion = (TextView) findViewById(R.id.textQuestion);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter < maxPresCounter) {
                    if (counter == 0) {
                        editText.setText("");
                    }
                    editText.setText(editText.getText().toString() + text);
                    //textView.animate().alpha(0).setDuration((300));
                    counter++;

                    if (counter == maxPresCounter) {
                        checkAnswer();
                    }
                }
            }
        });
        viewParent.addView((textView));
    }

    private void checkAnswer() {
        counter = 0;

        EditText editText = findViewById(R.id.answers);
        LinearLayout linearLayout = findViewById(R.id.letterRoot);
        LinearLayout linearLayout1 = findViewById(R.id.letterRoot1);

        if (editText.getText().toString().equals(answer.toLowerCase())) {
            Toast.makeText(Game.this, "Correct", Toast.LENGTH_SHORT).show();
            editText.setText("");
        } else {
            Toast.makeText(Game.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

        options = shuffle(options);
        linearLayout.removeAllViews();
        linearLayout1.removeAllViews();
        for (int i = options.length -1 ; i >= 0 ; i--)
        {
            String key = options[i];
            if(i>4)
                addView(linearLayout, key, editText);
            else
                addView(linearLayout1, key, editText);
        }
    }

}
