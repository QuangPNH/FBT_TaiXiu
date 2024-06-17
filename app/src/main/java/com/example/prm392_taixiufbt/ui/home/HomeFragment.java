package com.example.prm392_taixiufbt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentHomeBinding;
import com.example.prm392_taixiufbt.models.MenuItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayList<MenuItems> menuItems = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initData();
        // Step 1: Sort the ArrayList
        Collections.sort(menuItems, new Comparator<MenuItems>() {
            @Override
            public int compare(MenuItems o1, MenuItems o2) {
                return Integer.compare(o1.getCategory(), o2.getCategory());
            }
        });

        // Step 2: Separate the items into two lists
        ArrayList<MenuItems> mainFeaturesList = new ArrayList<>();
        ArrayList<MenuItems> gameModeList = new ArrayList<>();

        for (MenuItems item : menuItems) {
            if (item.getCategory() == 0) {
                mainFeaturesList.add(item);
            } else if (item.getCategory() == 1) {
                gameModeList.add(item);
            }
        }

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Step 3: Create adapters and set them to the RecyclerViews
        HomeRecycleViewAdapterForMenu mainFeaturesAdapter = new HomeRecycleViewAdapterForMenu(getContext(), mainFeaturesList);
        RecyclerView mainFeaturesRecyclerView = binding.mMainItems;
        mainFeaturesRecyclerView.setAdapter(mainFeaturesAdapter);
        mainFeaturesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));


        HomeRecycleViewAdapter gameModeAdapter = new HomeRecycleViewAdapter(getContext(), gameModeList);
        RecyclerView gameModeRecyclerView = binding.mGameMode;
        gameModeRecyclerView.setAdapter(gameModeAdapter);
        gameModeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        return root;
    }
    public void initData(){
        menuItems.add(new MenuItems("Nạp Tiền", "ic_payment", 0));
        menuItems.add(new MenuItems("Rút Tiền", "ic_withdrawal", 0));
        menuItems.add(new MenuItems("Thể Thao", "ic_football", 0));
        menuItems.add(new MenuItems("Khuyến Mãi", "ic_promo", 0));
        menuItems.add(new MenuItems("CSKH", "ic_headphone", 0));
        menuItems.add(new MenuItems("30 Giây", "ic_30", 1));
        menuItems.add(new MenuItems("60 Giây", "ic_60", 1));
        menuItems.add(new MenuItems("2 phút", "ic_2m", 1));
        menuItems.add(new MenuItems("5 phút", "ic_5m", 1));
        menuItems.add(new MenuItems("10 phút", "ic_10m", 1));
        menuItems.add(new MenuItems("Baccarat", "ic_baccarat", 1));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}