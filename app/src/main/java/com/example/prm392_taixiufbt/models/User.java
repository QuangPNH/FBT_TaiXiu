package com.example.prm392_taixiufbt.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "typeId")
    public int typeId;
    @ColumnInfo(name = "money")
    public int money;


    public User() {
    }

    public User(int id, String username, String password, String email, String name, String phone, int typeId,int money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.typeId = typeId;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

