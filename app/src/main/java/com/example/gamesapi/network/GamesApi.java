package com.example.gamesapi.network;

import com.example.gamesapi.models.Game;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GamesApi {
    @GET("games")
    Call<List<Game>> getGames();
}
