package com.example.animelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.animelist.Model.AnimeGenreList;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String myJson=inputStreamToString(getAssets().open("AnimeGenre.json"));
            AnimeGenreList myModel = new Gson().fromJson(myJson, AnimeGenreList.class);
            System.out.println(myJson);
            System.out.println(myModel.list);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
