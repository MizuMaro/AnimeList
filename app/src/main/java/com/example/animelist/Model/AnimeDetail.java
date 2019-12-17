package com.example.animelist.Model;

import com.google.gson.annotations.SerializedName;

public class AnimeDetail {



    @SerializedName("mal_id")
    private int id;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String status;
    @SerializedName("episodes")
    private int episodes;
    @SerializedName("score")
    private long score;
    @SerializedName("synopsis")
    private String synopsis;
    @SerializedName("premiered")
    private String premiered;
    @SerializedName("duration")
    private String duration;
    @SerializedName("type")
    private String type;
    @SerializedName("trailer_url")
    private String trailer_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}