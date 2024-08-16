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
    private boolean isFavorite;

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

            // Verificar si el juego ya está en favoritos
            isFavorite = isGameFavorite(game);

            // Establecer el ícono del botón de favoritos dependiendo del estado
            updateFavoriteIcon();

            addFavoritesImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite) {
                        removeFromFavorites(game);
                    } else {
                        addToFavorites(game);
                    }
                    updateFavoriteIcon();
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

    private boolean isGameFavorite(Game game) {
        for (Game favoriteGame : favoriteGames) {
            if (favoriteGame.getId() == game.getId()) {
                return true;
            }
        }
        return false;
    }

    private void addToFavorites(Game game) {
        favoriteGames.add(game);
        saveFavorites();
        isFavorite = true;
    }

    private void removeFromFavorites(Game game) {
        for (int i = 0; i < favoriteGames.size(); i++) {
            if (favoriteGames.get(i).getId() == game.getId()) {
                favoriteGames.remove(i);
                break;
            }
        }
        saveFavorites();
        isFavorite = false;
    }

    private void saveFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(favoriteGames);
        editor.putString("favorite_games", json);
        editor.apply();
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            addFavoritesImageView.setImageResource(R.drawable.ic_favoritos); // Cambia esto al ícono de agregado
        } else {
            addFavoritesImageView.setImageResource(R.drawable.ic_favoritos); // Cambia esto al ícono de eliminado
        }
    }
}
