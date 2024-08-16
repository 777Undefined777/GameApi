package com.example.gamesapi.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamesapi.R;
import com.example.gamesapi.models.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteGamesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteGamesAdapter adapter;
    private List<Game> favoriteGames;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_games);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE);
        gson = new Gson();

        loadFavorites();

        adapter = new FavoriteGamesAdapter(favoriteGames);
        recyclerView.setAdapter(adapter);
    }

    private void loadFavorites() {
        String json = sharedPreferences.getString("favorite_games", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        favoriteGames = gson.fromJson(json, type);

        if (favoriteGames == null) {
            favoriteGames = new ArrayList<>();
        }
    }
}
