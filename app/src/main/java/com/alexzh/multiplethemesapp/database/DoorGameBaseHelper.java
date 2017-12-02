package com.alexzh.multiplethemesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexzh.multiplethemesapp.database.DoorGameDbSchema.PlayerTable;

/**
 * Created by lynnreilly on 11/8/17.
 */

public class DoorGameBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dotdg.db";

    public DoorGameBaseHelper(Context context) {
        super(context,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PlayerTable.NAME + "( " + " _id integer primary key autoincrement, " +
                PlayerTable.Cols.UUID + ", " +
                PlayerTable.Cols.PLAYER + ", " +
                PlayerTable.Cols.SCORE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
