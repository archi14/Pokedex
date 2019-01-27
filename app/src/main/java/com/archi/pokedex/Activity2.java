package com.archi.pokedex;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Activity2 extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Bundle bundle = getIntent().getExtras();
        String url =bundle.getString("url");
        String Image = bundle.getString("image");
        image = findViewById(R.id.image);
        Picasso.get().load(Image).into(image);


    }
}
