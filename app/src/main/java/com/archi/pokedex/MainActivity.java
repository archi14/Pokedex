package com.archi.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    TextView name;
    Button next,previous,btn;
    int count =1;
    String frontUrl = "https://pokeapi.co/api/v2/pokemon-form/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.prev);
        btn =findViewById(R.id.btn);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(frontUrl,count);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count+=1;
                call(frontUrl,count);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count-=1;
                call(frontUrl,count);
            }
        });

    }
    public void call(String url,int count)
    {
        NetworkCall(url+count+"/");
    }


   public void NetworkCall(String url)
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
                final PokemonFront pokemon = parseJson(result);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Picasso.get().load(pokemon.getImage()).into(image);
                        name.setText(pokemon.getName());
                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(),Activity2.class);
                                intent.putExtra("url",pokemon.getUrl());
                                intent.putExtra("count",count);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


    }


    public PokemonFront parseJson(String s)
    {
        try {
            JSONObject root = new JSONObject(s);
            int id = root.getInt("id");
            JSONObject pokemon = root.getJSONObject("pokemon");
            String name =pokemon.getString("name");
            String url = pokemon.getString("url");
            String image = root.getJSONObject("sprites").getString("front_default");
            PokemonFront pokFront = new PokemonFront(name,url,id,image);
            Log.d("Parsing", "object created");
            return pokFront;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("PARSING", "some kind of error ");
        return new PokemonFront();
    }
}
