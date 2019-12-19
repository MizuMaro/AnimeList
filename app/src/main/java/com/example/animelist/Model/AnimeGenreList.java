package com.example.animelist.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model qui permet de serialize la liste des genre depuis l'asset  AnimeGenre.json
 * Asset Crée par moi même afin de pouvoir utiliser l'appel avec un genre en parametre
 */
public class AnimeGenreList {

    @SerializedName("AnimeGenreList")
    public ArrayList<AnimeGenre> list;

    public AnimeGenre getAnimeGenre(String genre){
        for(AnimeGenre g : list){
            if(g.getName().equalsIgnoreCase(genre)){
                return g;
            }
        }
        return null;
    }

    static public class AnimeGenre {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("nsfw")
        private boolean nsfw;
        @SerializedName("image")
        private String image;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isNsfw() {
            return nsfw;
        }

        public String getImage() {
            return image;
        }
    }

}
