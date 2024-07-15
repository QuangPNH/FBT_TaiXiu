package com.example.prm392_taixiufbt.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithGameRecord {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<GameRecord> gameRecords;
}
