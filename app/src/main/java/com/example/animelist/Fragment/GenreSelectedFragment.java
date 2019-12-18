package com.example.animelist.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.animelist.Adapter.ListAdapter;
import com.example.animelist.Api.ApiInterface;
import com.example.animelist.Api.RetrofitClientInstance;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class GenreSelectedFragment extends Fragment {
    int genreId;
    RequestManager requestManager;
    public GenreSelectedFragment(int genreId) {
        this.genreId = genreId;
    }

    AnimeListResult result;
    private RecyclerView recyclerView;
    private View view;
    ApiInterface api;

    private void getListByGenre(){
        /*Create handle for the RetrofitInstance interface*/
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeListResult> call = api.getAnimeByGenrec(genreId);
        call.enqueue(new Callback<AnimeListResult>() {
            @Override
            public void onResponse(Call<AnimeListResult> call, Response<AnimeListResult> response) {
                result = response.body();
                generateList();

            }

            @Override
            public void onFailure(Call<AnimeListResult> call, Throwable t) {
                System.out.println("Anime by Genre" + "Failed to get "+t.getMessage());
                Toast.makeText(view.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestManager=Glide.with(this);
        getListByGenre();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_genre, container, false);

        return view;
    }

    public void generateList() {
        recyclerView =view.findViewById(R.id.recycler_genre);

        ListAdapter adapter = new ListAdapter(result,requestManager) ;
        recyclerView.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),3);

        recyclerView.setLayoutManager(layoutManager);


    }
}
