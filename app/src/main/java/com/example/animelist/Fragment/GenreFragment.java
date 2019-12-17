package com.example.animelist.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private RecyclerView recyclerView;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AnimeGenreList genreList = null ;
        try {
            String myJson = inputStreamToString(getContext().getAssets().open("AnimeGenre.json"));

            genreList = new Gson().fromJson(myJson, AnimeGenreList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view= inflater.inflate(R.layout.fragment_genre, container, false);
        recyclerView =view.findViewById(R.id.recycler_genre);
        GenreAdapter adapter = new GenreAdapter(genreList, Glide.with(this),GenreFragment.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());

        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(container.getContext(),DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }





}
