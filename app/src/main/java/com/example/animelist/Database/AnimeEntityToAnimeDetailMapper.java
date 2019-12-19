package com.example.animelist.Database;

import com.example.animelist.Model.AnimeDetail;

public class AnimeEntityToAnimeDetailMapper {

    public static AnimeDetail map(AnimeEntity animeEntity) {
        AnimeDetail animeDetail = new AnimeDetail();
        animeDetail.setId(Integer.parseInt(animeEntity.getId()));
        animeDetail.setTitle(animeEntity.getTitle());
        animeDetail.setDuration(animeEntity.getDuration());
        animeDetail.setEpisodes(animeEntity.getEpisodes());
        animeDetail.setImage_url(animeEntity.getImage_url());
        animeDetail.setPremiered(animeEntity.getPremiered());
        animeDetail.setScore((long)animeEntity.getScore());
        animeDetail.setSynopsis(animeEntity.getSynopsis());
        animeDetail.setType(animeEntity.getType());
        animeDetail.setStatus(animeEntity.getStatus());
        animeDetail.setTrailer_url(animeEntity.getTrailer_url());
        animeDetail.setUrl(animeEntity.getUrl());
        return animeDetail;
    }
}
