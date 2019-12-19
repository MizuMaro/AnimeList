package com.example.animelist.Database;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.animelist.Model.AnimeDetail;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class DataBaseAction {

    Context apContext;
    public DataBaseAction(Context context) {
        this.apContext=context;
    }

    AnimeDatabase db;

    public AnimeDatabase getDb(){
        if (db == null) {
            db = Room.databaseBuilder(apContext,
                    AnimeDatabase.class, "database-name").build();
        }
        return db;
    }

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
