package com.example.animelist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.animelist.Fragment.AnimeListFragment;
import com.example.animelist.Fragment.GenreSelectedFragment;
import com.example.animelist.Model.AnimeGenreList;
import com.example.animelist.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> implements View.OnClickListener{
    AnimeGenreList genreList ;
    Fragment fragment;
    private final RequestManager glide;
    public GenreAdapter(AnimeGenreList genreList, RequestManager glide, Fragment fragment) {
        this.fragment = fragment;
        this.genreList = genreList;
        this.glide= glide;
        //chargement de toutes les images de categories
        for(AnimeGenreList.AnimeGenre a : genreList.list){
            glide.load(a.getImage());
        }
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
        AnimeGenreList.AnimeGenre genre = genreList.list.get(position);
        String current = genre.getName();
        holder.txtGenre.setText(current);
        glide.load(genre.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return genreList.list.size();
    }

    @Override
    public void onClick(View v) {
        AnimeGenreList.AnimeGenre selected ;
        TextView genre  = v.findViewById(R.id.genreTextView);
        String current = genre.getText().toString();
        selected = genreList.getAnimeGenre(current);
        fragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new GenreSelectedFragment(selected.getId()))
                .addToBackStack(null)
                .commit();

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
