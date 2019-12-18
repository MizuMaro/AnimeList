package com.example.animelist;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.animelist.Api.ApiInterface;
import com.example.animelist.Api.RetrofitClientInstance;
import com.example.animelist.Model.AnimeDetail;
import com.example.animelist.Model.AnimeListResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnime extends AppCompatActivity {

    Context mContext;

    private TextView txtEpisodes;
    private TextView txtTitle;
    private TextView txtType;
    private TextView txtStatus;
    private TextView txtSynopsis;
    private TextView txtPremiered;
    private TextView txtScore;
    private ImageView img;
    private RequestManager requestManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_detail);
        mContext = getApplicationContext();
        requestManager= Glide.with(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getAnimeDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    ApiInterface api;
    AnimeDetail result ;
    private void getAnimeDetail(){
        /*Create handle for the RetrofitInstance interface*/
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<AnimeDetail> call = api.getAnimeDetail(1);
        call.enqueue(new Callback<AnimeDetail>() {
            @Override
            public void onResponse(Call<AnimeDetail> call, Response<AnimeDetail> response) {
                result = response.body();
                generateDetail();

            }

            @Override
            public void onFailure(Call<AnimeDetail> call, Throwable t) {
                System.out.println("Anime by Genre" + "Failed to get "+t.getMessage());
                Toast.makeText(mContext.getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDetail() {
        txtEpisodes =  (TextView) findViewById(R.id.detail_episode);
        txtTitle =  (TextView)findViewById(R.id.detail_title);
        txtType = (TextView) findViewById(R.id.detail_type);txtEpisodes =  (TextView) findViewById(R.id.detail_episode);
        txtStatus =  (TextView)findViewById(R.id.detail_status);
        txtPremiered = (TextView) findViewById(R.id.detail_premiered);
        txtSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        txtScore = (TextView) findViewById(R.id.detail_score);
        img = (ImageView) findViewById(R.id.detail_image);

        txtTitle.setText(result.getTitle());
        txtScore.setText(result.getScore()+"");
        txtSynopsis.setText(result.getSynopsis());
        txtPremiered.setText(result.getPremiered());
        txtType.setText(result.getType());
        txtEpisodes.setText(result.getEpisodes()+"");
        txtStatus.setText(result.getStatus());

        requestManager.load(result.getImage_url()).into(img);

    }
}
