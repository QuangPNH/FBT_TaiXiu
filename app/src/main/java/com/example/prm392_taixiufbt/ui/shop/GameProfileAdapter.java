package com.example.prm392_taixiufbt.ui.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_taixiufbt.models.GameProfile;
import java.util.ArrayList;
import java.util.List;

public class GameProfileAdapter extends RecyclerView.Adapter<GameProfileAdapter.ViewHolder> {

    private List<GameProfile> gameProfiles = new ArrayList<>();
    private final GameProfileListener listener;

    public GameProfileAdapter(GameProfileListener listener) {
        this.listener = listener;
    }

    public void setGameProfiles(List<GameProfile> gameProfiles) {
        this.gameProfiles = gameProfiles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameProfile gameProfile = gameProfiles.get(position);
        holder.textView.setText(gameProfile.getTitle()); // Assuming GameProfile has a getTitle() method.
        holder.itemView.setOnClickListener(v -> listener.onGameProfileSelected(gameProfile));
    }

    @Override
    public int getItemCount() {
        return gameProfiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface GameProfileListener {
        void onGameProfileSelected(GameProfile gameProfile);
    }
}