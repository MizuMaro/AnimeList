package com.example.animelist.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeGenreList {

    @SerializedName("AnimeGenreList")
    public ArrayList<AnimeGenre> list;

    public AnimeGenre getAnime(String genre){
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
