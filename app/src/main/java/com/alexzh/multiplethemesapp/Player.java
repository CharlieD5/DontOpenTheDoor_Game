package com.alexzh.multiplethemesapp;

import java.util.UUID;

/**
 * Created by lynnreilly on 10/25/17.
 */

public class Player {
    private UUID mId;
    private String mPlayer;
    private double mScore;

    public Player() {
        this(UUID.randomUUID());
    }

    public Player (UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getPlayer() {
        return mPlayer;
    }

    public void setPlayer(String player) {
        mPlayer = player;
    }

    public double getScore() {
        return mScore;
    }

    public void setScore(double score) {
        mScore = score;
    }
}
