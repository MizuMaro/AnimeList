package com.example.animelist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.animelist.DetailAnime;
import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.GenreViewHolder> implements View.OnClickListener{
    List<AnimeDetail> animeListResult ;
    private final RequestManager glide;
    public FavListAdapter(List animeListResult, RequestManager glide) {
        this.animeListResult = animeListResult;
        this.glide= glide;

    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View genreView = inflater.inflate(R.layout.fav_item,parent,false);
        GenreViewHolder viewHolder = new GenreViewHolder(genreView);
        genreView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        AnimeDetail anime = animeListResult.get(position);



        holder.txtTitle.setText(anime.getTitle());
        holder.txtEpisodes.setText(anime.getEpisodes()+ " episodes");
        holder.txtType.setText(anime.getType());
        glide.load(anime.getImage_url()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (animeListResult != null)
            return animeListResult == null ? 0 : animeListResult.size();
        else
            return 0;
    }

    @Override
    public void onClick(View v) {
        AnimeDetail selected = null;

        TextView genre  = v.findViewById(R.id.favTitleTextView);
        String current = genre.getText().toString();
        for(AnimeDetail s : animeListResult )
        {
            if(s.getTitle().contains(current)){
                selected = s;
            }
        }
        Intent myIntent = new Intent(v.getContext(), DetailAnime.class);
        myIntent.putExtra("animeDetail",selected); //Optional parameters
        v.getContext().startActivity(myIntent);
    }

    public class  GenreViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle ;
        ImageView img;
        TextView txtEpisodes;
        TextView txtType;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEpisodes =  (TextView) itemView.findViewById(R.id.favEpisodesTextView);
            txtTitle =  (TextView)itemView.findViewById(R.id.favTitleTextView);
            txtType = (TextView) itemView.findViewById(R.id.favTypeTextView);
            img = (ImageView) itemView.findViewById(R.id.favImage);

        }
    }


}
