package com.example.prm392_taixiufbt.ui.home;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.ViewHolder>{
    Context context;
    ArrayList<MenuItems> menuItems;

    public HomeRecycleViewAdapter(Context context, ArrayList<MenuItems> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int itemWidth = screenWidth / 3; // For 3 items per row
        // Adjust for any margins or padding as necessary
        itemWidth -= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics); // Example padding adjustment

        // Set the size of the ImageView
        ViewGroup.LayoutParams imageLayoutParams = holder.image.getLayoutParams();
        imageLayoutParams.width = itemWidth;
        imageLayoutParams.height = itemWidth; // Assuming square items for a uniform grid
        holder.image.setLayoutParams(imageLayoutParams);

        // Adjust the text size dynamically based on item width
        // This is a simplistic approach; you may need to adjust the formula based on your design
        float textSize = itemWidth / 10f; // Example formula to adjust text size
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        // Bind your data to the itemView here
        MenuItems menuItem = menuItems.get(position);
        holder.name.setText(menuItem.getName());
        int imageResource = context.getResources().getIdentifier(menuItem.getImage(),"drawable",context.getPackageName());
        if (imageResource != 0) { // Resource exists
            holder.image.setImageResource(imageResource);
        } else {
            // Set a default image or handle the error
        }
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
        float screenWidth = displayMetrics.widthPixels;
        return (int) ((screenWidth - (margins * (spanCount + 1))) / spanCount);
    }
}