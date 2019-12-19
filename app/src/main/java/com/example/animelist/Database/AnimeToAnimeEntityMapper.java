package com.example.animelist.Database;

import com.example.animelist.Database.AnimeEntity;
import com.example.animelist.Model.AnimeDetail;

public class AnimeToAnimeEntityMapper {

    public static AnimeEntity map(AnimeDetail animeDetail) {
        AnimeEntity animeEntity = new AnimeEntity();
        animeEntity.setId(animeDetail.getId()+"");
        animeEntity.setTitle(animeDetail.getTitle());
        animeEntity.setDuration(animeDetail.getDuration());
        animeEntity.setEpisodes(animeDetail.getEpisodes());
        animeEntity.setImage_url(animeDetail.getImage_url());
        animeEntity.setPremiered(animeDetail.getPremiered());
        animeEntity.setScore((long)animeDetail.getScore());
        animeEntity.setSynopsis(animeDetail.getSynopsis());
        animeEntity.setType(animeDetail.getType());
        animeEntity.setStatus(animeDetail.getStatus());
        animeEntity.setTrailer_url(animeDetail.getTrailer_url());
        animeEntity.setUrl(animeDetail.getUrl());
        return animeEntity;
    }
}
