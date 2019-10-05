package com.archi.pokedex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("{id}/")
    Call<RetroPokemon> getAllInfo(@Path("id") int pokeid);
}
