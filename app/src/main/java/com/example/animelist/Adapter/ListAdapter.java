package com.example.animelist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.animelist.Model.AnimeGenreList;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.GenreViewHolder> implements View.OnClickListener{
    AnimeListResult animeListResult ;
    private final RequestManager glide;
    public ListAdapter(AnimeListResult animeListResult, RequestManager glide) {
        this.animeListResult = animeListResult;
        this.glide= glide;
        //chargement de toutes les images de categories

    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View genreView = inflater.inflate(R.layout.genre_item,parent,false);
        GenreViewHolder viewHolder = new GenreViewHolder(genreView);
        genreView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        AnimeListResult.AnimePreview genre = animeListResult.anime.get(position);
        String current = genre.getName();
        holder.txtGenre.setText(current);
        glide.load(genre.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return animeListResult.anime == null ? 0 : animeListResult.anime.size();
    }

    @Override
    public void onClick(View v) {
        AnimeListResult.AnimePreview selected ;
        TextView genre  = v.findViewById(R.id.genreTextView);
        Context context = v.getContext();
        String current = genre.getText().toString();
        selected = animeListResult.getAnimePreview(current);

        Toast.makeText(context,selected.getId()+"", Toast.LENGTH_SHORT).show();
    }

    public class  GenreViewHolder extends RecyclerView.ViewHolder {
        TextView txtGenre ;
        ImageView img;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGenre = itemView.findViewById(R.id.genreTextView);
            img = (ImageView) itemView.findViewById(R.id.genreImage);

        }
    }


}
