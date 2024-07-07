package com.example.prm392_taixiufbt.ui.home;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.models.MenuItems;

import java.util.ArrayList;

public class HomeRecycleViewAdapterForMenu extends RecyclerView.Adapter<HomeRecycleViewAdapterForMenu.ViewHolder>{
    Context context;
    ArrayList<MenuItems> menuItems;

    public HomeRecycleViewAdapterForMenu(Context context, ArrayList<MenuItems> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int size = calculateItemSize(context, 1, 16); // Adjusted for a 5x1 grid with 16px margins
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = size; // Adjust height for a horizontal grid
        holder.itemView.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(size, size);
        holder.image.setLayoutParams(imageLayoutParams);

        MenuItems menuItem = menuItems.get(position);
        holder.name.setText(menuItem.getName());

        int imageResource = context.getResources().getIdentifier(menuItem.getImage(),"drawable",context.getPackageName());
        if (imageResource != 0) { // Resource exists
            holder.image.setImageResource(imageResource);
        } else {
            // Set a default image or handle the error
        }


        // Center the item
        LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        itemLayoutParams.gravity = Gravity.CENTER;
        holder.itemView.setLayoutParams(itemLayoutParams);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.p_name);
            image = itemView.findViewById(R.id.p_image);
        }
    }

    public static int calculateItemSize(Context context, int spanCount, int margins) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenHeight = displayMetrics.heightPixels;
        // Adjusting for a horizontal grid (5x1)
        return (int) ((screenHeight - (margins * (spanCount + 1))) / spanCount);
    }

}