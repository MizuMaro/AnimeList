package com.example.animelist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.animelist.Api.ApiInterface;
import com.example.animelist.Api.RetrofitClientInstance;
import com.example.animelist.Database.DataBaseAction;
import com.example.animelist.Model.AnimeDetail;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activité qui permet d'afficher le detail d'un anime.
 */
public class DetailAnime extends AppCompatActivity {

    private Context mContext;
    private ToggleButton fav;
    private TextView txtEpisodes;
    private TextView txtTitle;
    private TextView txtType;
    private TextView txtStatus;
    private TextView txtSynopsis;
    private TextView txtPremiered;
    private TextView txtScore;
    private ImageView img;
    private Button redirect;
    private RequestManager requestManager;
    private ProgressBar progressBar;
    private int id;
    private ScrollView scrollDetail;
    private Button tryAgain;
    private View top;
    private DataBaseAction db;
    private ApiInterface api;
    private AnimeDetail result ;

    /**
     * Methode OnCreate qui associe le layout anime_detail
     * Cette methode permet de récuperer via le parent l'id de l'anime qu'on souhaite afficher ou bien l'objet AnimeDetail récupéré en local
     * le bouton TryAgain permet de relancer la requete en cas d'echec de la requete, il arrive que l'api renvoi une erreur 500  dans ce cas l'utilisateur peut choisir de relancer la requete.
     * la base de donnée est aussi initialisé qui permettra d'ajouter un anime en favoris ou le retirer.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_detail);
        mContext = getApplicationContext();
        requestManager= Glide.with(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        result = (AnimeDetail) getIntent().getSerializableExtra("animeDetail");
        img = (ImageView) findViewById(R.id.detail_image);
        txtEpisodes =  (TextView) findViewById(R.id.detail_episode);
        txtTitle =  (TextView)findViewById(R.id.detail_title);
        txtType = (TextView) findViewById(R.id.detail_type);
        txtEpisodes =  (TextView) findViewById(R.id.detail_episode);
        txtStatus =  (TextView)findViewById(R.id.detail_status);
        txtPremiered = (TextView) findViewById(R.id.detail_premiered);
        txtSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        txtScore = (TextView) findViewById(R.id.detail_score);
        redirect= (Button) findViewById(R.id.redirectMal);
        fav = (ToggleButton) findViewById(R.id.button_favorite);
        progressBar = (ProgressBar) findViewById(R.id.progressBarDetail);
        scrollDetail = (ScrollView) findViewById(R.id.scrollDetail);
        top = (View) findViewById(R.id.topBloc) ;
        tryAgain = (Button) findViewById(R.id.retryDetail);
        tryAgain.setVisibility(View.GONE);
        tryAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                tryAgain.setVisibility(View.GONE);
                getAnimeDetail(id);
            }

        });
        db = new DataBaseAction(getApplicationContext());

        progressBar.setVisibility(View.VISIBLE);
        top.setVisibility(View.VISIBLE);

        if (result == null)
            getAnimeDetail(id);
        else
            generateDetail();
    }


    /**
     * Methode qui permet de récuperer depuis l'api un anime qui est en cas de succès retourné dans result
     * @param id identifiant de l'anime
     */
    private void getAnimeDetail(int id){
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeDetail> call = api.getAnimeDetail(id);
        call.enqueue(new Callback<AnimeDetail>() {
            @Override
            public void onResponse(Call<AnimeDetail> call, Response<AnimeDetail> response) {
                if(response.isSuccessful()) {
                    result = response.body();

                    generateDetail();
                }else{
                    tryAgain.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AnimeDetail> call, Throwable t) {
                System.out.println("Anime by Genre" + "Failed to get "+t.getMessage());
                Toast.makeText(mContext.getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Methode qui permet de verifier si l'anime affiché est en favoris ou non et met a jour la valeur
     * favorite dans l'objet result.
     */
    public void isFav() {
        db.getDb().animeDao().getFavoriteIdList().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> ids) {
                        if (ids.contains(result.getId() + "")) {
                            result.setFavorite(true);
                            fav.setChecked(result.isFavorite());
                        }
                        else{
                            result.setFavorite(false);
                            fav.setChecked(result.isFavorite());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * Methode qui permet d'affecter les données à l'affiche une fois les données récuperé
     */
    private void generateDetail() {
        isFav();
        requestManager.load(result.getImage_url()).into(img);
        scrollDetail.setVisibility(View.VISIBLE);
        txtTitle.setText(result.getTitle());
        txtScore.setText(result.getScore()+"");
        txtSynopsis.setText(result.getSynopsis());
        txtPremiered.setText(result.getPremiered());
        txtType.setText(result.getType());
        txtEpisodes.setText(result.getEpisodes()+"");
        txtStatus.setText(result.getStatus());
        progressBar.setVisibility(View.GONE);
        top.setVisibility(View.GONE);
        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @SuppressLint("CheckResult")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.addToFav(result);
                }else {
                    db.removeFromFav(result);

                }
            }
        });
        redirect.setOnClickListener(new View.OnClickListener() {
            /**
             * Action qui permet d'ouvrir la page internet lié à l'anime sur le site MyAnimeList
             */
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse(result.getUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Url invalide",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
