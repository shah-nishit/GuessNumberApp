package com.example.guessnumberapp.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends Activity {

    int randomNumber;
    private static final int NUMBERS_IN_QUIZ = 5;
    private int totalGuesses;
    private int correctAnswers;
    private TextView answerTextView;
    private TextView questionNumberTextView;
    private Button btnGuess1;
    private Button btnGuess2;
    private Button btnGuess3;
    private String randomNumberText;
    private SharedPreferences TopTen;
    private static final String TOP10 = "TOP10";
    private ArrayList<String> Dates;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        questionNumberTextView.setText(getResources().getString(R.string.output, 0, NUMBERS_IN_QUIZ));

        btnGuess1 = (Button) findViewById(R.id.button1);
        btnGuess2 = (Button) findViewById(R.id.button2);
        btnGuess3 = (Button) findViewById(R.id.button3);

        TopTen = getSharedPreferences(TOP10, MODE_PRIVATE);
        Dates = new ArrayList<String>(TopTen.getAll().keySet());
        adapter = new ArrayAdapter<String>(this, R.layout.toptenlist, Dates);

        btnGuess1.setOnClickListener(guessButtonListener);
        btnGuess2.setOnClickListener(guessButtonListener);
        btnGuess3.setOnClickListener(guessButtonListener);

    }
    protected void onStart() {
        super.onStart();
        totalGuesses = 0;
        correctAnswers = 0;
    }
    public int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            totalGuesses = totalGuesses + 1;
            Button guessButton = ((Button) v);
            randomNumberText = guessButton.getText().toString();
            gameLogic(randomNumberText);
        }
    };

    public void gameLogic(String buttonNumber)
    {
        randomNumber = randInt(1, 3);
        //Toast.makeText(MainActivity.this, "Guess Number is: " + totalGuesses, Toast.LENGTH_SHORT).show();
        if (buttonNumber.equals(String.valueOf(randomNumber)))
        {
            ++correctAnswers; // increment the number of correct answers

            // display correct answer in green text
            questionNumberTextView.setText(getResources().getString(R.string.output, (correctAnswers), NUMBERS_IN_QUIZ));
            answerTextView.setText(randomNumber + "!");
            answerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
            Toast.makeText(MainActivity.this, "Lucky Guess!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            answerTextView.setText(R.string.incorrect_answer);
            answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer));
            Toast.makeText(MainActivity.this, "Unlucky Guess!", Toast.LENGTH_SHORT).show();
        }
        if (totalGuesses == NUMBERS_IN_QUIZ)
        {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            SharedPreferences.Editor preferenceEditor = TopTen.edit();
            String datetime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String result = datetime + " Score: " + Integer.toString(correctAnswers);
            preferenceEditor.putString(result, result );
            preferenceEditor.apply();
            if (!TopTen.contains(datetime))
            {
                Dates.add(datetime);
                Collections.sort(Dates, String.CASE_INSENSITIVE_ORDER);
                adapter.notifyDataSetChanged();
            }
            intent.putExtra("CorrectAnswers",correctAnswers);
            startActivity(intent);
        }
    }

    public void resetNumberApp()
    {
        totalGuesses = 0;
        correctAnswers = 0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}