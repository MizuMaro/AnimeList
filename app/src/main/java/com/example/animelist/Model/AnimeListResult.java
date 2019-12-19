package com.example.animelist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Model qui permet de serialize le resultat lorsqu'on recupere la liste des animes par genre
 */
public class AnimeListResult {

    @SerializedName("anime")
    @Expose
    public ArrayList<AnimePreview> anime;

    public AnimePreview getAnimePreview(String genre){
        for(AnimePreview g : anime){
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
