package com.example.animelist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.animelist.Api.ApiInterface;
import com.example.animelist.Api.RetrofitClientInstance;
import com.example.animelist.Fragment.AnimeListFragment;
import com.example.animelist.Fragment.GenreFragment;
import com.example.animelist.Fragment.SearchFragment;
import com.example.animelist.Model.AnimeListResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_bar);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        getListByGenre();
    }
    ApiInterface api;

    private void getListByGenre(){
        /*Create handle for the RetrofitInstance interface*/
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeListResult> call = api.getAnimeByGenre(1);
        call.enqueue(new Callback<AnimeListResult>() {
            @Override
            public void onResponse(Call<AnimeListResult> call, Response<AnimeListResult> response) {
                System.out.println(response);
                 response.body();

            }

            @Override
            public void onFailure(Call<AnimeListResult> call, Throwable t) {
                System.out.println("Anime by Genre" + "Failed to get "+t.getMessage());
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment selected = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.genre:
                            selected = new GenreFragment();
                            break;
                        case R.id.myList :
                            selected = new AnimeListFragment();
                            break;
                        case R.id.search :
                            selected = new SearchFragment();
                            break;

                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selected)
                            .commit();
                    return true;
                }
            };

}
