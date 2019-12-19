package com.example.animelist.Fragment;

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
import com.example.animelist.Adapter.SearchListAdapter;
import com.example.animelist.Api.ApiInterface;
import com.example.animelist.Api.RetrofitClientInstance;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.Model.AnimeSearchResult;
import com.example.animelist.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private View view;
    RequestManager requestManager;
    private ProgressBar progressBar;
    AnimeSearchResult result;
    private RecyclerView recyclerView;
    private SearchView searchView;
    ApiInterface api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_search, container, false);
        searchView =(SearchView) view.findViewById(R.id.search);
        prepareSearchView();
        requestManager= Glide.with(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarSearch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_search);
        return view;
    }


    private void prepareSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Timer timer = new Timer();

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (s.length() < 3){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    timer.cancel();
                    timer = new Timer();
                    int sleep = 350;
                    if (s.length() <= 4)
                        sleep = 500;
                    else if (s.length() <= 5)
                        sleep = 300;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getListByGenre(s);
                        }
                    }, sleep);
                }
                return true;
            }
        });
    }


    private void getListByGenre(String query){
        /*Create handle for the RetrofitInstance interface*/
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeSearchResult> call = api.searchAnimeDetail(query);
        call.enqueue(new Callback<AnimeSearchResult>() {
            @Override
            public void onResponse(Call<AnimeSearchResult> call, Response<AnimeSearchResult> response) {
                if(response.isSuccessful()) {
                    result = response.body();
                    generateList();
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<AnimeSearchResult> call, Throwable t) {
                System.out.println("Anime by Genre" + "Failed to get "+t.getMessage());
                Toast.makeText(view.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void generateList() {
        recyclerView.setVisibility(view.VISIBLE);
        progressBar.setVisibility(View.GONE);
        SearchListAdapter adapter = new SearchListAdapter(result,requestManager) ;
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);


    }

}
