package com.charlyan.dontopenthedoor;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import static com.charlyan.dontopenthedoor.ScoreActivity.NAME_KEY;
import static com.charlyan.dontopenthedoor.ScoreActivity.SCORE_KEY;

/**
 * Created by Anthony Gomez
 *
 * PlayHard activity is launched by selecting the hard difficulty option after selecting Play Game.
 * Creates 2 clickable image views and creates the game activity. Saves highest score reached in integer
 * score.
 */

public class PlayEasy extends BaseActivity {
    DBAdapter db = new DBAdapter(this);
    private Scores s;
    ImageView d1, d2;
    ImageView a1, a2;
    TextView tv_score;
    Button start_button;
    Random r;
    int score = 0, fps = 1000;
    int which = 0, whichsave = 0;
    int templeft = 0, left = 1;
    // Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    AnimationDrawable an;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_easy_gameplay);

        s = new Scores();

        r = new Random();

        start_button = (Button) findViewById(R.id.start_button);
        tv_score = (TextView) findViewById(R.id.tv_score);
        d1 = (ImageView) findViewById(R.id.d1);
        d2 = (ImageView) findViewById(R.id.d2);
        a1 = (ImageView) findViewById(R.id.a1);
        a2 = (ImageView) findViewById(R.id.a2);

        d1.setVisibility(View.VISIBLE);
        d2.setVisibility(View.VISIBLE);
        a1.setVisibility(View.INVISIBLE);
        a2.setVisibility(View.INVISIBLE);

        //Disables the doors so that cannot be clicked when game hasn't started
        d1.setEnabled(false);
        d2.setEnabled(false);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = 0;
                left = 1;
                tv_score.setText("SCORE: " + score);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        theGameActions();
                    }
                }, 1000);
                start_button.setEnabled(false);
                start_button.setVisibility(View.INVISIBLE);
            }
        });

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templeft = 1;
                d1.setImageResource(R.drawable.door1);
                score = score + 3;
                tv_score.setText("SCORE: " + score);
                d1.setEnabled(false);
            }
        });
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templeft = 1;
                d2.setImageResource(R.drawable.door1);
                score = score + 3;
                tv_score.setText("SCORE: " + score);
                d2.setEnabled(false);
            }
        });

    }

    private void theGameActions() {


        d1.setImageResource(R.drawable.door1);
        d2.setImageResource(R.drawable.door1);

        if (score < 10) {
            fps = 1000;
        } else if (score >= 10 && score < 20) {
            fps = 950;
        } else if (score >= 20 && score < 30) {
            fps = 900;
        } else if (score >= 30 && score < 40) {
            fps = 850;
        } else if (score >= 40 && score < 50) {
            fps = 800;
        } else if (score >= 50 && score < 60) {
            fps = 750;
        } else if (score >= 60 && score < 70) {
            fps = 700;
        } else if (score >= 70 && score < 80) {
            fps = 650;
        } else if (score >= 80 && score < 90) {
            fps = 600;
        } else if (score >= 90 && score < 100) {
            fps = 550;
        } else if (score >= 100 && score < 300) {
            fps = 400;
        } else if (score >= 300 && score < 600) {
            fps = 350;
        }
        an = (AnimationDrawable) ContextCompat.getDrawable(this, R.drawable.anim);

        do {
            which = r.nextInt(2) + 1;
        } while (whichsave == which);
        whichsave = which;

        if (which == 1) {
            d1.setImageDrawable(an);
            d1.setVisibility(View.VISIBLE);
            a1.setVisibility(View.VISIBLE);
            d1.setEnabled(true);
        } else if (which == 2) {
            d2.setImageDrawable(an);
            d2.setVisibility(View.VISIBLE);
            a2.setVisibility(View.VISIBLE);
            d2.setEnabled(true);
        }

        an.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                d1.setVisibility(View.VISIBLE);
                d2.setVisibility(View.VISIBLE);

                a1.setVisibility(View.INVISIBLE);
                a2.setVisibility(View.INVISIBLE);

                d1.setEnabled(false);
                d2.setEnabled(false);

                if (templeft == 0) {
                    left = left - 1;
                } else if (templeft == 1) {
                    templeft = 0;
                }

                if (left == 0) {

                    db.open();
                    List<Scores> scoresList = db.getListOfScores();
                    Scores oldScore = scoresList.get(scoresList.size() - 1);
                    int thirdScore = Integer.parseInt(oldScore.getScore());
                    // Vibrate for 500 milliseconds
                    //   v.vibrate(1000);
                    if (score > thirdScore) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlayEasy.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_new_high_score, null);
                        final EditText mPlayerName = (EditText) mView.findViewById(R.id.player_name);

                        Button mSaveScore = (Button) mView.findViewById(R.id.save_score_button);
                        TextView newScore = (TextView) mView.findViewById(R.id.score);
                        newScore.setText("Score: " + score);
                        mSaveScore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String name = mPlayerName.getText().toString();
                                if (!name.isEmpty()) {
                                    Bundle b = new Bundle();
                                    b.putString(NAME_KEY, name);
                                    b.putInt(SCORE_KEY, score);
                                    Intent intent = new Intent(PlayEasy.this, ScoreActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    setContentView(R.layout.activity_score_board);
                                } else {
                                    Toast.makeText(PlayEasy.this,
                                            R.string.error_save_msg,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                    else if (score <= thirdScore)
                    {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlayEasy.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_score, null);
                        ImageButton mContinue = (ImageButton) mView.findViewById(R.id.continue_leaderboard_button);
                        ImageButton mReplay = mView.findViewById(R.id.replay_button);
                        TextView finalScore = (TextView) mView.findViewById(R.id.score_label);
                        finalScore.setText("Score: " + score);
                        mContinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                b.putString(NAME_KEY, null);
                                b.putInt(SCORE_KEY, score);
                                Intent intent = new Intent(PlayEasy.this, ScoreActivity.class);
                                intent.putExtras(b);
                                startActivity(intent);
                                setContentView(R.layout.activity_score_board);
                            }
                        });

                        mReplay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(PlayEasy.this, PlayEasy.class);
                                startActivity(intent);
                                setContentView(R.layout.activity_easy_gameplay);
                            }
                        });

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                    db.close();
                    start_button.setVisibility(View.VISIBLE);

                } else if (left > 0) {
                    theGameActions();
                }
            }
        }, fps);
    }
}
