package com.archi.pokedex;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    int count =1;
    int TotalCount =200;
    ArrayList<PokemonFront> pokemonFronts;
    String frontUrl = "https://pokeapi.co/api/v2/pokemon-form/";
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ImageView image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.view);
        pokemonFronts = new ArrayList<>();
        call(frontUrl,count);

    }

    public void call(String url,int count)
    {
        NetworkCall(url+count+"/");
    }


   public void NetworkCall(final String url)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                            .url(url)
                            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                parseJson(result);
                count++;
                call(frontUrl,count);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("size", String.valueOf(pokemonFronts.size()));
                            if(pokemonFronts.size()<=1)
                            {
                                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),pokemonFronts);
                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                                recyclerView.setAdapter(recyclerAdapter);
                            }else
                            {
                                recyclerAdapter.notifyDataSetChanged();
                            }



                    }
                });
            }
        });
    }


    public void parseJson(String s)
    {
        try {
            JSONObject root = new JSONObject(s);
            int id = root.getInt("id");
            JSONObject pokemon = root.getJSONObject("pokemon");
            String name =pokemon.getString("name");
            String url = pokemon.getString("url");
            String image = root.getJSONObject("sprites").getString("front_default");
            PokemonFront pokFront = new PokemonFront(name,url,id,image);
            pokemonFronts.add(pokFront);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
