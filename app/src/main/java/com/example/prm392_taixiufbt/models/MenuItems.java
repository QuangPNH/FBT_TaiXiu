package com.example.prm392_taixiufbt.models;

public class MenuItems {
    private String name;
    private String image;
    private int category;
    //1 is GameMode
    //0 is MainFeatures

    public MenuItems(String name, String image, int category) {
        this.name = name;
        this.image = image;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public MenuItems() {
    }
}
