package com.archi.pokedex;

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
    TextView height,id,name;
    Button next,previous,btn;
    int count =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn =findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkCall("https://pokeapi.co/api/v2/pokemon/"+count+"/");
            }
        });

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
                final Pokemon pokemon = parseJson(result);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image = findViewById(R.id.image);
                        name = findViewById(R.id.name);
                        id = findViewById(R.id.id);
                        height = findViewById(R.id.height);

                        Picasso.get().load(pokemon.getImage()).into(image);
                        name.setText(pokemon.getName());
                        id.setText(String.valueOf(pokemon.getId()));
                        height.setText(String.valueOf(pokemon.getHeight()));
                    }
                });
            }
        });


    }


    public Pokemon parseJson(String s)
    {
        try {
            JSONObject root = new JSONObject(s);
            int height = root.getInt("height");
            int id = root.getInt("id");
            JSONArray array = root.getJSONArray("forms");
            JSONObject jsonObject = array.getJSONObject(0);
             String name =jsonObject.getString("name");
            String image = root.getJSONObject("sprites").getString("front_default");
            Pokemon pokemon = new Pokemon(image,name,height,id);
            Log.d("Parsing", "object created");
            return pokemon;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("PARSING", "some kind of error ");
        return new Pokemon();
    }
}
