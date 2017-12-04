package com.charlyan.dontopenthedoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Charlie on 11/7/17.
 */

public class DifficultyActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_difficulty_selection);

        Button easyButton = (Button)findViewById(R.id.easy_button);
        Button mediumButton = (Button)findViewById(R.id.medium_button);
        Button hardButton = (Button)findViewById(R.id.hard_button);

    }

    public void EasyButtonClick(View view) {

        Intent intent = new Intent(DifficultyActivity.this, PlayEasy.class);
        startActivity(intent);
        setContentView(R.layout.activity_easy_gameplay);
    }

    public void MediumButtonClick(View view) {

        Intent intent = new Intent(DifficultyActivity.this, PlayMedium.class);
        startActivity(intent);
        setContentView(R.layout.activity_medium_gameplay);
    }


    public void HardButtonClick(View view) {
        Intent intent = new Intent(DifficultyActivity.this, PlayHard.class);
        startActivity(intent);
        setContentView(R.layout.activity_hard_gameplay);
    }

    public void BackButtonClick(View view) {
        Intent intent = new Intent(DifficultyActivity.this, MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}
