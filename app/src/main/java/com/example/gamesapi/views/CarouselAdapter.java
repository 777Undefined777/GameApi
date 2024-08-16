package com.example.gamesapi.views;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gamesapi.R;
import com.example.gamesapi.models.Game;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<Game> games;
    private Context context;

    public CarouselAdapter(List<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Game game = games.get(position);

        Glide.with(context)
                .load(game.getThumbnail())
                .into(holder.carouselImageView);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView carouselImageView;


        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselImageView = itemView.findViewById(R.id.carouselImageView);

        }
    }
}
