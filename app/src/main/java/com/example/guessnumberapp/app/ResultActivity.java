package com.example.guessnumberapp.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hardkiller on 18-06-2014.
 */
public class ResultActivity extends ListActivity
{
    private static final String TOP10 = "TOP10";
    private SharedPreferences TopTen;
    private ArrayList<String> Dates;
    private ArrayAdapter<String> adapter;
    Button share;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topscore);
        share = (Button)findViewById(R.id.sharebutton);
        final int scoreCount  = this.getIntent().getIntExtra("ScoreCount",0);
        TopTen = getSharedPreferences(TOP10, MODE_PRIVATE);
        Dates = new ArrayList<String>(TopTen.getAll().keySet());
        adapter = new ArrayAdapter<String>(this, R.layout.toptenlist, Dates);
        setListAdapter(adapter);
        Collections.sort(Dates, new ScoreCompare());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share my score!");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"I Scored "+ Integer.toString(scoreCount) +" in MidTerm!!");
                shareIntent.setType("text/plain");

                // display apps that can share text
                startActivity(Intent.createChooser(shareIntent,"Share my score!"));
            }
        });
    }
}
