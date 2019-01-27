package com.archi.pokedex;

import java.util.ArrayList;

public class PokemonItem {
    ArrayList<String> abilities;
    int weight;
    int height;

    public PokemonItem(ArrayList<String> abilities, int weight, int height) {

        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
    }
    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
