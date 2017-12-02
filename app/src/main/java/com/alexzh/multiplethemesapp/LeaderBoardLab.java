package com.alexzh.multiplethemesapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexzh.multiplethemesapp.database.DoorGameBaseHelper;
import com.alexzh.multiplethemesapp.database.DoorGameCursorWrapper;
import com.alexzh.multiplethemesapp.database.DoorGameDbSchema.PlayerTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lynnreilly on 11/27/17.
 */


public class LeaderBoardLab {
    private static LeaderBoardLab sLeaderBoardLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LeaderBoardLab get(Context context) {
        if (sLeaderBoardLab == null) {
            sLeaderBoardLab = new LeaderBoardLab(context);
        }
        return sLeaderBoardLab;
    }

    private LeaderBoardLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DoorGameBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addPlayer(Player p) {
        ContentValues values = getContentValues(p);

        mDatabase.insert(PlayerTable.NAME, null, values);
    }

    public List<Player> getPlayers() {
        //return new ArrayList<>();
        List<Player> players = new ArrayList<>();

        DoorGameCursorWrapper cursor = queryPlayers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                players.add(cursor.getPlayer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return players;

    }

    public Player getPlayer(UUID id) {
        DoorGameCursorWrapper cursor = queryPlayers(
                PlayerTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPlayer();
        } finally {
            cursor.close();
        }
        // return null;
    }

    public void updatePlayer(Player player) {
        String uuidString = player.getId().toString();
        ContentValues values = getContentValues(player);

        mDatabase.update(PlayerTable.NAME, values,
                PlayerTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private DoorGameCursorWrapper queryPlayers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PlayerTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new DoorGameCursorWrapper(cursor);
        return null;
    }

    private static ContentValues getContentValues(Player player) {
        ContentValues values = new ContentValues();
        values.put(PlayerTable.Cols.UUID, player.getId().toString());
        values.put(PlayerTable.Cols.PLAYER, player.getPlayer());
        values.put(PlayerTable.Cols.SCORE, player.getScore());

        return values;
    }

}


