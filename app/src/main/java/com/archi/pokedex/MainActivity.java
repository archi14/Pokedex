package com.archi.pokedex;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public ArrayList<RetroPokemon>  pokemons = new ArrayList<>();
    boolean visited[] = new boolean[200];
    int count =1;
    int TotalCount =200;
    TextView text;
    ArrayList<PokemonFront> pokemonFronts;
    //String frontUrl = "https://pokeapi.co/api/v2/pokemon-form/";
    String baseUrl = "https://pokeapi.co/api/v2/pokemon/";
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ImageView image1;
    ImageButton left,right;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recyclerView = findViewById(R.id.view);
        image1 = findViewById(R.id.pokeimage);
        text = findViewById(R.id.name);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1)
                {
                    count--;
                    if(visited[count]==false)
                    {
                        start();
                    }else
                    {
                        generateDataList(pokemons.get(count-1));
                    }
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(visited[count]==false)
                {
                    start();
                }else
                {
                    generateDataList(pokemons.get(count-1));
                }

            }
        });
        if(retrofit==null)
        {
            retrofit = new retrofit2.Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        if(visited[count]!=true)
        {
            start();
        }else
        {
            generateDataList(pokemons.get(count-1));
        }


        //pokemonFronts = new ArrayList<>();
        //call(frontUrl,count);

    }

    private void start() {
        GetDataService service = retrofit.create(GetDataService.class);
        retrofit2.Call<RetroPokemon> call = service.getAllInfo(count);
        call.enqueue(new retrofit2.Callback<RetroPokemon>() {
            @Override
            public void onResponse(retrofit2.Call<RetroPokemon> call, retrofit2.Response<RetroPokemon> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<RetroPokemon> call, Throwable t) {

            }
        });
    }

    private void generateDataList(RetroPokemon body) {
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.downloader(new OkHttp3Downloader(getApplicationContext()));
        builder.build().load(body.getSprites().getFront_default()).into(image1);
        text.setText(body.getName());
    }

/*    public void call(String url,int count)
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
    }*/


    /*public void parseJson(String s)
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
    }*/
}
