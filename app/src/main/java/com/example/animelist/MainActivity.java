package com.example.animelist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.animelist.Fragment.AnimeListFragment;
import com.example.animelist.Fragment.GenreFragment;
import com.example.animelist.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_bar);
        navigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment selected = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.genre:
                            selected = new GenreFragment();
                            break;
                        case R.id.myList :
                            selected = new AnimeListFragment();
                            break;
                        case R.id.search :
                            selected = new SearchFragment();
                            break;

                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selected)
                            .commit();
                    return true;
                }
            };

}
