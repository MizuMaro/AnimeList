package com.example.animelist.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

    public GenreSelectedFragment(int genreId) {
        this.genreId = genreId;
    }

    AnimeListResult result;
    private RecyclerView recyclerView;
    private Button tryAgain;
    private View view;
    ApiInterface api;

    private void getListByGenre(){
        /*Create handle for the RetrofitInstance interface*/
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeListResult> call = api.getAnimeByGenrec(genreId);
        call.enqueue(new Callback<AnimeListResult>() {
            @Override
            public void onResponse(Call<AnimeListResult> call, Response<AnimeListResult> response) {
                if(response.isSuccessful()) {
                    result = response.body();
                    generateList();
                }else{
                    tryAgain.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

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


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_genre, container, false);
        recyclerView =view.findViewById(R.id.recycler_genre);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarGenre);
        progressBar.setVisibility(View.VISIBLE);

        tryAgain = (Button) view.findViewById(R.id.retry);
        tryAgain.setVisibility(View.GONE);
        tryAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                tryAgain.setVisibility(View.GONE);
                getListByGenre();
            }

        });

        getListByGenre();
        return view;
    }

        public void generateList() {
        recyclerView.setVisibility(view.VISIBLE);
        tryAgain.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        ListAdapter adapter = new ListAdapter(result,requestManager) ;
        recyclerView.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),3);

        recyclerView.setLayoutManager(layoutManager);


    }
}
