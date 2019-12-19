package com.example.animelist.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.animelist.Adapter.GenreAdapter;
import com.example.animelist.Model.AnimeGenreList;
import com.example.animelist.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GenreFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private ProgressBar progressBar;



    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AnimeGenreList genreList = null ;
        view= inflater.inflate(R.layout.fragment_genre, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarGenre);
        progressBar.setVisibility(View.VISIBLE);
        try {
            String myJson = inputStreamToString(getContext().getAssets().open("AnimeGenre.json"));

            genreList = new Gson().fromJson(myJson, AnimeGenreList.class);

            recyclerView =view.findViewById(R.id.recycler_genre);
            recyclerView.setVisibility(view.VISIBLE);

            GenreAdapter adapter = new GenreAdapter(genreList, Glide.with(this),GenreFragment.this);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());

            recyclerView.setLayoutManager(layoutManager);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(container.getContext(),DividerItemDecoration.VERTICAL);

            recyclerView.addItemDecoration(itemDecoration);
            progressBar.setVisibility(View.GONE);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }





}
