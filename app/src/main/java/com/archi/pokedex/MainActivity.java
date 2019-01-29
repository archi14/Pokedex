package com.archi.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<MyPojo> list;
    ArrayList<PokemonFront> pokelist;
    public static final String baseurl ="https://pokeapi.co/api/v2/";
    private static Retrofit retrofit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //image = findViewById(R.id.image);
        recyclerView  = findViewById(R.id.view);
        pokelist = new ArrayList<>();
        list = new ArrayList<>();
        for(int i=1;i<800;i++)
        {
            getData(i);
        }

        Log.d("on", String.valueOf(list.size()));

    }

    public void getData(int index)
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();

        }

        final PokeApiSource pokeApiSource =retrofit.create(PokeApiSource.class);
        retrofit2.Call<MyPojo> call = pokeApiSource.getImage(String.valueOf(index));

        call.enqueue(new retrofit2.Callback<MyPojo>() {
            @Override
            public void onResponse(retrofit2.Call<MyPojo> call, retrofit2.Response<MyPojo> response) {
                MyPojo myPojo = response.body();
                list.add(myPojo);
                if(myPojo!=null)
                {
                    pokelist.add(new PokemonFront(myPojo.getName()," ", Integer.valueOf(myPojo.getId()),myPojo.getSprites().getFront_default()));
                }

                Log.d("on", String.valueOf(pokelist.size()));
                if(pokelist.size()<=1)
                {
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(),pokelist);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                    recyclerView.setAdapter(recyclerAdapter);
                }else
                {
                    recyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<MyPojo> call, Throwable t) {
                Log.d("off", t.toString());
            }
        });

    }

}
