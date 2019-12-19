package com.example.animelist.Api;

import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.Model.AnimeSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

        @GET("genre/anime/{mal_id}")
        Call<AnimeListResult> getAnimeByGenrec(@Path("mal_id")int genreId);

        @GET("anime/{mal_id}")
        Call<AnimeDetail> getAnimeDetail(@Path("mal_id")int id);

        @GET("search/anime?")
        Call<AnimeSearchResult> searchAnimeDetail(@Query("q") String query);
}
