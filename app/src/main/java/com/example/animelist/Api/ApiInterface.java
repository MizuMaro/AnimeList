package com.example.animelist.Api;

import com.example.animelist.Model.AnimeListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

        @GET("anime/{mal_id}/1")
        Call<AnimeListResult> getAnimeByGenre(@Path("mal_id")int genreId);
}
