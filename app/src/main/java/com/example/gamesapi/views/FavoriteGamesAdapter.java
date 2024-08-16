package com.example.gamesapi.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gamesapi.R;
import com.example.gamesapi.models.Game;
import java.util.List;

public class FavoriteGamesAdapter extends RecyclerView.Adapter<FavoriteGamesAdapter.GameViewHolder> {

    private List<Game> favoriteGames;

    public FavoriteGamesAdapter(List<Game> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_fav, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = favoriteGames.get(position);
        holder.titleTextView.setText(game.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(game.getThumbnail())
                .into(holder.gameImageView);
    }

    @Override
    public int getItemCount() {
        return favoriteGames.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImageView;
        TextView titleTextView;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImageView = itemView.findViewById(R.id.gameImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
}
