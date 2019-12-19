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

/**
 * Classe principale contenant un fragment container permettant d'accèder aux differentes pages
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;

    /**
     * Methode onCreate qui associe le layout activity_main et associe le menu de navigation.
     * Lors de la creation, le fragment de la liste des genres est affiché.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AnimeListFragment())
                .commit();
        navigationView = findViewById(R.id.nav_bar);
        navigationView.setOnNavigationItemSelectedListener(navListener);
    }


    /**
     * Initialisation des actions du menu de navigation entre les fragment.
     */
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
