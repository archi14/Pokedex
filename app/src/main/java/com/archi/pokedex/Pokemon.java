package com.archi.pokedex;

public class Pokemon {
    String image;
    String name;
    int height;
    int id;

    public Pokemon() {
    }

    public Pokemon(String image, String name, int height, int id) {
        this.image = image;
        this.name = name;
        this.height = height;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
