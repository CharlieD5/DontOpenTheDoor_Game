package com.charlyan.dontopenthedoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class ScoreActivity extends BaseActivity {
    public static final String NAME_KEY = "NAME";
    public static final String SCORE_KEY = "SCORE";

    private ListView mListView;
    Button bContinue;
    Button bShare;
    Intent shareIntent;
    String shareMessage = "My top 3 high scores for Don't Open The Door are: "
            + ", "
            + ", "
            + "! Can you beat mine?";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        bContinue = (Button) findViewById(R.id.continue_button);

        DBAdapter db;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String name = extras.getString(NAME_KEY);
            int score = extras.getInt(SCORE_KEY);

            db = new DBAdapter(this);

            db.open();
            db.insertScore(name, String.valueOf(score));

            List<Scores> scoresList = db.getListOfScores();

            // Initialize and create a new adapter with layout named list found in activity_main layout
            mListView = (ListView) findViewById(R.id.list);
            ArrayAdapter<Scores> adapter = new ScoreAdapter(this, scoresList);
            mListView.setAdapter(adapter);

            db.close();
        } else {
            db = new DBAdapter(this);

            db.open();

            List<Scores> scoresList = db.getListOfScores();

            // Initialize and create a new adapter with layout named list found in activity_main layout
            mListView = (ListView) findViewById(R.id.list);
            ArrayAdapter<Scores> adapter = new ScoreAdapter(this, scoresList);
            mListView.setAdapter(adapter);
        }

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

    public static void Sort () {

    }

    public void ContinueButtonClick(View view) {
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(intent);
    }

}