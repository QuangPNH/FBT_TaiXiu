package com.example.prm392_taixiufbt.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class GameProfile implements Serializable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "roll1Rate")
    public boolean roll1Rate;
    @ColumnInfo(name = "roll2Rate")
    public boolean roll2Rate;
    @ColumnInfo(name = "roll3Rate")
    public boolean roll3Rate;
    @ColumnInfo(name = "roll4Rate")
    public boolean roll4Rate;
    @ColumnInfo(name = "roll5Rate")
    public boolean roll5Rate;
    @ColumnInfo(name = "roll6Rate")
    public boolean roll6Rate;
    @ColumnInfo(name = "roll7Rate")
    public boolean roll7Rate;
    @ColumnInfo(name = "roll8Rate")
    public boolean roll8Rate;
    @ColumnInfo(name = "roll9Rate")
    public boolean roll9Rate;
    @ColumnInfo(name = "roll10Rate")
    public boolean roll10Rate;
    @ColumnInfo(name = "isActive")
    public boolean isActive;
    public GameProfile() {
    }

    public GameProfile(int id, String title, boolean roll1Rate, boolean roll2Rate, boolean roll3Rate, boolean roll4Rate, boolean roll5Rate, boolean roll6Rate, boolean roll7Rate, boolean roll8Rate, boolean roll9Rate, boolean roll10Rate,boolean isActive) {
        this.id = id;
        this.title = title;
        this.roll1Rate = roll1Rate;
        this.roll2Rate = roll2Rate;
        this.roll3Rate = roll3Rate;
        this.roll4Rate = roll4Rate;
        this.roll5Rate = roll5Rate;
        this.roll6Rate = roll6Rate;
        this.roll7Rate = roll7Rate;
        this.roll8Rate = roll8Rate;
        this.roll9Rate = roll9Rate;
        this.roll10Rate = roll10Rate;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRoll1Rate() {
        return roll1Rate;
    }

    public void setRoll1Rate(boolean roll1Rate) {
        this.roll1Rate = roll1Rate;
    }

    public boolean isRoll2Rate() {
        return roll2Rate;
    }

    public void setRoll2Rate(boolean roll2Rate) {
        this.roll2Rate = roll2Rate;
    }

    public boolean isRoll3Rate() {
        return roll3Rate;
    }

    public void setRoll3Rate(boolean roll3Rate) {
        this.roll3Rate = roll3Rate;
    }

    public boolean isRoll4Rate() {
        return roll4Rate;
    }

    public void setRoll4Rate(boolean roll4Rate) {
        this.roll4Rate = roll4Rate;
    }

    public boolean isRoll5Rate() {
        return roll5Rate;
    }

    public void setRoll5Rate(boolean roll5Rate) {
        this.roll5Rate = roll5Rate;
    }

    public boolean isRoll6Rate() {
        return roll6Rate;
    }

    public void setRoll6Rate(boolean roll6Rate) {
        this.roll6Rate = roll6Rate;
    }

    public boolean isRoll7Rate() {
        return roll7Rate;
    }

    public void setRoll7Rate(boolean roll7Rate) {
        this.roll7Rate = roll7Rate;
    }

    public boolean isRoll8Rate() {
        return roll8Rate;
    }

    public void setRoll8Rate(boolean roll8Rate) {
        this.roll8Rate = roll8Rate;
    }

    public boolean isRoll9Rate() {
        return roll9Rate;
    }

    public void setRoll9Rate(boolean roll9Rate) {
        this.roll9Rate = roll9Rate;
    }

    public boolean isRoll10Rate() {
        return roll10Rate;
    }

    public void setRoll10Rate(boolean roll10Rate) {
        this.roll10Rate = roll10Rate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
