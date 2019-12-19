package com.example.animelist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeSearchResult {

    @SerializedName("results")
    @Expose
    public ArrayList<AnimePreview> results;

    public AnimePreview getAnimePreview(String genre){
        for(AnimePreview g : results){
            if(g.getName().equalsIgnoreCase(genre)){
                return g;
            }
        }
        return null;
    }



    static public class AnimePreview{
        @SerializedName("mal_id")
        private int id;
        @SerializedName("episodes")
        private int episodes;
        @SerializedName("title")
        private String name;
        @SerializedName("type")
        private String type;
        @SerializedName("image_url")
        private String image;

        public int getId() {
            return id;
        }
        public int getEpisodes() {
            return episodes;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getImage() {
            return image;
        }

    }

}
