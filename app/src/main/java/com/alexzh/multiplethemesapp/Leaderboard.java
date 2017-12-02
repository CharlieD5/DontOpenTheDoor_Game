package com.alexzh.multiplethemesapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lynnreilly on 10/25/17.
 */

public class Leaderboard extends BaseActivity {

    protected SQLiteDatabase db;

    ArrayList<String> addArray = new ArrayList<String>();
    ListView show;
    Button bContinue;
    Button bShare;
    Intent shareIntent;
    String shareMessage = "My top 3 high scores for Don't Open The Door are: "
            + ", "
            + ", "
            + "! Can you beat mine?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Button continue_button = (Button) findViewById(R.id.continue_button);
        Button share_button = (Button) findViewById(R.id.share_button);
       // db = (new DatabaseHelper(this)).getReadableDatabase();

       // db = (new DatabaseHelper(this)).getReadableDatabase();


        bShare = (Button) findViewById(R.id.share_button);
        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

    }
    public void ContinueButtonClick(View view) {
        Intent intent = new Intent(Leaderboard.this, MainActivity.class);
        startActivity(intent);
    }
}
