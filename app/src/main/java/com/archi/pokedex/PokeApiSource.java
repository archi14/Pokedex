package com.archi.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApiSource {

    @GET ("pokemon/{id}/")

    Call<MyPojo> getImage(@Path("id") String id);

}
