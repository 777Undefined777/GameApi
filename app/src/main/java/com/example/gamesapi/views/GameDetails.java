package com.example.gamesapi.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.gamesapi.R;
import com.example.gamesapi.models.Game;

public class GameDetails extends AppCompatActivity {

    private ImageView gameImageView;
    private TextView titleTextView;
    private TextView shortDescriptionTextView;
    private TextView gameUrlTextView;
    private TextView genreTextView;
    private TextView platformTextView;
    private TextView publisherTextView;
    private TextView developerTextView;
    private TextView releaseDateTextView;

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

        // Obtener el juego desde el intent
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
        } else {
            Log.e("GameDetails", "Game object is null");
        }
    }
}
