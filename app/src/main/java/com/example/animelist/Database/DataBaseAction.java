package com.example.animelist.Database;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.animelist.Model.AnimeDetail;

import androidx.room.Room;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Class qui permet d'initialiser la base de donnée et effectuer des requetes
 */
public class DataBaseAction {

    Context apContext;
    AnimeDatabase db;


    /**
     * Associe le context de l'application
     * @param context
     */
    public DataBaseAction(Context context) {
        this.apContext=context;
    }

    /**
     * Initialise la base AnimeDatabase is elle n'est pas initialisé
     * @return
     */
    public AnimeDatabase getDb(){
        if (db == null) {
            db = Room.databaseBuilder(apContext,
                    AnimeDatabase.class, "database-anime").build();
        }
        return db;
    }

    /**
     * Retire de la base des favoris l'anime en parametre
     * @param result
     */
    @SuppressLint("CheckResult")
    public void removeFromFav(AnimeDetail result){
        getDb();

        db.animeDao().deleteAnimeFromFavorites(result.getId() + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        System.out.println("Supprimé de la liste");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("erreur : "+e);

                    }
                });
    }

    /**
     * Ajoute dans la base des favoris l'anime en parametre
     * @param result
     */
    @SuppressLint("CheckResult")
    public void addToFav(AnimeDetail result){
        getDb();
        db.animeDao().addAnimeToFavorites(AnimeToAnimeEntityMapper.map(result)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        System.out.println("Ajouté à la liste");

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("erreur :"+e);

                    }
                });
    }




}
