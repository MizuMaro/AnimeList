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
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter du recyclerView affichant la liste des animes par genre selectionné
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.GenreViewHolder> implements View.OnClickListener{

    AnimeListResult animeListResult ;
    private final RequestManager glide;

    public ListAdapter(AnimeListResult animeListResult, RequestManager glide) {
        this.animeListResult = animeListResult;
        this.glide= glide;
    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View genreView = inflater.inflate(R.layout.preview_item,parent,false);
        GenreViewHolder viewHolder = new GenreViewHolder(genreView);
        genreView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        AnimeListResult.AnimePreview preview = animeListResult.anime.get(position);



        holder.txtTitle.setText(preview.getName());
        holder.txtEpisodes.setText(preview.getEpisodes()+ " episodes");
        holder.txtType.setText(preview.getType());
        glide.load(preview.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (animeListResult != null)
            return animeListResult.anime == null ? 0 : animeListResult.anime.size();
        else
            return 0;
    }
    /**
     * Methode qui associe l'action a chaque item permettant de faire appel a l'activité DetailAnime affichant le detail de l'anime par son id depuis l'api
     * @param v
     */
    @Override
    public void onClick(View v) {
        AnimeListResult.AnimePreview selected ;
        TextView genre  = v.findViewById(R.id.previewTitleTextView);
        String current = genre.getText().toString();
        selected = animeListResult.getAnimePreview(current);
        Intent myIntent = new Intent(v.getContext(), DetailAnime.class);
        myIntent.putExtra("id", selected.getId()); //Optional parameters
        v.getContext().startActivity(myIntent);
    }

    public class  GenreViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle ;
        ImageView img;
        TextView txtEpisodes;
        TextView txtType;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEpisodes =  (TextView) itemView.findViewById(R.id.previewEpisodesTextView);
            txtTitle =  (TextView)itemView.findViewById(R.id.previewTitleTextView);
            txtType = (TextView) itemView.findViewById(R.id.previewTypeTextView);
            img = (ImageView) itemView.findViewById(R.id.previewImage);

        }
    }


}
