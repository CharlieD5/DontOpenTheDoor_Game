package com.alexzh.multiplethemesapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.alexzh.multiplethemesapp.Player;
import com.alexzh.multiplethemesapp.database.DoorGameDbSchema.PlayerTable;

import java.util.UUID;

/**
 * Created by lynnreilly on 11/15/17.
 */

public class DoorGameCursorWrapper extends CursorWrapper{
    DoorGameCursorWrapper (Cursor cursor) {
        super(cursor);
    }

    public Player getPlayer() {
        String uuidString = getString(getColumnIndex(PlayerTable.Cols.UUID));
        String player = getString(getColumnIndex(PlayerTable.Cols.PLAYER));
        Double score = getDouble(getColumnIndex(PlayerTable.Cols.SCORE));

        Player p = new Player(UUID.fromString(uuidString));
        p.setPlayer(player);
        p.setScore(score);

        return p;
    }
}
