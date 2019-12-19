package com.example.animelist.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.animelist.Adapter.FavListAdapter;
import com.example.animelist.Database.AnimeEntity;
import com.example.animelist.Database.AnimeEntityToAnimeDetailMapper;
import com.example.animelist.Database.DataBaseAction;
import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Fragment affichant la liste des animes dans la liste de favoris
 */
public class AnimeListFragment extends Fragment {

    private View view;
    private DataBaseAction db;
    private List<AnimeDetail> listFav;
    private RequestManager requestManager;
    private RecyclerView recyclerView;

    /**
     * Creation de la vue avec l'initialisation de requestManager qui permettra d'afficher les images grace à Glide
     * initialisation de la base de donnée Room
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_my_list, container, false);
        requestManager= Glide.with(this);
        db = new DataBaseAction(getContext());
        return view;
    }

    /**
     * Appel à la methode getAnimesFromFav a chaque reprise du fragment (permet d'actualiser la page lorsqu'on revient sur ce fragment)
     */
    @Override
    public void onResume() {
        super.onResume();
        getAnimesFromFav();
    }

    /**
     * Methode de récuperation de la liste de favoris depuis la base de donnée en local et affiche le resultat dans
     * le recyclerView
     */
    @SuppressLint("CheckResult")
    public void getAnimesFromFav(){
        listFav = new ArrayList<>();
        db.getDb().animeDao().loadFavorites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<AnimeEntity>>() {
                    @Override
                    public void onNext(List<AnimeEntity> animeEntities) {
                        for(AnimeEntity a : animeEntities){
                            listFav.add(AnimeEntityToAnimeDetailMapper.map(a));
                        }
                        recyclerView =view.findViewById(R.id.recycler_fav);
                        FavListAdapter adapter = new FavListAdapter(listFav,requestManager) ;
                        recyclerView.setAdapter(adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(layoutManager);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
