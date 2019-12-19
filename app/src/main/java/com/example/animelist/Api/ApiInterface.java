package com.example.animelist.Api;

import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.Model.AnimeListResult;
import com.example.animelist.Model.AnimeSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface des appels à l'api
 */
public interface ApiInterface {
        /**
         * Recupere les animes par genre
         * @param genreId
         * @return
         */
        @GET("genre/anime/{mal_id}")
        Call<AnimeListResult> getAnimeByGenrec(@Path("mal_id")int genreId);

        /**
         * Recupere le detail d'un anime par son id
         * @param id
         * @return
         */
        @GET("anime/{mal_id}")
        Call<AnimeDetail> getAnimeDetail(@Path("mal_id")int id);

        /**
         * Recupere le resultat de la recherche par nom
         * @param query
         * @return
         */
        @GET("search/anime?")
        Call<AnimeSearchResult> searchAnimeDetail(@Query("q") String query);
}
