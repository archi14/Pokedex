package com.archi.pokedex;

public class PokemonFront {
    String name;
    String image;
    String url;
    int id;

    public PokemonFront()
    {

    }
    public PokemonFront(String name, String url, int id,String image) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.image =image;
    }

    public String getName() {
        return name;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
