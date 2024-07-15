package com.example.prm392_taixiufbt.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.time.LocalDateTime;
@Entity
public class GameRecord {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "userId")
    public int userId;
    @ColumnInfo(name = "gamemode")
    public int gamemode;

    @ColumnInfo(name = "total_earnings")
    public float totalEarnings;

    @ColumnInfo(name = "total_round")
    public int totalRound;
    @ColumnInfo(name = "total_win")
    public int totalWin;
    @ColumnInfo(name = "total_lose")
    public int totalLose;
    @ColumnInfo(name = "played_at")
    public LocalDateTime playedAt;

    public GameRecord() {
    }

    public GameRecord(int id, int userId, int gamemode, float totalEarnings, int totalRound, int totalWin, int totalLose, LocalDateTime playedAt) {
        this.id = id;
        this.userId = userId;
        this.gamemode = gamemode;
        this.totalEarnings = totalEarnings;
        this.totalRound = totalRound;
        this.totalWin = totalWin;
        this.totalLose = totalLose;
        this.playedAt = playedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public float getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(float totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(int totalRound) {
        this.totalRound = totalRound;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
    }

    public int getTotalLose() {
        return totalLose;
    }

    public void setTotalLose(int totalLose) {
        this.totalLose = totalLose;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }
}
