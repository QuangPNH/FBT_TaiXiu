package com.example.prm392_taixiufbt.ui.promo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.DAO.NewsItemDAO;
import com.example.prm392_taixiufbt.databinding.FragmentPromoBinding;

import java.util.ArrayList;

public class PromoFragment extends Fragment {

    private FragmentPromoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPromoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize RecyclerView
        RecyclerView recyclerView = binding.recyclerViewPromo;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final NewsItemAdapter adapter = new NewsItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        // Inside onCreateView of PromoFragment
        FBTTaiXiuDatabase db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                FBTTaiXiuDatabase.class, "FBT_TaiXiu").build();

        NewsItemDAO.NewsItemDao newsItemDao = db.newsItemDao();
        PromoViewModelFactory factory = new PromoViewModelFactory(newsItemDao);
        PromoViewModel promoViewModel = new ViewModelProvider(this, factory).get(PromoViewModel.class);

        promoViewModel.getNewsItemsByTypeId(0).observe(getViewLifecycleOwner(), newsItems -> {
            // Update the UI
            adapter.setNewsItems(newsItems);
            adapter.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}