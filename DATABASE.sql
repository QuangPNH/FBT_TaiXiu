create database FBT_TaiXiu
use FBT_TaiXiu

CREATE TABLE Users (
    id INT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255)
);

CREATE TABLE GameRecords (
    id INT PRIMARY KEY,
    userId INT,
    gamemode INT,
    totalEarnings FLOAT,
    totalRound INT,
    totalWin INT,
    totalLose INT,
    playedAt TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Users(id)
);

CREATE TABLE NewsItems (
    id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    imageUrl VARCHAR(255),
    content TEXT NOT NULL,
    author VARCHAR(255),
    date DATETIME,
    typeId INT
);

CREATE TABLE QAItems (
    id INT PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL
);

CREATE TABLE MenuItems (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    category INT NOT NULL
);