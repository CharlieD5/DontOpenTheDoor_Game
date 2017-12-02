package com.alexzh.multiplethemesapp.database;

/**
 * Created by lynnreilly on 11/8/17.
 */

public class DoorGameDbSchema {
    public static final class PlayerTable {
        public static final String NAME = "players";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PLAYER = "player";
            public static final String SCORE = "score";
        }
    }
}
