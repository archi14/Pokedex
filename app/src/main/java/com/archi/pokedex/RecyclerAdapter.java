package com.archi.pokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<PokemonFront> pokemonFronts;
    public RecyclerAdapter(Context context, ArrayList<PokemonFront> pokemonFronts)
    {
        this.context = context;
        this.pokemonFronts = pokemonFronts;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewimage,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final PokemonFront pokemonFront = pokemonFronts.get(position);
        Picasso.get().load(pokemonFront.getImage()).into(holder.image);
        holder.name.setText(pokemonFront.getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",pokemonFront.getUrl());
                bundle.putString("image",pokemonFront.getImage());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pokemonFronts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

        }
    }
}
