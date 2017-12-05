package com.charlyan.dontopenthedoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_NAME =  "name";
    static final String KEY_NEWSCORE = "newScore";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "myDB";
    static final String DATABASE_TABLE = "scores";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table scores (_id integer primary key autoincrement, "
            + "name text not null, newScore text not null);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(DATABASE_CREATE);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version" + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS scores");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long insertScore (String name, String newScore) {
        ContentValues scoreBoard = new ContentValues();
        scoreBoard.put(KEY_NAME, name);
        scoreBoard.put(KEY_NEWSCORE, newScore);
        return db.insert(DATABASE_TABLE, null, scoreBoard);
    }

    public boolean deleteScore (long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllScores() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_NEWSCORE},
                null, null, null, null, null);
    }

    public Cursor getScore (long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_NEWSCORE},
                KEY_ROWID + "=" + rowId,null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateScore (long rowId, String name, String newScore) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_NEWSCORE, newScore);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public List<Scores> getListOfScores() {
        Cursor cursor = getAllScores();
        List<Scores> scoresList = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {
                Scores singleScore = new Scores();
                singleScore.setName(cursor.getString(1));
                singleScore.setScore(cursor.getString(2));
                scoresList.add(singleScore);
            } while (cursor.moveToNext());
        }
        Collections.sort(scoresList, new Comparator<Scores>() {
            @Override
            public int compare(Scores o1, Scores o2) {
                return Integer.parseInt(o2.getScore()) - Integer.parseInt(o1.getScore());
            }
        });

        if (scoresList.size() > 3) {
            do {
                int i = 1;
                scoresList.remove(scoresList.size() - i);
                i++;
            } while (scoresList.size() != 3);
            return scoresList;
        } else if (scoresList.size() < 4) {
            return scoresList;
        }
        return scoresList;
    }
}
