package com.example.animelist.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Base de donnée
 */
@Database(entities = {AnimeEntity.class}, version = 1,exportSchema = false )
public abstract class AnimeDatabase extends RoomDatabase {
    public abstract AnimeDao animeDao();
}
