package com.example.gamesapi.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
    private GamesPresenter gamesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gamesPresenter = new GamesPresenterImpl(this, RetrofitClient.getGamesApi());
        gamesPresenter.getGames();
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
        GamesAdapter adapter = new GamesAdapter(games, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
