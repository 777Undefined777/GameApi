package com.example.gamesapi.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gamesapi.R;

import com.example.gamesapi.models.Game;
import com.example.gamesapi.network.RetrofitClient;
import com.example.gamesapi.presenters.GamesPresenter;
import com.example.gamesapi.presenters.GamesPresenterImpl;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView recyclerView;
    private RecyclerView carouselRecyclerView;
    private GamesPresenter gamesPresenter;
    private Handler carouselHandler;
    private Runnable carouselRunnable;
    private int carouselPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Grid con 2 columnas

        carouselRecyclerView = findViewById(R.id.RecyclerCarrousel);
        carouselRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Carrusel horizontal

        gamesPresenter = new GamesPresenterImpl(this, RetrofitClient.getGamesApi());
        gamesPresenter.getGames();

        // Handler para el auto-scroll del carrusel
        carouselHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void showLoading() {
        // Puedes dejar esto vacío o eliminar el método si prefieres
    }

    @Override
    public void hideLoading() {
        // Puedes dejar esto vacío o eliminar el método si prefieres
    }

    @Override
    public void showGames(List<Game> games) {
        // Adaptador del Grid de Juegos
        GamesAdapter gamesAdapter = new GamesAdapter(games, this);
        recyclerView.setAdapter(gamesAdapter);

        // Filtrar los primeros 50 juegos para el carrusel
        List<Game> carouselGames = games.subList(0, Math.min(50, games.size()));
        CarouselAdapter carouselAdapter = new CarouselAdapter(carouselGames, this);
        carouselRecyclerView.setAdapter(carouselAdapter);

        // Iniciar auto-scroll
        startAutoScroll(carouselGames.size());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startAutoScroll(final int itemCount) {
        carouselRunnable = new Runnable() {
            @Override
            public void run() {
                if (carouselPosition == itemCount) {
                    carouselPosition = 0; // Reiniciar al primer ítem
                }
                carouselRecyclerView.smoothScrollToPosition(carouselPosition++);
                carouselHandler.postDelayed(this, 3000); // Cambiar cada 3000 ms
            }
        };
        carouselHandler.postDelayed(carouselRunnable, 3000); // Primer cambio en 3000 ms
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (carouselHandler != null && carouselRunnable != null) {
            carouselHandler.removeCallbacks(carouselRunnable);
        }
    }
}
