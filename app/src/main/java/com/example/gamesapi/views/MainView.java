package com.example.gamesapi.views;

import com.example.gamesapi.models.Game;
import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void showGames(List<Game> games);
    void showError(String message);
}
