package com.alexzh.multiplethemesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class LeaderBoardActivity extends SingleFragmentActivity {

    private static final String EXTRA_PLAYER_ID =
            "com.alexzh.multiplethemesapp.player_id";

    public static Intent newIntent(Context packageContext, UUID playerId) {
        Intent intent = new Intent(packageContext, LeaderBoardActivity.class);
        intent.putExtra(EXTRA_PLAYER_ID, playerId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID playerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PLAYER_ID);
        return LeaderBoardFragment.newInstance(playerId);
    }
}
