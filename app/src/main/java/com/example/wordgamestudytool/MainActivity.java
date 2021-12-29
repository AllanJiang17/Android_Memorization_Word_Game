package com.example.wordgamestudytool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordgamestudytool.model.Input;
import com.example.wordgamestudytool.model.RecListViewAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, String> questionAnswer;
    private int counter = 0;
    private int maxPresCounter;
    TextView textQuestion;
    private int iterator;
    List<String> options;
    ProgressBar progressBar;
    String answer;
    String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private RecyclerView listRecycleView;
    private ArrayList<Input> inputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        questionAnswer = new HashMap<>();
        setContentView(R.layout.activity_main);
        inputs = new ArrayList<>();
    }

    public void startGame(View view){
        setContentView(R.layout.add_word_layout);
    }

    public void returnMenu(View view){
        setContentView(R.layout.activity_main);
    }

    public void addItem(View view){
        TextInputEditText question = findViewById(R.id.question);
        TextInputEditText answer = findViewById(R.id.answer);
        String q = question.getText().toString();
        String a = answer.getText().toString();
        if(q.isEmpty()){
            question.setError("Question Empty");
        } else if (a.isEmpty() || a.length() > 10){
            answer.setError("Answer Empty or Over length of 10");
        } else {
            questionAnswer.put(q, a);
            Toast.makeText(this, "Insert Successful", Toast.LENGTH_SHORT).show();
            question.setText("");
            answer.setText("");

            Input input = new Input(q, a);
            inputs.add(input);
        }
    }

    public void showItem(View view){
        setContentView(R.layout.list_view);
        listRecycleView = findViewById(R.id.listView);
        RecListViewAdapter recListViewAdapter = new RecListViewAdapter();
        recListViewAdapter.setInputs(inputs);

        listRecycleView.setAdapter(recListViewAdapter);
        listRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void complete(View view){
        if(questionAnswer.size() > 0) {
            setContentView(R.layout.game);
            progressBar = findViewById(R.id.progress_bar);
            iterator = 0;
            initiateGame();
        } else {
            Toast.makeText(MainActivity.this, "No Entries", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiateGame() {
        if(questionAnswer.size() > iterator){
            progressBar.setMax(questionAnswer.size());
            Set<String> question = questionAnswer.keySet();
            TextView questionView = findViewById(R.id.textQuestion);
            String q = (String) question.toArray()[iterator];
            questionView.setText(q);
            iterator++;
            answer = questionAnswer.get(q);
            options = new ArrayList<>();
            for(int i = 0; i < answer.length(); ++i){
                options.add(answer.substring(i, i+1).toLowerCase());
            }
            List<String> otherLetters = new ArrayList<>();
            for(String s : alphabet){
                if(!options.contains(s)){
                    otherLetters.add(s);
                }
            }
            while(options.size() < 10){
                Random rnd = new Random();
                int index = rnd.nextInt(otherLetters.size());
                options.add(otherLetters.get(index));
            }

            options = shuffle(options);
            maxPresCounter = answer.length();
//        for (String a : options) {
//            addView(((LinearLayout) findViewById(R.id.letterRoot)), a, ((EditText) findViewById(R.id.answers)));
//        }

            for (int i = 9 ; i >= 0 ; i--)
            {
                String key = options.get(i);
                if(i>4)
                    addView(((LinearLayout) findViewById(R.id.letterRoot)), key, ((EditText) findViewById(R.id.answers)));
                else
                    addView(((LinearLayout) findViewById(R.id.letterRoot1)), key, ((EditText) findViewById(R.id.answers)));
            }
        } else {
            questionAnswer.clear();
            setContentView(R.layout.add_word_layout);
        }
    }

    private List<String> shuffle(List<String> list) {
        Random rnd = new Random();
        for (int i = list.size() - 1; i > 0; i--) {
            int pivot = rnd.nextInt(i + 1);
            String s = list.get(pivot);
            list.set(pivot, list.get(i));
            list.set(i, s);
        }
        return list;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutSize = new LinearLayout.LayoutParams(
                150,200
        );

        linearLayoutSize.rightMargin = 30;
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
                    viewParent.removeView(textView);
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
        LinearLayout parent = findViewById(R.id.game);

        if (editText.getText().toString().equals(answer.toLowerCase())) {
            progressBar.incrementProgressBy(1);
            Snackbar.make(parent, "Correct", Snackbar.LENGTH_INDEFINITE)
                    .setTextColor(getResources().getColor(R.color.black))
                    .setAction("Next", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText("");
                            linearLayout.removeAllViews();
                            linearLayout1.removeAllViews();
                            initiateGame();
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.purple_200))
                    .show();
        } else {
            Snackbar.make(parent, "Incorrect", Snackbar.LENGTH_INDEFINITE)
                    .setTextColor(getResources().getColor(R.color.black))
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText("");
                            options = shuffle(options);
                            linearLayout.removeAllViews();
                            linearLayout1.removeAllViews();
                            for (int i = options.size() -1 ; i >= 0 ; i--)
                            {
                                String key = options.get(i);
                                if(i>4)
                                    addView(linearLayout, key, editText);
                                else
                                    addView(linearLayout1, key, editText);
                            }
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.purple_200))
                    .show();
        }
    }

}