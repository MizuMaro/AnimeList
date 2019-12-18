package com.example.animelist.Api;

import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.Model.AnimeListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

        @GET("v3/genre/anime/{mal_id}")
        Call<AnimeListResult> getAnimeByGenrec(@Path("mal_id")int genreId);

        @GET("v3/anime/{mal_id}")
        Call<AnimeDetail> getAnimeDetail(@Path("mal_id")int id);
}
