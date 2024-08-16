package com.example.gamesapi.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamesapi.R;
import com.example.gamesapi.models.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameDetails extends AppCompatActivity {

    private ImageView gameImageView, addFavoritesImageView;
    private TextView titleTextView;
    private TextView shortDescriptionTextView;
    private TextView gameUrlTextView;
    private TextView genreTextView;
    private TextView platformTextView;
    private TextView publisherTextView;
    private TextView developerTextView;
    private TextView releaseDateTextView;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<Game> favoriteGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        // Referencias a las vistas
        gameImageView = findViewById(R.id.gameImageView);
        titleTextView = findViewById(R.id.titleTextView);
        shortDescriptionTextView = findViewById(R.id.shortDescriptionTextView);
        gameUrlTextView = findViewById(R.id.gameUrlTextView);
        genreTextView = findViewById(R.id.genreTextView);
        platformTextView = findViewById(R.id.platformTextView);
        publisherTextView = findViewById(R.id.publisherTextView);
        developerTextView = findViewById(R.id.developerTextView);
        releaseDateTextView = findViewById(R.id.releaseDateTextView);
        addFavoritesImageView = findViewById(R.id.addfavorites);

        sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE);
        gson = new Gson();

        // Cargar la lista de favoritos existente
        loadFavorites();

        Game game = getIntent().getParcelableExtra("game");

        if (game != null) {
            // Mostrar la información del juego
            titleTextView.setText(game.getTitle());
            shortDescriptionTextView.setText(game.getShortDescription());
            gameUrlTextView.setText(game.getGameUrl());
            genreTextView.setText(game.getGenre());
            platformTextView.setText(game.getPlatform());
            publisherTextView.setText(game.getPublisher());
            developerTextView.setText(game.getDeveloper());
            releaseDateTextView.setText(game.getReleaseDate());

            // Usar Glide para cargar la imagen
            Glide.with(this)
                    .load(game.getThumbnail())
                    .placeholder(R.drawable.images) // Imagen por defecto mientras carga
                    .error(R.drawable.images) // Imagen por defecto en caso de error
                    .into(gameImageView);

            // Logs para depuración
            Log.d("GameDetails", "Title: " + game.getTitle());
            Log.d("GameDetails", "Short Description: " + game.getShortDescription());
            Log.d("GameDetails", "Game URL: " + game.getGameUrl());
            Log.d("GameDetails", "Genre: " + game.getGenre());
            Log.d("GameDetails", "Platform: " + game.getPlatform());
            Log.d("GameDetails", "Publisher: " + game.getPublisher());
            Log.d("GameDetails", "Developer: " + game.getDeveloper());
            Log.d("GameDetails", "Release Date: " + game.getReleaseDate());

            addFavoritesImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavorites(game);
                    Intent intent = new Intent(GameDetails.this, FavoriteGamesActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("GameDetails", "Game object is null");
        }
    }

    private void loadFavorites() {
        String json = sharedPreferences.getString("favorite_games", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        favoriteGames = gson.fromJson(json, type);

        if (favoriteGames == null) {
            favoriteGames = new ArrayList<>();
        }
    }

    private void addToFavorites(Game game) {
        favoriteGames.add(game);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(favoriteGames);
        editor.putString("favorite_games", json);
        editor.apply();
    }
}
