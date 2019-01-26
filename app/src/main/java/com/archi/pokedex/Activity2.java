package com.archi.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        String url = getIntent().getStringExtra("url");
        int count = getIntent().getIntExtra("count",0);

    }
}
