package com.example.animelist.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeGenreList {

    @SerializedName("AnimeGenreList")
    public ArrayList<AnimeGenre> list;

    static public class AnimeGenre {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("nsfw")
        private boolean nsfw;

    }

}
