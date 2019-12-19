package com.example.animelist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.animelist.Database.AnimeDatabase;
import com.example.animelist.Database.AnimeEntity;
import com.example.animelist.Database.AnimeToAnimeEntityMapper;
import com.example.animelist.Database.DataBaseAction;
import com.example.animelist.Model.AnimeDetail;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnime extends AppCompatActivity {

    Context mContext;

    private ToggleButton fav;
    private TextView txtEpisodes;
    private TextView txtTitle;
    private TextView txtType;
    private TextView txtStatus;
    private TextView txtSynopsis;
    private TextView txtPremiered;
    private TextView txtScore;
    private ImageView img;
    private RequestManager requestManager;
    private ProgressBar progressBar;
    private int id;
    private ScrollView scrollDetail;
    private Button tryAgain;
    private View top;
    private DataBaseAction db;

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

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        top.setVisibility(View.VISIBLE);

        if (result == null)
            getAnimeDetail(id);
        else
            generateDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    ApiInterface api;
    AnimeDetail result ;



    private void getAnimeDetail(int id){


        /*Create handle for the RetrofitInstance interface*/
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

    }

}
