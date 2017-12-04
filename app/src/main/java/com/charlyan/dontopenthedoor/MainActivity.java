package com.charlyan.dontopenthedoor;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main); // Home Screen
        Button play_button = (Button) findViewById(R.id.play_button);
        Button leaderboardButton = (Button)findViewById(R.id.leaderboard_button);
        Button theme_button = (Button) findViewById(R.id.theme_button);
        final ImageButton sound_button = (ImageButton) findViewById(R.id.sound_button);
        final ImageButton vibrate_button = (ImageButton) findViewById(R.id.vibrate_button);

        // Alert Dialog greeting that suggests user to use headphones
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Note:")
                        .setMessage("Plugging in headphones can enhance the game play experience!");
                AlertDialog alertdialog = builder.create();
                alertdialog.show();
            }
        }, 2000);

        //Adds Background Music
        final MediaPlayer player = MediaPlayer.create(this, R.raw.scary);
        player.setLooping(true);
        player.start();
        sound_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // If the music is playing
                if(player.isPlaying()) {
                    // Pause the music player
                    player.pause();
                    sound_button.setBackgroundResource(R.drawable.sound_off);
                }
                // If it's not playing
                else {
                    // Resume the music player
                    player.start();
                    sound_button.setBackgroundResource(R.drawable.sound_on);
                }
            }
        });

//        vibrate_button.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                // If the music is playing
//                if(v.) {
//                    // Pause the music player
//                    player.pause();
//                    sound_button.setBackgroundResource(R.drawable.vibrate_off);
//                }
//                // If it's not playing
//                else {
//                    // Resume the music player
//                    player.start();
//                    sound_button.setVisibility(R.drawable.vibrate);
//                }
//            }
//        });
    }

    public void PlayButtonClick(View view) {
        //setContentView(R.layout.activity_difficulty_selection); // Goes to game difficulty selection: {easy, medium, hard}
        Intent intent = new Intent(MainActivity.this, DifficultyActivity.class);
        startActivity(intent);
    }

    public void LeaderboardButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
    }

    public void ThemeButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ThemesActivity.class);
        startActivity(intent);
    }

}