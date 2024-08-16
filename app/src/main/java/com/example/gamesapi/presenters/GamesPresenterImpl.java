package com.example.gamesapi.presenters;

import com.example.gamesapi.models.Game;
import com.example.gamesapi.network.GamesApi;
import com.example.gamesapi.views.MainView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesPresenterImpl implements GamesPresenter {

    private MainView mainView;
    private GamesApi gamesApi;

    public GamesPresenterImpl(MainView mainView, GamesApi gamesApi) {
        this.mainView = mainView;
        this.gamesApi = gamesApi;
    }

    @Override
    public void getGames() {
        mainView.showLoading();
        gamesApi.getGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                mainView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    mainView.showGames(response.body());
                } else {
                    mainView.showError("Error al cargar los juegos");
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                mainView.hideLoading();
                mainView.showError(t.getMessage());
            }
        });
    }
}
